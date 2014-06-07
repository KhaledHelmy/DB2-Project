package Parser;

import gudusoft.gsqlparser.stmt.TInsertSqlStatement;
import Interfaces.StatmentParserInterface;

public class InsertParser implements StatmentParserInterface {
	private TInsertSqlStatement stmnt;

	public InsertParser(TInsertSqlStatement stmnt) {
		this.stmnt = stmnt;
	}

	@Override
	public String parse() {
		
		throw new UnsupportedOperationException();
	}

}
