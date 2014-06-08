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

	public Hashtable<Integer, Record> getRecords() {
		return records;
	}

	public void addRecord(Integer row, Record record) {
		records.put(row, record);
	}
	
	public void deleteRecord(Integer row){
		records.remove(getRecord(row));
	}

	public Record getRecord(Integer row) {
		return records.get(row);
	}

	public List<Record> select(String columnName, String columnValue) {
		List<Record> result = new ArrayList<Record>();
		Iterator<Record> it = getRecords().values().iterator();
		while (it.hasNext()) {
			Record record = it.next();
			String recordValue = record.getValue(columnName);
			if (recordValue.equals(columnValue)) {
				result.add(record);
			}
		}
		return result;
	}

	public List<Record> getAllRecords() {
		return new ArrayList<Record>(records.values());
	}
	
	public boolean valueExists(String colName, String value){
		boolean found = false;
		List<Record> recordsList = getAllRecords();
		for(Record record : recordsList){
			if(record.getValue(colName).equals(value)){
				found = true;
				break;
			}
		}
		return found;
	}
}
