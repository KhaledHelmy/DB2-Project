package Commands;

import java.util.Hashtable;
import java.util.Iterator;

import Exceptions.DBEngineException;
import Interfaces.DBNonQueryCommand;
import Interfaces.DBQueryCommand;
import Utilities.Table;

public class DeleteCommand implements DBNonQueryCommand{
	private String strTableName, strOperator;
	private Hashtable<String, String> htblColNameValue;
	
	public DeleteCommand(	String strTableName, 
							Hashtable<String, String> htblColNameValue,
							String strOperator){
		this.strTableName = strTableName;
		this.strOperator = strOperator;
		this.htblColNameValue = htblColNameValue;
	}
	
	public void execute() throws DBEngineException{
		Table table = Table.getInstance(strTableName);
		table.deleteRecords(htblColNameValue, strOperator);
	}
}
