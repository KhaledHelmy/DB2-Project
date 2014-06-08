package DataBase;

import java.io.File;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Properties;

import Exceptions.DBEngineException;
import Exceptions.DBInvalidColumnNameException;
import Interfaces.DBAppInterface;
import Interfaces.DBFileSystem;
import Transactions.TransactionManager;
import Utilities.Memory;
import Utilities.Table;

@SuppressWarnings("rawtypes")
public class DBApp implements DBAppInterface {
	private static DBFileSystem fileSystem = Memory.getMemory();
	private static DBApp dBApp = new DBApp();
	private static TransactionManager transactionManager = TransactionManager
			.getInstance();

	public static final String MaximumRowsCountinPage = "MaximumRowsCountinPage",
			BPlusTreeN = "BPlusTreeN";
	private static File metaData, DBAppProperties;
	private Properties properties;

	private DBApp() {
		try {
			init();
		} catch (DBInvalidColumnNameException e) {
			e.printStackTrace();
		}
	}

	public static DBFileSystem getFileSystem() {
		return fileSystem;
	}

	public static DBApp getInstance() {
		return dBApp;
	}

	public int getMaximumRowsCountinPage() {
		return Integer.parseInt(properties.getProperty(MaximumRowsCountinPage));
	}

	@Override
	public void init() throws DBInvalidColumnNameException {
		metaData = new File("data/metadata.csv");
		DBAppProperties = new File("config/DBApp.properties");
		properties = getFileSystem().loadProperties();
		getFileSystem().loadMetaData();
		Table.initTables(getFileSystem().getColumns(), properties);
	}

	@Override
	public void createTable(String strTableName,
			Hashtable<String, String> htblColNameType,
			Hashtable<String, String> htblColNameRefs, String strKeyColName)
			throws DBEngineException {
		getTransactionManager().createTable(strTableName, htblColNameType,
				htblColNameRefs, strKeyColName);
	}

	@Override
	public void createIndex(String strTableName, String strColName)
			throws DBEngineException {
		getTransactionManager().createIndex(strTableName, strColName);
	}

	@Override
	public void insertIntoTable(String strTableName,
			Hashtable<String, String> htblColNameValue)
			throws DBEngineException {
		getTransactionManager().insertIntoTable(strTableName, htblColNameValue);
	}

	@Override
	public void updateTable(String strTableName,
			Hashtable<String, String> htblColNameValueUpdate,
			Hashtable<String, String> htblColNameValueCondition,
			String strOperator) throws DBEngineException {
		getTransactionManager().updateTable(strTableName,
				htblColNameValueUpdate, htblColNameValueCondition, strOperator);
	}

	@Override
	public void deleteFromTable(String strTableName,
			Hashtable<String, String> htblColNameValue, String strOperator)
			throws DBEngineException {
		getTransactionManager().deleteFromTable(strTableName, htblColNameValue,
				strOperator);
	}

	@Override
	public Iterator selectFromTable(String strTable,
			Hashtable<String, String> htblColNameValue, String strOperator)
			throws DBEngineException {
		return getTransactionManager().selectFromTable(strTable,
				htblColNameValue, strOperator);
	}

	@Override
	public void saveAll() throws DBEngineException {
		getTransactionManager().saveAll();
	}

	public int getBPlusTreeN() {
		return Integer.parseInt(properties.getProperty(BPlusTreeN));
	}

	public static File getMetaData() {
		return metaData;
	}

	public static File getDBAppProperties() {
		return DBAppProperties;
	}

	public Properties getProperties() {
		return properties;
	}

	public TransactionManager getTransactionManager() {
		return transactionManager;
	}
}
