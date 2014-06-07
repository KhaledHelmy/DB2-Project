package Parser;

import gudusoft.gsqlparser.EDbVendor;
import gudusoft.gsqlparser.TCustomSqlStatement;
import gudusoft.gsqlparser.TGSqlParser;
import gudusoft.gsqlparser.stmt.TCreateIndexSqlStatement;
import gudusoft.gsqlparser.stmt.TCreateTableSqlStatement;
import gudusoft.gsqlparser.stmt.TDeleteSqlStatement;
import gudusoft.gsqlparser.stmt.TInsertSqlStatement;
import gudusoft.gsqlparser.stmt.TSelectSqlStatement;
import gudusoft.gsqlparser.stmt.TUpdateSqlStatement;
import Exceptions.DBAppException;
import Exceptions.SyntaxErrorException;
import Exceptions.UnsupportedStatmentException;
import Interfaces.ParserInterface;

public class Parser implements ParserInterface {

	public Parser() {
	}

	@Override
	public void parse(String statements) throws DBAppException {
		TGSqlParser sqlparser = new TGSqlParser(EDbVendor.dbvmysql);
		sqlparser.sqltext = statements;
		int ret = sqlparser.parse();
		if (ret != 0)
			throw new SyntaxErrorException(sqlparser.getErrormessage());
		for (int i = 0; i < sqlparser.sqlstatements.size(); i++) {
			analyzeStmt(sqlparser.sqlstatements.get(i));
			System.out.println("");
		}
	}

	@Override
	public void parseFile(String statements) throws DBAppException {

	}

	@Override
	public void analyzeStmt(TCustomSqlStatement stmnt) throws DBAppException {
		switch (stmnt.sqlstatementtype) {
		case sstcreatetable:
			new CreateParser((TCreateTableSqlStatement) stmnt).parse();
			break;
		case sstcreateindex:
			new IndexParser((TCreateIndexSqlStatement) stmnt).parse();
			break;
		case sstinsert:
			new InsertParser((TInsertSqlStatement) stmnt).parse();
			break;
		case sstupdate:
			new UpdateParser((TUpdateSqlStatement) stmnt).parse();
			break;
		case sstdelete:
			new DeleteParser((TDeleteSqlStatement) stmnt).parse();
			break;
		case sstselect:
			new SelectParser((TSelectSqlStatement) stmnt).parse();
			break;
		default:
			throw new UnsupportedStatmentException(
					stmnt.sqlstatementtype.toString());
		}
	}

}
