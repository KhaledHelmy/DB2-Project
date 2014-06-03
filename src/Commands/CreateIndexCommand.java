package Commands;

import Exceptions.DBEngineException;
import Interfaces.DBNonQueryCommand;
import Utilities.Table;

public class CreateIndexCommand implements DBNonQueryCommand{
	private String strTableName, strColName;
	
	public CreateIndexCommand(	String strTableName, 
								String strColName){
		this.strTableName = strTableName;
		this.strColName = strColName;
	}
	
	public void execute() throws DBEngineException{
		Table table = Table.getInstance(strTableName);
		
		if(table == null){
			throw new DBEngineException("Table " + strTableName + " doesn't Exist");
		}
		
		table.createIndex(strColName);
	}
}
