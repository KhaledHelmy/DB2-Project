package Utilities;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import jdbm.RecordManager;
import jdbm.RecordManagerFactory;
import jdbm.btree.BTree;
import jdbm.helper.DefaultSerializer;
import jdbm.helper.Serializer;
import jdbm.helper.StringComparator;
import DataBase.DBApp;

public class OurBPlusTree {
	private static RecordManager recordManager = getRecordManager();
	private static Serializer serializer = new DefaultSerializer();
	private String tableName;
	private String colName;
	private BTree theTree;

	private static RecordManager getRecordManager() {
		try {
			return RecordManagerFactory.createRecordManager("data/DATABASE");
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static RecordManager getOurRecordManagerInstance() {
		return recordManager;
	}

	public static OurBPlusTree ourCreate(List<Record> records, String colName,
			String tableName) {
		OurBPlusTree ourTree = ourCreate(tableName, colName);
		for (Record r : records)
			ourTree.ourInsert(r.getValue(colName), r.getRowNumber() + "");
		return ourTree;
	}

	public static OurBPlusTree ourCreate(String tableName, String colName) {
		OurBPlusTree ourTree = getTree(tableName, colName);
		return ourTree;
	}

	public Object ourInsert(Record record) {
		String key, value;
		key = record.getValue(getColName());
		value = record.getRowNumber() + "";
		return ourInsert(key, value);
	}

	public Object ourInsert(String key, String value) {
		String found = (String) find(key);
		if (found == null)
			insert(key, value, false);
		else
			insert(key, found + "," + value, true);
		return found;
	}

	public String getColName() {
		return colName;
	}

	public void ourDelete(Record record) {
		String rows = (String) find(record.getValue(getColName()));
		System.out.println(rows);
		String row = record.getRowNumber() + "";
		int index = rows.indexOf(row);
		String insert = null;
		if (index == 0) {
			insert = rows.substring(row.length() + 1);
		} else if (index + row.length() == rows.length()) {
			insert = rows.substring(0, index - 1);
		} else {
			insert = rows.substring(0, index - 1) + rows.substring(index);
		}
		insert(record.getValue(getColName()), insert, true);
	}

	public List<Record> ourSearch(String key) {
		String strings = (String) find(key);
		List<Record> records = new ArrayList<Record>();
		if (strings == null)
			return records;
		Table thisTable = Table.getInstance(tableName);
		int pageNo = 0, rowNo = 0, parse = 0;
		String[] rows = strings.split(",");
		for (String s : rows) {
			parse = Integer.parseInt(s);
			pageNo = parse / DBApp.getInstance().getMaximumRowsCountinPage();
			rowNo = parse % DBApp.getInstance().getMaximumRowsCountinPage();
			Page p = thisTable.getPage(pageNo);
			Record rec = p.getRecords().get(rowNo);
			records.add(rec);
		}
		return records;
	}

	private Object find(Object key) {
		try {
			return theTree.find(key);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	private Object insert(Object key, Object value, boolean replace) {
		try {
			return theTree.insert(key, value, replace);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public void setColName(String colName) {
		this.colName = colName;
	}

	public static OurBPlusTree getTree(String tableName, String colName) {
		OurBPlusTree ourTree = new OurBPlusTree();
		String tableCol = tableName + "_" + colName;
		try {
			long id = recordManager.getNamedObject(tableCol);
			if (id != 0)
				ourTree.theTree = load(id);
			else
				ourTree.theTree = getNewInstance(tableCol);
			ourTree.setTableName(tableName);
			ourTree.setColName(colName);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return ourTree;
	}

	public static OurBPlusTree getTreeIfExists(String tableName, String colName) {
		OurBPlusTree ourTree = new OurBPlusTree();
		String tableCol = tableName + "_" + colName;
		try {
			Long id = recordManager.getNamedObject(tableCol);
			if (id != 0) {
				ourTree.theTree = load(id);
				return ourTree;
			}
			ourTree.setTableName(tableName);
			ourTree.setColName(colName);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	private static BTree getNewInstance(String tableCol) {
		try {
			BTree tree = BTree.createInstance(recordManager,
					new StringComparator(), serializer, serializer, DBApp
							.getInstance().getBPlusTreeN());
			recordManager.setNamedObject(tableCol, tree.getRecid());
			return tree;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	private static BTree load(long id) {
		try {
			BTree tree = BTree.load(recordManager, id);
			return tree;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public int size(){
		return theTree.size();
	}
}
