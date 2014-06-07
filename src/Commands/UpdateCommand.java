package Commands;

import java.util.Hashtable;

import Exceptions.DBEngineException;
import Interfaces.DBNonQueryCommand;
import Utilities.Table;

public class UpdateCommand implements DBNonQueryCommand{
	private String strTableName;
	private Hashtable<String, String> htblColNameValue;

	public UpdateCommand(String strTableName,
			Hashtable<String, String> htblColNameValue) {
		this.strTableName = strTableName;
		this.htblColNameValue = htblColNameValue;
	}
	
	public void execute() throws DBEngineException{
		Table table = Table.getInstance(strTableName);
		
		if(table == null){
			throw new DBEngineException("Table " + strTableName + " doesn't Exist");
		}
		
		table.updateRecord(htblColNameValue);
	}
}
