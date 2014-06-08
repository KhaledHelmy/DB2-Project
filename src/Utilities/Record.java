package Utilities;

import java.util.Hashtable;
import java.util.Iterator;
import java.util.Set;
import java.util.TreeSet;

import DataBase.DBApp;
import Exceptions.DBEngineException;
import Interfaces.DBFileSystem;

public class Record {
	private String pageName;
	private boolean active = true;
	public void setActive(boolean active) {
		this.active = active;
	}

	private int insertionId;
	private Hashtable<String, String> htblColNameValue;

	public Record(Hashtable<String, String> htblColNameValue, String pageName, int insertionId) {
		this.htblColNameValue = htblColNameValue;
		this.pageName = pageName;
		this.insertionId = insertionId;
	}

	public static Record createRecord(String input, String pageName, int insertionId)
			throws DBEngineException {
		Hashtable<String, String> colNameValue = new Hashtable<String, String>();
		String tableName = pageName.split("_")[0];
		String[] values = input.trim().split(",");
		for(int i = 0; i < values.length; i++){
			values[i] = values[i].trim();
		}
		TreeSet<String> set = Table.getInstance(tableName).getColsName();
		Iterator<String> iter = set.iterator();
		if (values.length != set.size())
			throw new DBEngineException();
		for (int i = 0; i < set.size(); i++)
			colNameValue.put(iter.next(), values[i]);
		return new Record(colNameValue, pageName, insertionId);
	}

	public String getValue(String column) {
		String value = htblColNameValue.get(column);
		return htblColNameValue.get(column);
	}
	public void setColumnValue(String colName,String colVal){
		htblColNameValue.put(colName,colVal);
	}

	public void delete() {
		DBFileSystem memory = DBApp.getFileSystem();
		memory.deleteRecord(pageName, insertionId % DBApp.getInstance().getMaximumRowsCountinPage());
		active = false;
	}

	public boolean isActive() {
		return active;
	}

	public String getPageName() {
		return pageName;
	}

	public int getRowNumber() {
		return insertionId;
	}

	public Hashtable<String, String> getHtblColNameValue() {
		return htblColNameValue;
	}

	@Override
	public String toString() {
		Set<String> iter = new TreeSet<String>();
		iter.addAll(htblColNameValue.keySet());
		String append = "", temp;
		for (String string : iter) {
			temp = htblColNameValue.get(string);
			append += commamize(temp);
		}
		return append.substring(0, append.length() - 2);
	}

	private String commamize(String s) {
		return s + ", ";
	}

	public int getPageNumber() {
		return getRowNumber() / DBApp.getInstance().getMaximumRowsCountinPage();
	}

	public void deactivate() {
		setActive(false);
	}
}
