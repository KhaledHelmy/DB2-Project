package Parser;

import gudusoft.gsqlparser.stmt.TSelectSqlStatement;
import Interfaces.StatmentParserInterface;

public class SelectParser implements StatmentParserInterface {
	private TSelectSqlStatement stmnt;

	public SelectParser(TSelectSqlStatement stmnt) {
		this.stmnt = stmnt;
	}

	@Override
	public void parse() {
		throw new UnsupportedOperationException();
	}
}