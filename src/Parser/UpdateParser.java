package Parser;

import gudusoft.gsqlparser.nodes.TExpression;
import gudusoft.gsqlparser.stmt.TUpdateSqlStatement;

import java.util.Hashtable;
import java.util.List;

import Abtracts.ConditionalParsers;
import Exceptions.DBEngineException;

public class UpdateParser extends ConditionalParsers {

	public UpdateParser(TUpdateSqlStatement stmnt) {
		this.stmnt = stmnt;
	}

	@Override
	public String parse() throws DBEngineException {
		String tableName = stmnt.getTargetTable().toString();
		List<TExpression> conditions = check();
		Hashtable<String, String> htblColNameValue = new Hashtable<String, String>();
		for (TExpression exp : conditions) {
			htblColNameValue.put(exp.getLeftOperand().toString(), exp
					.getRightOperand().toString());
		}
		Hashtable<String, String> updateValues = new Hashtable<String, String>();
		for (int i = 0; i < stmnt.getResultColumnList().size(); i++) {
			TExpression Col = stmnt.getResultColumnList().getResultColumn(i)
					.getExpr();
			String key = Col.getLeftOperand().toString();
			String value = Col.getRightOperand().toString();
			updateValues.put(key, value);
		}
		dbEngine.updateTable(tableName, updateValues, htblColNameValue,
				operator);
		return "Value has been updated in table " + tableName;
	}

}
