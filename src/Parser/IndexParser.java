package Parser;

import gudusoft.gsqlparser.stmt.TCreateIndexSqlStatement;
import Interfaces.StatmentParserInterface;

public class IndexParser implements StatmentParserInterface {
	private TCreateIndexSqlStatement stmnt;

	public IndexParser(TCreateIndexSqlStatement stmnt) {
		this.stmnt = stmnt;
	}

	@Override
	public void parse() {
		throw new UnsupportedOperationException();
	}

}
