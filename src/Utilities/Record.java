package Utilities;

import java.util.Hashtable;

import DataBase.DBApp;
import Interfaces.DBFileSystem;

public class Record {
	private String pageName;
	private boolean active = true;
	private int rowNumber;
	private Hashtable<String, String> htblColNameValue = new Hashtable<String, String>();
	
	public Record(Hashtable<String, String> htblColNameValue, String pageName){
		this.htblColNameValue = htblColNameValue;
		this.pageName = pageName;
	}
	
	public String getValue(String column){
		return htblColNameValue.get(column.toString());
	}
	
	public void delete(){
		DBFileSystem memory = DBApp.getFileSystem();
		memory.deleteRecord(pageName, rowNumber);
		active = false;
	}
	
	public boolean isActive(){
		return active;
	}
}
