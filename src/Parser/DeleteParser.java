package Parser;

import gudusoft.gsqlparser.stmt.TDeleteSqlStatement;
import Interfaces.StatementParserInterface;

public class DeleteParser implements StatementParserInterface {
	private TDeleteSqlStatement stmnt;

	public DeleteParser(TDeleteSqlStatement stmnt) {
		this.stmnt = stmnt;
	}

	@Override
	public String parse() {
		throw new UnsupportedOperationException();
	}

}
