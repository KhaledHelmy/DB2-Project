package Parser;

import gudusoft.gsqlparser.stmt.TDeleteSqlStatement;
import Interfaces.StatmentParserInterface;

public class DeleteParser implements StatmentParserInterface {
	private TDeleteSqlStatement stmnt;

	public DeleteParser(TDeleteSqlStatement stmnt) {
		this.stmnt = stmnt;
	}

	@Override
	public void parse() {
		throw new UnsupportedOperationException();
	}

}
