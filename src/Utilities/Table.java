package Utilities;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;

import jdbm.btree.BTree;
import DataBase.DBApp;
import Exceptions.DBInvalidColumnNameException;

public class Table {
	private String name;
	private String keyColName;
	private int totalNumberOfInsertions;
	private Hashtable<String, Column> columns = new Hashtable<String, Column>();
	private static Hashtable<String, Table> tables;
	private Hashtable<Integer, Page> pages = new Hashtable<Integer, Page>();
	private Hashtable<String, OurBPlusTree> ourBPlusTrees = new Hashtable<String, OurBPlusTree>();
	
	public static Table getInstance(String tableName){
		return tables.get(tableName);
	}
	
	public static void addTable(Table table){
		tables.put(table.getName(), table);
	}
	
	public Page getPage(int pageNumber){
		if(!pages.contains(pageNumber)){
			Page page = new Page(getName() + pageNumber);
			pages.put(pageNumber, page);
		}
		return pages.get(pageNumber);
	}
	
	public Table(String strTableName,
			Hashtable<String, String> htblColNameType,
			Hashtable<String, String> htblColNameRefs, String strKeyColName)
			throws DBInvalidColumnNameException {
		setName(strTableName);
		String temp;
		for (String s : htblColNameType.keySet()) {
			s = s.trim();
			temp = strTableName.trim() + ", ";
			temp += s + ", ";
			temp += htblColNameType.get(s) + ", ";
			if (s.equals(strKeyColName.trim())) {
				temp += "True, True, ";
			} else {
				temp += "False, False, ";
			}
			String ref = htblColNameRefs.get(s);
			if (ref != null)
				temp += ref;
			else
				temp += "null";
			Column col = new Column(temp);
			columns.put(s, col);
		}
		setKeyColName(strKeyColName.trim());
		addTable(this);
	}
	
	public static void createTable(String strTableName,
			Hashtable<String, String> htblColNameType,
			Hashtable<String, String> htblColNameRefs, String strKeyColName) 
				throws DBInvalidColumnNameException{
		Table table = new Table(strTableName, 
								htblColNameType, 
								htblColNameRefs,
								strKeyColName);
		addTable(table);
	}
	
	public Table(String tableName) {
		setName(tableName);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public int getNumberOfPages(){
		int maximumRowsCountinPage = DBApp.getInstance().getMaximumRowsCountinPage();
		return (totalNumberOfInsertions + maximumRowsCountinPage - 1) / maximumRowsCountinPage;
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
	}

	public Column getColumn(String colName) {
		return columns.get(colName);
	}

	public static Hashtable<String, Table> initTables(List<Column> columns) {
		Hashtable<String, Table> tables = new Hashtable<String, Table>();
		String colName, tableName;
		for (Column col : columns) {
			colName = col.getColName();
			tableName = col.getTableName();
			if (!tables.containsKey(tableName))
				tables.put(tableName, new Table(tableName));
			tables.get(tableName).addColumn(colName, col);
		}
		return tables;
	}

	public List<Record> getAllRecords(){
		int numberOfPages = getNumberOfPages();
		List<Record> result = new ArrayList<Record>();
		
		for(int i = 0; i < numberOfPages; i++){
			Page page = getPage(i);
			List<Record> pageResult = page.getAllRecords();
			result.addAll(pageResult);
		}
		return result;
	}
	
	public void createIndex(String strColName) {
		List<Record> records = getAllRecords();
		OurBPlusTree tree = OurBPlusTree.ourCreate(records);
		ourBPlusTrees.put(strColName, tree);
	}

	public void deleteRecords(Hashtable<String, String> htblColNameValue,
			String strOperator) {
		// TODO Auto-generated method stub
		
	}

	public void insertRecord(Hashtable<String, String> htblColNameValue) {
		// TODO Auto-generated method stub
		
	}

	public Iterator query(Hashtable<String, String> htblColNameValue,
			String strOperator) {
		Object[] keys = htblColNameValue.keySet().toArray();
		if(keys.length == 1){
			return select(keys[0].toString(), htblColNameValue.get(keys[0])).iterator();
		}
		else if(keys.length == 2){
			Iterator result = null;
			if(strOperator.equalsIgnoreCase("or")){
				result = union(htblColNameValue);
			}
			else{
				result = intersection(htblColNameValue);
			}
			return result;
		}
		else{
			return new ArrayList<Record>().iterator();
		}
	}
	
	public Iterator union(Hashtable<String, String> htblColNameValue){
		Object[] keys = htblColNameValue.keySet().toArray();
		
		List<Record> set1 = select(keys[0].toString(), htblColNameValue.get(keys[0]));
		List<Record> set2 = select(keys[1].toString(), htblColNameValue.get(keys[1]));
		
		set1.addAll(set2);
		return set1.iterator();
	}
	
	public Iterator intersection(Hashtable<String, String> htblColNameValue){
		Object[] keys = htblColNameValue.keySet().toArray();
		
		int key1 = 0, key2 = 1;
		if(!isIndexed(keys[key1].toString())){
			key1 = 1;
			key2 = 0;
		}
		
		List<Record> set1 = select(keys[key1].toString(), htblColNameValue.get(keys[key1].toString()));
		List<Record> result = new ArrayList<Record>();
		
		String value = htblColNameValue.get(keys[key2].toString());
		for(Record record : set1){
			if(record.getValue(keys[key2].toString()).equals(value)){
				result.add(record);
			}
		}
		return result.iterator();
	}
	
	public boolean isIndexed(String colName){
		Column col = columns.get(colName);
		if(col!=null)
			return col.isIndex();
		return false;
	}
	
	public List<Record> select(String columnName, String columnValue){
		if(isIndexed(columnName)){
			return selectIndexedColumn(columnName, columnValue);
		}
		else{
			return selectNonIndexedColumn(columnName, columnValue);
		}
	}
	
	public List<Record> selectIndexedColumn(String columnName, String columnValue){
		OurBPlusTree tree = getBPlusTree(columnName);
		return tree.ourSearch(columnValue);
	}
	
	public List<Record> selectNonIndexedColumn(String columnName, String columnValue){
		int numberOfPages = getNumberOfPages();
		List<Record> result = new ArrayList<Record>();
		
		for(int i = 0; i < numberOfPages; i++){
			Page page = getPage(i);
			List<Record> pageResult = page.select(columnName, columnValue);
			result.addAll(pageResult);
		}
		return result;
	}
	
	public OurBPlusTree getBPlusTree(String columnName){
		return ourBPlusTrees.get(columnName);
	}
}
