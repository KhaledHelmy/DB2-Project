package Interfaces;

import java.util.Hashtable;
import java.util.Iterator;

import Exceptions.DBEngineException;

public interface DBAppInterface {
	public void init();

	public void createTable(String strTableName,
			Hashtable<String, String> htblColNameType,
			Hashtable<String, String> htblColNameRefs, String strKeyColName)
			throws DBEngineException;

	public void createIndex(String strTableName, String strColName)
			throws DBEngineException;

	public void insertIntoTable(String strTableName,
			Hashtable<String, String> htblColNameValue) throws DBEngineException;

	public void deleteFromTable(String strTableName,
			Hashtable<String, String> htblColNameValue, String strOperator)
			throws DBEngineException;

	public Iterator selectFromTable(String strTable,
			Hashtable<String, String> htblColNameValue, String strOperator)
			throws DBEngineException;

	public void saveAll() throws DBEngineException;
}
