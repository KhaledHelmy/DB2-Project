package Utilities;

import java.util.Hashtable;
import java.util.Properties;

import jdbm.btree.BTree;

import Interfaces.DBFileSystem;

public class Memory implements DBFileSystem{
	
	public static Memory memory = new Memory();
	
	public static DBFileSystem getMemory(){
		return memory;
	}
	
	@Override
	public boolean createTable() {
		throw new UnsupportedOperationException();
	}

	@Override
	public Table loadTable() {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean createTree() {
		throw new UnsupportedOperationException();
	}

	@Override
	public BTree loadTree() {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean addRecord() {
		throw new UnsupportedOperationException();
	}

	@Override
	public void deleteRecord(Hashtable<String, Object> params) {
		throw new UnsupportedOperationException();
	}

	@Override
	public Properties loadProperties() {
		throw new UnsupportedOperationException();
	}

	@Override
	public Hashtable<String, Object> loadMetaData() {
		throw new UnsupportedOperationException();
	}

	@Override
	public Hashtable<Integer, Record> loadPage(String pageName) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void deleteRecord(String pageName, int rowNumber) {
		// TODO Auto-generated method stub
		
	}

}
