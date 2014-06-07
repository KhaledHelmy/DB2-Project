package Interfaces;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Properties;
import java.util.Stack;

import Exceptions.DBInvalidColumnNameException;
import Utilities.Column;
import Utilities.OurBPlusTree;
import Utilities.Record;
import Utilities.Table;

public interface DBFileSystem {
	public boolean createTable(String tableName,
			Hashtable<String, String> colNameType,
			Hashtable<String, String> colNameRef, String strKeyCol);

	public Table loadTable(String tableName);

	public boolean createTree(String tableName, String colName);

	public OurBPlusTree loadTree(String tableName, String columnName);

	public boolean addRecord(String tableName,
			Hashtable<String, String> htblColNameValue);

	public void deleteRecord(Hashtable<String, Object> params);

	public Properties loadProperties();

	public void loadMetaData() throws DBInvalidColumnNameException;

	public void appendMetaData(Table table);

	public Hashtable<Integer, Record> loadPage(String pageName);

	public void deleteRecord(String pageName, int rowNumber);

	public void updateProperties();

	public List<Column> getColumns();

	boolean saveTrees();

	void saveMetaData();
	
	public String getProperty(String propertyName);
	
	public void appendToFile(String toBeAppended,String path);
	
	public void readLog(ArrayList<String> committed, Stack<String> logFileReversed);
}
