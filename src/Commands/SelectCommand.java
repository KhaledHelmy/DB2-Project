package Commands;

import java.util.Hashtable;
import java.util.Iterator;

import Exceptions.DBEngineException;
import Interfaces.DBQueryCommand;
import Utilities.Table;

public class SelectCommand implements DBQueryCommand{
	private String strTable, strOperator;
	private Hashtable<String, String> htblColNameValue;
	
	public SelectCommand(	String strTable,
							Hashtable<String, String> htblColNameValue,
							String strOperator){
		this.strTable = strTable;
		this.htblColNameValue = htblColNameValue;
		this.strOperator = strOperator;
	}
	
	public Iterator execute() throws DBEngineException{
		Table table = Table.getInstance(strTable);
		
		if(table == null){
			throw new DBEngineException("Table " + strTable + " doesn't Exist");
		}
		
		return table.query(htblColNameValue, strOperator);
	}
}
