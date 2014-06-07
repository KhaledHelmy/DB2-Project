package Parser;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

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
import Exceptions.UnsupportedStatementException;
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
		}
	}

	@Override
	public void parseFile(String filePath) throws DBAppException {
		File f = new File(filePath);
		if (!f.exists())
			throw new DBAppException("The File " + filePath + " doesnot exist");
		try {
			BufferedReader bf = new BufferedReader(new FileReader(f));
			String statements = "";
			while (bf.ready()) {
				statements += bf.readLine();
			}
			bf.close();
			parse(statements);
		} catch (FileNotFoundException e) {
		} catch (IOException e) {
			throw new DBAppException(
					"Something went wrong while rading from the file "
							+ filePath);
		}
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
			throw new UnsupportedStatementException(
					stmnt.sqlstatementtype.toString());
		}
	}

}
