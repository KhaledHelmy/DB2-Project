package DataBase;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;

import jdbm.RecordManager;
import Commands.CreateIndexCommand;
import Commands.CreateTableCommand;
import Commands.DeleteCommand;
import Commands.InsertCommand;
import Commands.SaveAllCommand;
import Commands.SelectCommand;
import Exceptions.DBEngineException;
import Interfaces.DBAppInterface;
import Interfaces.DBFileSystem;
import Interfaces.DBNonQueryCommand;
import Interfaces.DBQueryCommand;
import Utilities.Column;
import Utilities.Memory;
import Utilities.Table;

@SuppressWarnings("unused")
public class DBApp implements DBAppInterface {
	private static DBFileSystem fileSystem = Memory.getMemory();
	private static DBApp dBApp = new DBApp();

	private int MaximumRowsCountinPage, BPlusTreeN;
	private File metaData = new File("data/metadata.csv"),
			DBAppProperties = new File("config/DBApp.properties");
	private RecordManager recordManager;

	private DBApp() {
		init();
	}

	public static DBFileSystem getFileSystem() {
		return fileSystem;
	}

	public static DBApp getInstance() {
		return dBApp;
	}

	public int getMaximumRowsCountinPage(){
		return MaximumRowsCountinPage;
	}
	
	@Override
	public void init() {
		Properties properties = getFileSystem().loadProperties();

		MaximumRowsCountinPage = Integer.parseInt((String) properties
				.get("MaximumRowsCountinPage"));
		BPlusTreeN = Integer.parseInt((String) properties.get("BPlusTreeN"));
		System.out.println(MaximumRowsCountinPage);
		System.out.println(BPlusTreeN);

		Hashtable<String, Object> metaData = getFileSystem().loadMetaData();
		Table.initTables((List<Column>) metaData.get("columns"));
	}

	@Override
	public void createTable(String strTableName,
			Hashtable<String, String> htblColNameType,
			Hashtable<String, String> htblColNameRefs, String strKeyColName)
			throws DBEngineException {
		DBNonQueryCommand createTableCommand = new CreateTableCommand(
				strTableName, htblColNameType, htblColNameRefs, strKeyColName);
		createTableCommand.execute();
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

}
