package Parser;

import gudusoft.gsqlparser.stmt.TCreateTableSqlStatement;
import Interfaces.StatmentParserInterface;

public class CreateParser implements StatmentParserInterface {
	private TCreateTableSqlStatement stmnt;

	public CreateParser(TCreateTableSqlStatement stmnt) {
		this.stmnt = stmnt;
	}
	@Override
	public void parse() {
		throw new UnsupportedOperationException();
	}

}
