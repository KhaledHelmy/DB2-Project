package Parser;

import gudusoft.gsqlparser.stmt.TUpdateSqlStatement;
import Interfaces.StatmentParserInterface;

public class UpdateParser implements StatmentParserInterface {
	private TUpdateSqlStatement stmnt;

	public UpdateParser(TUpdateSqlStatement stmnt) {
		this.stmnt = stmnt;
	}

	@Override
	public void parse() {
		throw new UnsupportedOperationException();
	}

}
