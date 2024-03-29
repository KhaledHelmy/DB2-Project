package Parser;

import gudusoft.gsqlparser.TBaseType;
import gudusoft.gsqlparser.nodes.TExpression;
import gudusoft.gsqlparser.stmt.TSelectSqlStatement;

import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import Abtracts.ConditionalParsers;
import Exceptions.DBAppException;
import Utilities.Record;
import Utilities.Table;

public class SelectParser extends ConditionalParsers {

	public SelectParser(TSelectSqlStatement stmnt) {
		this.stmnt = stmnt;
	}

	@Override
	public String parse() throws DBAppException {
		if (stmnt.joins.size() != 1
				|| stmnt.joins.getJoin(0).getKind() != TBaseType.join_source_fake)
			throw new DBAppException("Joins are not supported");
		String tableName = stmnt.joins.getJoin(0).getTable().toString();
		List<TExpression> conditions = check();
		Hashtable<String, String> htblColNameValue = new Hashtable<String, String>();
		for (TExpression exp : conditions) {
			htblColNameValue.put(exp.getLeftOperand().toString(), exp
					.getRightOperand().toString());
		}
		Iterator<?> iter = dbEngine.selectFromTable(tableName,
				htblColNameValue, operator);
		Set<String> colName = Table.getInstance(tableName).getColsName();
		String ColNamesFormated = "";
		for (int i = 0; i < colName.size(); i++) {
			ColNamesFormated += "%10s|";
		}
		ColNamesFormated += "\n";
		String res = String.format(ColNamesFormated, colName.toArray());
		Object[] values = new String[colName.size()];
		while (iter.hasNext()) {
			Record r = (Record) iter.next();
			if (r == null)
				continue;
			Hashtable<String, String> nameValue = r.getHtblColNameValue();
			int i = 0;
			for (String s : colName) {
				values[i] = nameValue.get(s);
				i++;
			}
			res += String.format(ColNamesFormated, values);
		}
		return res;
	}
}
