package Utilities;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Properties;
import java.util.Set;
import java.util.TreeSet;

import ConstraintsCheckers.ConstraintsCheckersType;
import DataBase.DBApp;
import Exceptions.DBEngineException;
import Exceptions.DBInvalidColumnNameException;
import Factories.ConstraintsCheckerFactory;
import Interfaces.ConstraintsChecker;
import Interfaces.DBFileSystem;

@SuppressWarnings("rawtypes")
public class Table {
	private static boolean doCascade = true;
	private String name;
	private String keyColName;
	private int totalNumberOfInsertions;
	private TreeSet<String> colSet;
	private Hashtable<String, Column> columns = new Hashtable<String, Column>();
	private static Hashtable<String, Table> tables = new Hashtable<String, Table>();
	private Hashtable<Integer, Page> pages = new Hashtable<Integer, Page>();
	private Hashtable<String, OurBPlusTree> ourBPlusTrees = new Hashtable<String, OurBPlusTree>();

	public Integer getTotalNumberOfInsertions() {
		return totalNumberOfInsertions;
	}

	// public void setTotalNumberOfInsertions(int totalNumberOfInsertions) {
	// DBApp.getInstance().getProperties()
	// .setProperty(name, totalNumberOfInsertions + "");
	// }

	
	public void incrementTotalNumberOfInsertions() {
		totalNumberOfInsertions++;
		DBApp.getInstance().getProperties()
				.setProperty(getName(), getTotalNumberOfInsertions() + "");
		DBApp.getFileSystem().updateProperties();
	}

	public static Table getInstance(String tableName) {
		return tables.get(tableName);
	}

	public static void addTable(Table table) {
		tables.put(table.getName(), table);
	}

	public Page getPage(int pageNumber) {
		Page page = pages.get(pageNumber);
		if (page == null) {
			page = new Page(getName() + "_" + pageNumber);
			pages.put(pageNumber, page);
		}
		return page;
	}

	public void unloadPage(int pageNumber) {
		pages.remove(pageNumber);
	}

	public void unloadTree(String colName) {
		ourBPlusTrees.remove(colName);
	}

	private boolean isValidType(String type) throws DBEngineException {
		try {
			Class<?> myClass = Class.forName(type);
			myClass.getDeclaredMethod("valueOf", String.class);
		} catch (ClassNotFoundException e) {
			throw new DBEngineException(type + " is not a valid type");
		} catch (NoSuchMethodException e) {
			throw new DBEngineException(type + " is not a supported type");
		} catch (SecurityException e) {
			throw new DBEngineException(type + " is not a supported type");
		}
		return true;
	}

	public Table(String strTableName,
			Hashtable<String, String> htblColNameType,
			Hashtable<String, String> htblColNameRefs, String strKeyColName)
			throws DBEngineException, DBInvalidColumnNameException {
		if (!htblColNameType.containsKey(strKeyColName))
			throw new DBInvalidColumnNameException("The Column "
					+ strKeyColName + " doesn't exist in the table "
					+ strTableName);
		if (tables.containsKey(strTableName))
			throw new DBEngineException("The Table " + strTableName
					+ " already exists in the database");

		setName(strTableName);
		String temp;
		Hashtable<String, Column> c = new Hashtable<String, Column>();
		for (String s : htblColNameType.keySet()) {
			s = s.trim();
			String colType = htblColNameType.get(s);
			temp = strTableName.trim() + ", ";
			temp += s + ", ";
			temp += colType + ", ";
			if (s.equals(strKeyColName.trim())) {
				temp += "True, True, ";
			} else {
				temp += "False, False, ";
			}

			// if(!isValidType(colType)){
			// throw new DBEngineException(
			// "Column " + s + " doesn't have a valid type (" + colType + ")");
			// }

			String ref = htblColNameRefs.get(s) + "";
			String[] reference = ref.split("\\.");
			if (!ref.equalsIgnoreCase("null")) {
				if (reference.length != 2)
					throw new DBEngineException(
							"The referenced Table or referenced Column is not specified");
				String referencedTableName = reference[0];
				String referencedColumnName = reference[1];
				if (!tables.containsKey(referencedTableName))
					throw new DBEngineException(
							"The referenced Table doesn't exist");
				if (!Table.getInstance(referencedTableName).columns
						.containsKey(referencedColumnName))
					throw new DBEngineException(
							"The referenced Column doesn't exist");
				Column referencedColumn = Table
						.getInstance(referencedTableName).getColumn(
								referencedColumnName);
				if (!referencedColumn.getColType().equalsIgnoreCase(
						htblColNameType.get(s))) {
					throw new DBEngineException(
							"The referenced Column is not of the same type");
				}
				if (!referencedColumn.isKey()) {
					throw new DBEngineException(
							"The referenced Column is not a primary key");
				}
			}
			temp += ref;
			Column col = new Column(temp);

			if (col.HasReference()) {
				Table referencedTable = Table.getInstance(col
						.getReferencedTableName());
				referencedTable.addReferenceBack(
						getName() + "." + col.getColName(),
						col.getReferencedColumnName());
			}

			c.put(s, col);
		}
		columns.putAll(c);
		colSet = new TreeSet<String>();
		colSet.addAll(htblColNameType.keySet());
		setKeyColName(strKeyColName.trim());
		OurBPlusTree.ourCreate(strTableName, strKeyColName);
		DBApp.getFileSystem().saveTrees();
	}

	private void addReferenceBack(String referenceBack, String targetColumn) {
		Column column = getColumn(targetColumn);
		column.addReferenceBack(referenceBack + "." + targetColumn);
	}

	public List<String> getAllReferenceBack() {
		List<String> referencesBack = new ArrayList<String>();
		for (Column column : getAllCoLumns()) {
			referencesBack.addAll(column.getReferenceBack());
		}
		return referencesBack;
	}

	private Table(String tableName) {
		setName(tableName);
		colSet = new TreeSet<String>();
	}

	public void createIndex(String strColName)
			throws DBInvalidColumnNameException {
		if (!isColName(strColName))
			throw new DBInvalidColumnNameException(
					"There is no such Column Name (" + strColName
							+ ") in the Table (" + getName() + ")");
		List<Record> records = getAllRecords();
		OurBPlusTree tree = OurBPlusTree.ourCreate(records, strColName, name);
		ourBPlusTrees.put(strColName, tree);
		columns.get(strColName).setIndex(true);
		if (!strColName.equalsIgnoreCase(keyColName))
			DBApp.getFileSystem().saveMetaData();
		DBApp.getFileSystem().saveTrees();
	}

	private void updateBPlusTrees(Hashtable<String, String> htblColNameValue,
			int insertionNumber) {
		for (String s : columns.keySet()) {
			Column col = columns.get(s);
			if (col.isIndex()) {
				ourBPlusTrees.put(s, OurBPlusTree.getTree(getName(), s));
			}
		}
		Enumeration<String> refs = ourBPlusTrees.keys();
		while (refs.hasMoreElements()) {
			String col = refs.nextElement();

			OurBPlusTree tree = ourBPlusTrees.get(col);
			String key = htblColNameValue.get(col);

			tree.ourInsert(key, insertionNumber + "");

			// Mark BPlusTree to be saved in file
			// SaveAllCommand.insertBPlusTreeBuffer(getName(), col);
		}
		// Save all the insertions of the tree to the file
		// and unload all those trees
		if (DBApp.getFileSystem().saveTrees())
			ourBPlusTrees.clear();
	}

	public static Set<Column> getAllCoLumns() {
		Set<Column> set = new HashSet<Column>();
		for (Table t : tables.values())
			set.addAll(t.columns.values());
		return set;
	}

	public static void createTable(String strTableName,
			Hashtable<String, String> htblColNameType,
			Hashtable<String, String> htblColNameRefs, String strKeyColName)
			throws DBEngineException, DBInvalidColumnNameException {
		Table table = new Table(strTableName, htblColNameType, htblColNameRefs,
				strKeyColName);
		addTable(table);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getNumberOfPages() {
		int maximumRowsCountinPage = DBApp.getInstance()
				.getMaximumRowsCountinPage();
		return (totalNumberOfInsertions + maximumRowsCountinPage - 1)
				/ maximumRowsCountinPage;
	}

	public String getKeyColName() {
		return keyColName;
	}

	public void setKeyColName(String strKeyColName)
			throws DBInvalidColumnNameException {
		if (isColName(strKeyColName))
			this.keyColName = strKeyColName;
		else
			throw new DBInvalidColumnNameException();
	}

	public boolean isKeyColName(String strKeyColName) {
		if (getKeyColName() != null)
			return strKeyColName.equals(this.keyColName);
		return false;
	}

	public boolean isColName(String ColName) {
		return getColumns().containsKey(ColName);
	}

	public Hashtable<String, Column> getColumns() {
		return columns;
	}

	public void setColumns(Hashtable<String, Column> columns) {
		this.columns = columns;
	}

	public void addColumn(String colName, Column col) {
		columns.put(colName, col);
		colSet.add(colName);
	}

	public Column getColumn(String colName) {
		return columns.get(colName);
	}

	public static void initTables(List<Column> columns, Properties properties)
			throws DBInvalidColumnNameException {
		String colName, tableName;
		for (Column col : columns) {
			colName = col.getColName();
			tableName = col.getTableName();
			if (!tables.containsKey(tableName))
				addTable(new Table(tableName));
			getInstance(tableName).addColumn(colName, col);
			if (col.isKey()) {
				getInstance(tableName).setKeyColName(col.getColName());
			}
			String temp = properties.getProperty(tableName);
			getInstance(tableName).setTotalNumberOfInsertions(
					Integer.parseInt(temp));
		}
		for (Table table : tables.values()) {
			for (Column column : table.getColumns().values()) {
				if (column.HasReference()) {
					Table referencedTable = Table.getInstance(column
							.getReferencedTableName());
					referencedTable.addReferenceBack(table.getName() + "."
							+ column.getColName(),
							column.getReferencedColumnName());
				}
			}
		}
	}

	public void setTotalNumberOfInsertions(int totalNumberOfInsertions) {
		this.totalNumberOfInsertions = totalNumberOfInsertions;
	}

	public List<Record> getAllRecords() {
		int numberOfPages = getNumberOfPages();
		List<Record> result = new ArrayList<Record>();

		for (int i = 0; i < numberOfPages; i++) {
			Page page = getPage(i);
			List<Record> pageResult = page.getAllRecords();
			result.addAll(pageResult);
		}
		return result;
	}

	public void deleteRecords(Hashtable<String, String> htblColNameValue,
			String strOperator) {
		Iterator recordsToDelete = query(htblColNameValue, strOperator);

		if (doCascade) {
			recordsToDelete = cascadeDeletion(recordsToDelete);
		}

		while (recordsToDelete.hasNext()) {
			Record record = (Record) recordsToDelete.next();
			record.delete();
			for (OurBPlusTree tree : getAllOurBPlusTrees()) {
				tree.ourDelete(record);
			}
		}

		//unloadAllPages();
	}

	private String getCascadeTokenFormat(String tableName, String column,
			String value) {
		return tableName + "." + column + "." + value;
	}

	private List<String> getCascadeTokenOfRecord(String TableName, Record record) {
		List<String> list = new ArrayList<String>();
		for (Column col : columns.values()) {
			String colName = col.getColName();
			List<String> referencesBack = col.getReferenceBack();
			if (referencesBack.size() != 0) {
				for (String referenceBack : referencesBack) {
					String[] split = referenceBack.split("\\.");
					list.add(getCascadeTokenFormat(split[0], split[1],
							record.getValue(colName)));
				}
			}
		}
		return list;
	}

	private Iterator cascadeDeletion(Iterator recordsToCascade) {
		Set<String> queue = new LinkedHashSet<String>();
		Set<Record> recordsToDelete = new HashSet<Record>();

		while (recordsToCascade.hasNext()) {
			Record record = (Record) recordsToCascade.next();
			record.deactivate();
			queue.addAll(getCascadeTokenOfRecord(getName(), record));
			recordsToDelete.add(record);
		}
		Iterator<String> iterator;
		while (!queue.isEmpty()) {
			iterator = queue.iterator();
			String token = iterator.next();
			iterator.remove();

			String[] tokenSplit = token.split("\\.");
			String tableName = tokenSplit[0];

			Table table = Table.getInstance(tableName);
			Hashtable<String, String> htblColNameValue = new Hashtable<String, String>();
			htblColNameValue.put(tokenSplit[1], tokenSplit[2]);
			Iterator searchResult = table.query(htblColNameValue, "or");

			while (searchResult.hasNext()) {
				Record record = (Record) searchResult.next();
				if (!record.isActive()) {
					continue;
				}
				record.deactivate();
				queue.addAll(getCascadeTokenOfRecord(tableName, record));
				recordsToDelete.add(record);
			}
		}

		return recordsToDelete.iterator();
	}

	public List<OurBPlusTree> getAllOurBPlusTrees() {
		return new ArrayList<OurBPlusTree>(ourBPlusTrees.values());
	}

	public void insertRecord(Hashtable<String, String> htblColNameValue)
			throws DBEngineException {
		/*
		 * (DONE) Checks constraints (DONE) insert in files (DONE) increment
		 * number of total insertions in properties (DONE) mark page as not
		 * loaded (DONE) insert in B+ trees
		 */

		for (String key : htblColNameValue.keySet()) {
			if (!isColName(key)) {
				throw new DBEngineException(key + " is not a column in table "
						+ getName());
			}
		}

		for (String key : getColumns().keySet()) {
			String value = htblColNameValue.get(key);
			htblColNameValue.put(key, value + "");
		}

		// Check Constraints
		ConstraintsChecker constraintsChecker = ConstraintsCheckerFactory
				.getInstance(ConstraintsCheckersType.InsertConstraintChecker);
		constraintsChecker.check(getName(), htblColNameValue);

		// if(!constraintsChecker.check(getName(), htblColNameValue)){
		// / Raise some error (exception or return false or just play dead)
		// }

		// Insert in files
		DBApp.getFileSystem().addRecord(getName(), htblColNameValue);

		// Mark page as not loaded
		int pageNumber = getNumberOfPages() - 1;
		unloadPage(pageNumber);

		// Increment number of total insertions
		incrementTotalNumberOfInsertions();

		// Insert in B+ trees
		updateBPlusTrees(htblColNameValue, getTotalNumberOfInsertions() - 1);
	}

	public void updateRecord(Hashtable<String, String> htblColNameValue,
			Hashtable<String, String> htblColNameValueCondition, String strOperator)
			throws DBEngineException {
		for (String key : htblColNameValue.keySet()) {
			if (!isColName(key)) {
				throw new DBEngineException(key + " is not a column in table "
						+ getName());
			}
		}

		for (String key : getColumns().keySet()) {
			String value = htblColNameValue.get(key);
			htblColNameValue.put(key, value + "");
		}

		// Check Constraints
		ConstraintsChecker constraintsChecker = ConstraintsCheckerFactory
				.getInstance(ConstraintsCheckersType.InsertConstraintChecker);
		constraintsChecker.check(getName(), htblColNameValue);

		// if(!constraintsChecker.check(getName(), htblColNameValue)){
		// / Raise some error (exception or return false or just play dead)
		// }

		// Insert in files
		DBApp.getFileSystem().addRecord(getName(), htblColNameValue);

		// Mark page as not loaded
		int pageNumber = getNumberOfPages() - 1;
		unloadPage(pageNumber);

		// Increment number of total insertions
		incrementTotalNumberOfInsertions();

		// Insert in B+ trees
		updateBPlusTrees(htblColNameValue, getTotalNumberOfInsertions() - 1);
	}

	public Iterator query(Hashtable<String, String> htblColNameValue,
			String strOperator) {
		Iterator result = null;

		if (htblColNameValue.size() == 0) {
			result = getAllRecords().iterator();
		} else if (strOperator.equalsIgnoreCase("or")) {
			result = union(htblColNameValue);
		} else {
			result = intersection(htblColNameValue);
		}

		return result;
	}

	public Iterator union(Hashtable<String, String> htblColNameValue) {
		Object[] keys = htblColNameValue.keySet().toArray();

		HashSet<Record> result = new HashSet<Record>();

		for (int i = 0; i < keys.length; i++) {
			ArrayList<Record> subResult = select(keys[i].toString(),
					htblColNameValue.get(keys[i]));
			for (Record record : subResult) {
				result.add(record);
			}
		}

		return result.iterator();
	}

	public Iterator intersection(Hashtable<String, String> htblColNameValue) {
		Object[] keys = htblColNameValue.keySet().toArray();

		for (int i = 0; i < keys.length; i++) {
			if (isIndexed(keys[i].toString())) {
				Object tmp = keys[0];
				keys[0] = keys[i];
				keys[i] = tmp;
			}
		}

		ArrayList<Record> set = select(keys[0].toString(),
				htblColNameValue.get(keys[0].toString()));

		HashSet<Record> result = new HashSet<Record>();

		for (Record record : set) {
			boolean goodToAdd = true;
			for (int i = 1; i < keys.length; i++) {
				String value = htblColNameValue.get(keys[i].toString());
				if (!record.getValue(keys[i].toString()).equals(value)) {
					goodToAdd = false;
					break;
				}
			}
			if (goodToAdd) {
				result.add(record);
			}
		}

		return result.iterator();
	}

	public boolean isIndexed(String colName) {
		Column col = columns.get(colName);
		if (col != null)
			return col.isIndex();
		return false;
	}

	public ArrayList<Record> select(String columnName, String columnValue) {
		if (isIndexed(columnName)) {
			return selectIndexedColumn(columnName, columnValue);
		} else {
			return selectNonIndexedColumn(columnName, columnValue);
		}
	}

	public ArrayList<Record> selectIndexedColumn(String columnName,
			String columnValue) {
		OurBPlusTree tree = getBPlusTree(columnName);
		return (ArrayList<Record>) tree.ourSearch(columnValue);
	}

	public ArrayList<Record> selectNonIndexedColumn(String columnName,
			String columnValue) {
		int numberOfPages = getNumberOfPages();
		ArrayList<Record> result = new ArrayList<Record>();

		for (int i = 0; i < numberOfPages; i++) {
			Page page = getPage(i);
			List<Record> pageResult = page.select(columnName, columnValue);
			result.addAll(pageResult);
		}

		return result;
	}

	public OurBPlusTree getBPlusTree(String columnName) {
		OurBPlusTree res = null;
		if ((res = ourBPlusTrees.get(columnName)) == null) {
			DBFileSystem memory = DBApp.getFileSystem();
			res = memory.loadTree(getName(), columnName);
		}
		return res;
	}

	public TreeSet<String> getColsName() {
		return colSet;
	}

	public boolean valueExists(String colName, String value) {
		int numberOfPages = getNumberOfPages();
		boolean found = false;
		for (int i = 0; i < numberOfPages; i++) {
			boolean tmp;
			if (pageLoaded(i)) {
				tmp = getPage(i).valueExists(colName, value);
			} else {
				Page page = getPage(i);
				tmp = page.valueExists(colName, value);
				unloadPage(i);
			}
			if (tmp) {
				found = true;
				break;
			}
		}
		return found;
	}

	public boolean pageLoaded(int pageNumber) {
		return pages.get(pageNumber) != null;
	}

	private static void unloadAllPages() {
		for (Table table : tables.values()) {
			table.pages = new Hashtable<Integer, Page>();
		}
	}
}
