package Parser;

import gudusoft.gsqlparser.nodes.TExpression;
import gudusoft.gsqlparser.stmt.TDeleteSqlStatement;

import java.util.Hashtable;
import java.util.List;

import Abtracts.ConditionalParsers;
import Exceptions.DBEngineException;

public class DeleteParser extends ConditionalParsers {
	public DeleteParser(TDeleteSqlStatement stmnt) {
		this.stmnt = stmnt;
	}

	@Override
	public String parse() throws DBEngineException {
		String tableName = stmnt.getTargetTable().getTableName().toString();
		List<TExpression> conditions = check();
		Hashtable<String, String> htblColNameValue = new Hashtable<String, String>();
		for (TExpression exp : conditions) {
			htblColNameValue.put(exp.getLeftOperand().toString(), exp
					.getRightOperand().toString());
		}
		dbEngine.deleteFromTable(tableName, htblColNameValue, operator);
		return "Tuples Deleted from the table " + tableName;
	}
}
