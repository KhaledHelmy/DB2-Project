package Parser;

import gudusoft.gsqlparser.stmt.TCreateIndexSqlStatement;
import Exceptions.DBAppException;
import Interfaces.StatmentParserInterface;
import Team3.DBApp;

public class IndexParser implements StatmentParserInterface {
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
