package Parser;

import gudusoft.gsqlparser.EExpressionType;
import gudusoft.gsqlparser.nodes.TExpression;
import gudusoft.gsqlparser.stmt.TDeleteSqlStatement;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.List;

import org.w3c.dom.ls.LSInput;

import Exceptions.DBEngineException;
import Interfaces.StatementParserInterface;

public class DeleteParser implements StatementParserInterface {
	private TDeleteSqlStatement stmnt;
	private String operator;

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

	private List<TExpression> check() throws DBEngineException {
		HashSet<String> set = new HashSet<String>();
		List<TExpression> conditions = new ArrayList<TExpression>();
		checkHelper(stmnt.getWhereClause().getCondition(), set, conditions);
		if (set.size() > 2)
			throw new DBEngineException(
					"More than one type of operator isnot supported");
		for (String s : set) {
			if (!supported(s).isEmpty())
				throw new DBEngineException("The Operator " + s
						+ " isnot supported");
		}
		return conditions;
	}

	private void checkHelper(TExpression condition, HashSet<String> set,
			List<TExpression> conditions) {
		if (condition.getRightOperand() == null)
			return;
		if (condition.getLeftOperand() == null)
			return;
		if (condition.getExpressionType() == null)
			return;
		if (condition.getExpressionType() == EExpressionType.simple_comparison_t
				&& condition.getComparisonOperator().toString().matches("=+")) {
			conditions.add(condition);
		}
		set.add(condition.getExpressionType().toString());
		checkHelper(condition.getLeftOperand(), set, conditions);
		checkHelper(condition.getRightOperand(), set, conditions);
	}

	private String supported(String s) {
		if (s.equals("logical_or_t") || s.equals("logical_and_t")
				|| s.equals("simple_comparison_t")) {
			if (s.equals("logical_or_t"))
				operator = "or";
			if (s.equals("logical_and_t"))
				operator = "and";
			return "";
		}
		return s;
	}
}
