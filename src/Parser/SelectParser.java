package Parser;

import gudusoft.gsqlparser.stmt.TSelectSqlStatement;
import Interfaces.StatementParserInterface;

public class SelectParser implements StatementParserInterface {
	private TSelectSqlStatement stmnt;

	public SelectParser(TSelectSqlStatement stmnt) {
		this.stmnt = stmnt;
	}

	@Override
	public String parse() {
		throw new UnsupportedOperationException();
	}
}
