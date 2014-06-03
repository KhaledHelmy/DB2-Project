package Commands;

import java.util.Hashtable;

import DataBase.DBApp;
import Exceptions.DBEngineException;
import Exceptions.DBInvalidColumnNameException;
import Interfaces.DBNonQueryCommand;
import Utilities.Table;

public class CreateTableCommand implements DBNonQueryCommand{
	private String strTableName, strKeyColName;
	private Hashtable<String, String> htblColNameType, htblColNameRefs;
	
	public CreateTableCommand(	String strTableName, 
								Hashtable<String, String> htblColNameType,
								Hashtable<String, String> htblColNameRefs,
								String strKeyColName){
		this.strTableName = strTableName;
		this.htblColNameType = htblColNameType;
		this.htblColNameRefs = htblColNameRefs;
		this.strKeyColName = strKeyColName;
	}
	
	public void execute() throws DBEngineException{
		try {
			Table.createTable(	strTableName, htblColNameType,
								htblColNameRefs, strKeyColName);
		} catch (DBInvalidColumnNameException e) {
			e.printStackTrace();
		}
	}
}
