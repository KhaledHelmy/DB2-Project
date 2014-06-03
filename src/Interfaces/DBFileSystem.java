package Interfaces;

import java.util.Hashtable;
import java.util.Properties;

import jdbm.btree.BTree;

import Utilities.Record;
import Utilities.Table;

public interface DBFileSystem {
	public boolean createTable();
	public Table loadTable();
	public boolean createTree();
	public BTree loadTree();
	public boolean addRecord();
	public void deleteRecord(Hashtable<String, Object> params);
	public Properties loadProperties();
	public Hashtable<String, Object> loadMetaData();
	public Hashtable<Integer, Record> loadPage(String pageName);
	public void deleteRecord(String pageName, int rowNumber);
}
