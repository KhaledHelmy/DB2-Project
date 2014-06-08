package Commands;

import java.util.Hashtable;

import Exceptions.DBEngineException;
import Interfaces.DBNonQueryCommand;
import Utilities.Table;

public class UpdateCommand implements DBNonQueryCommand {
	private String strTableName, strOperator;
	private Hashtable<String, String> htblColNameValueUpdate,
			htblColNameValueCondition;

	public UpdateCommand(String strTableName,
			Hashtable<String, String> htblColNameValue,
			Hashtable<String, String> htblColNameValueCondition,
			String strOperator) {
		this.strTableName = strTableName;
		htblColNameValueUpdate = htblColNameValue;
		this.htblColNameValueCondition = htblColNameValueCondition;
		this.strOperator = strOperator;
	}

	public void execute() throws DBEngineException {
		Table table = Table.getInstance(strTableName);

		if (table == null) {
			throw new DBEngineException("Table " + strTableName
					+ " doesn't Exist");
		}
		table.updateRecord(htblColNameValueUpdate, htblColNameValueCondition,
				strOperator);
	}
}
