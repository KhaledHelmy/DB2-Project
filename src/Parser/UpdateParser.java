package Parser;

import gudusoft.gsqlparser.stmt.TUpdateSqlStatement;
import Interfaces.StatementParserInterface;

public class UpdateParser implements StatementParserInterface {
	private TUpdateSqlStatement stmnt;

	public UpdateParser(TUpdateSqlStatement stmnt) {
		this.stmnt = stmnt;
	}

	@Override
	public String parse() {
		throw new UnsupportedOperationException();
	}

}
