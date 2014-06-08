package Transactions;

import java.util.Hashtable;
import java.util.Iterator;

import BufferManager.BufferManager;
import Commands.CreateIndexCommand;
import Commands.CreateTableCommand;
import Commands.DeleteCommand;
import Commands.InsertCommand;
import Commands.SaveAllCommand;
import Commands.SelectCommand;
import Commands.UpdateCommand;
import DataBase.DBApp;
import Exceptions.DBEngineException;
import Interfaces.DBNonQueryCommand;
import Interfaces.DBQueryCommand;
import LogManager.LogManager;

public class TransactionManager {

	private BufferManager bufferManager;
	private LogManager logManager;
	private static TransactionManager transactionManager = null;

	private static TransactionManager getTransactionManager() {
		return transactionManager;
	}

	private static void setTransactionManager(
			TransactionManager transactionManager) {
		TransactionManager.transactionManager = transactionManager;
	}

	public static TransactionManager getInstance() {
		if (getTransactionManager() == null) {
			setTransactionManager(new TransactionManager());
			getTransactionManager().init();
		}

		return getTransactionManager();
	}

	public BufferManager getBufferManager() {
		return bufferManager;
	}

	public void setBufferManager(BufferManager bufferManager) {
		this.bufferManager = bufferManager;
	}

	public LogManager getLogManager() {
		return logManager;
	}

	public void setLogManager(LogManager logManager) {
		this.logManager = logManager;
	}

	public void init() {
		setBufferManager(BufferManager.getBufferManager());
		setLogManager(LogManager.getInstance());
	}

	public void createTable(String strTableName,
			Hashtable<String, String> htblColNameType,
			Hashtable<String, String> htblColNameRefs, String strKeyColName)
			throws DBEngineException {
		DBNonQueryCommand createTableCommand = new CreateTableCommand(
				strTableName, htblColNameType, htblColNameRefs, strKeyColName);
		createTableCommand.execute();
		DBApp.getFileSystem().createTable(strTableName, htblColNameType,
				htblColNameRefs, strKeyColName);
	}

	public void createIndex(String strTableName, String strColName)
			throws DBEngineException {
		DBNonQueryCommand createIndexCommand = new CreateIndexCommand(
				strTableName, strColName);
		createIndexCommand.execute();
	}

	public void insertIntoTable(String strTableName,
			Hashtable<String, String> htblColNameValue)
			throws DBEngineException {
		DBNonQueryCommand insertCommand = new InsertCommand(strTableName,
				htblColNameValue);
		insertCommand.execute();
	}

	public void updateTable(String strTableName,
			Hashtable<String, String> htblColNameValue,
			Hashtable<String, String> htblColNameValueCondition,
			String strOperator) throws DBEngineException {
		DBNonQueryCommand updateCommand = new UpdateCommand(strTableName,
				htblColNameValue, htblColNameValueCondition, strOperator);
		updateCommand.execute();
	}

	public void deleteFromTable(String strTableName,
			Hashtable<String, String> htblColNameValue, String strOperator)
			throws DBEngineException {
		DBNonQueryCommand deleteCommand = new DeleteCommand(strTableName,
				htblColNameValue, strOperator);
		deleteCommand.execute();
	}

	public Iterator selectFromTable(String strTable,
			Hashtable<String, String> htblColNameValue, String strOperator)
			throws DBEngineException {
		DBQueryCommand queryCommand = new SelectCommand(strTable,
				htblColNameValue, strOperator);
		return queryCommand.execute();
	}

	public void saveAll() throws DBEngineException {
		DBNonQueryCommand saveAllCommand = new SaveAllCommand(
				DBApp.getMetaData());
		saveAllCommand.execute();
	}
}
