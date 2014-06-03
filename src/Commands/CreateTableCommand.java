package Commands;

import java.util.Hashtable;

import Exceptions.DBEngineException;
import Interfaces.DBNonQueryCommand;
import Utilities.Table;

public class CreateTableCommand implements DBNonQueryCommand {
	private String strTableName, strKeyColName;
	private Hashtable<String, String> htblColNameType, htblColNameRefs;

	public CreateTableCommand(String strTableName,
			Hashtable<String, String> htblColNameType,
			Hashtable<String, String> htblColNameRefs, String strKeyColName) {
		this.strTableName = strTableName;
		this.htblColNameType = htblColNameType;
		this.htblColNameRefs = htblColNameRefs;
		this.strKeyColName = strKeyColName;
	}

	public void execute() throws DBEngineException {
		Table.createTable(strTableName, htblColNameType, htblColNameRefs,
				strKeyColName);
	}
}
