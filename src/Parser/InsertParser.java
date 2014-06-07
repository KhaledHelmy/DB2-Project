package Parser;

import java.util.Hashtable;
import java.util.Set;

import gudusoft.gsqlparser.stmt.TInsertSqlStatement;
import DataBase.DBApp;
import Exceptions.DBEngineException;
import Interfaces.StatementParserInterface;
import Utilities.Table;

public class InsertParser implements StatementParserInterface {
	private TInsertSqlStatement stmnt;
	private DBApp dbEngine = DBApp.getInstance();

	public InsertParser(TInsertSqlStatement stmnt) {
		this.stmnt = stmnt;
	}

	@Override
	public String parse() throws DBEngineException {
		String tableName = stmnt.getTargetTable().getTableName().toString();
		Hashtable<String, String> htblColNameValue = new Hashtable<String, String>();
		if (stmnt.getColumnList().size() == 0)
			ColumnsNotStated(tableName, htblColNameValue);
		else
			ColumnsStated(tableName, htblColNameValue);
		dbEngine.insertIntoTable(tableName, htblColNameValue);
		return "The Values has been inserted into the table " + tableName;
	}

	private void ColumnsStated(String tableName,
			Hashtable<String, String> htblColNameValue)
			throws DBEngineException {
		try {
			for (int i = 0; i < stmnt.getColumnList().size(); i++) {
				String ColName = stmnt.getColumnList().getObjectName(i)
						.toString();
				String value = stmnt.getValues().getMultiTarget(0)
						.getColumnList().getResultColumn(i).toString();
				htblColNameValue.put(ColName, value);
			}
		} catch (Exception e) { // Made especially for null pointer
			// exception
		}
	}

	private void ColumnsNotStated(String tableName,
			Hashtable<String, String> htblColNameValue) {
		try {
			Set<String> columns = Table.getInstance(tableName).getColsName();
			String[] cols = columns.toArray(new String[columns.size()]);
			for (int i = 0; i < cols.length; i++) {
				String value = stmnt.getValues().getMultiTarget(0)
						.getColumnList().getResultColumn(i).toString();
				htblColNameValue.put(tableName, value);
			}
		} catch (Exception e) { // Made especially for null pointer
			// exception
		}
	}
}
