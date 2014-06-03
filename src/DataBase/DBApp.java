package DataBase;

import java.io.File;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Properties;

import Commands.CreateIndexCommand;
import Commands.CreateTableCommand;
import Commands.DeleteCommand;
import Commands.InsertCommand;
import Commands.SaveAllCommand;
import Commands.SelectCommand;
import Exceptions.DBEngineException;
import Exceptions.DBInvalidColumnNameException;
import Interfaces.DBAppInterface;
import Interfaces.DBFileSystem;
import Interfaces.DBNonQueryCommand;
import Interfaces.DBQueryCommand;
import Utilities.Memory;
import Utilities.Table;

@SuppressWarnings("rawtypes")
public class DBApp implements DBAppInterface {
	private static DBFileSystem fileSystem = Memory.getMemory();
	private static DBApp dBApp = new DBApp();

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
		DBNonQueryCommand createTableCommand = new CreateTableCommand(
				strTableName, htblColNameType, htblColNameRefs, strKeyColName);
		createTableCommand.execute();
		fileSystem.createTable(strTableName, htblColNameType, htblColNameRefs,
				strKeyColName);
	}

	@Override
	public void createIndex(String strTableName, String strColName)
			throws DBEngineException {
		DBNonQueryCommand createIndexCommand = new CreateIndexCommand(
				strTableName, strColName);
		createIndexCommand.execute();
	}

	@Override
	public void insertIntoTable(String strTableName,
			Hashtable<String, String> htblColNameValue)
			throws DBEngineException {
		DBNonQueryCommand insertCommand = new InsertCommand(strTableName,
				htblColNameValue);
		insertCommand.execute();
	}

	@Override
	public void deleteFromTable(String strTableName,
			Hashtable<String, String> htblColNameValue, String strOperator)
			throws DBEngineException {
		DBNonQueryCommand deleteCommand = new DeleteCommand(strTableName,
				htblColNameValue, strOperator);
		deleteCommand.execute();
	}

	@Override
	public Iterator selectFromTable(String strTable,
			Hashtable<String, String> htblColNameValue, String strOperator)
			throws DBEngineException {
		DBQueryCommand queryCommand = new SelectCommand(strTable,
				htblColNameValue, strOperator);
		return queryCommand.execute();
	}

	@Override
	public void saveAll() throws DBEngineException {
		DBNonQueryCommand saveAllCommand = new SaveAllCommand(metaData);
		saveAllCommand.execute();
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

}
