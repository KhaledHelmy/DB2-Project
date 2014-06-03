package Utilities;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;

import DataBase.DBApp;
import Interfaces.DBFileSystem;

public class Page {
	private Hashtable<Integer, Record> records;
	
	public Page(String pageName){
		DBFileSystem memory = DBApp.getFileSystem();
		this.records = memory.loadPage(pageName);
	}
	
	public Hashtable<Integer, Record> getRecords(){
		return records;
	}

	public List<Record> select(String columnName, String columnValue) {
		List<Record> result = new ArrayList<Record>();
		Iterator<Record> it = getRecords().values().iterator();
		while(it.hasNext()){
			Record record = it.next();
			if(record.getValue(columnName).equals(columnValue)){
				result.add(record);
			}
		}
		return result;
	}
	
	public List<Record> getAllRecords(){
		throw new UnsupportedOperationException();
	}
}
