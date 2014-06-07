package Parser;

import gudusoft.gsqlparser.stmt.TCreateIndexSqlStatement;
import DataBase.DBApp;
import Exceptions.DBAppException;
import Interfaces.StatementParserInterface;

public class IndexParser implements StatementParserInterface {
	private TCreateIndexSqlStatement stmnt;
	private DBApp dbEngine = DBApp.getInstance();

	public IndexParser(TCreateIndexSqlStatement stmnt) {
		this.stmnt = stmnt;
	}

	@Override
	public String parse() throws DBAppException {
		String tableName, ColName;
		if (stmnt.getColumnNameList().size() != 1)
			throw new DBAppException(
					"The number of columns to be indexed must be equal to 1");
		tableName = stmnt.getTableName().toString();
		ColName = stmnt.getColumnNameList().toString();
		dbEngine.createIndex(tableName, ColName);
		return "The Column " + ColName + " in the table " + tableName
				+ " has been indexed";
	}

}
