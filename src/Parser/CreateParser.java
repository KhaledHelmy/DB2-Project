package Parser;

import gudusoft.gsqlparser.nodes.TColumnDefinition;
import gudusoft.gsqlparser.nodes.TConstraint;
import gudusoft.gsqlparser.stmt.TCreateTableSqlStatement;

import java.util.Hashtable;

import DataBase.DBApp;
import Exceptions.DBEngineException;
import Interfaces.StatementParserInterface;

public class CreateParser implements StatementParserInterface {
	private TCreateTableSqlStatement stmnt;

	public CreateParser(TCreateTableSqlStatement stmnt) {
		this.stmnt = stmnt;
	}

	@Override
	public String parse() throws DBEngineException {
		String tableName = stmnt.getTableName().toString();
		String strKeyColName = "", colName, colType, refTable, refColumn;
		Hashtable<String, String> htblColNameType = new Hashtable<String, String>();
		Hashtable<String, String> htblColNameRefs = new Hashtable<String, String>();
		for (int i = 0; i < stmnt.getColumnList().size(); i++) {
			TColumnDefinition col = stmnt.getColumnList().getColumn(i);
			colName = col.getColumnName().getColumnNameOnly();
			colType = col.getDatatype().toString();
			htblColNameType.put(colName, colType);
			for (int j = 0; col.getConstraints() != null
					&& j < col.getConstraints().size(); j++) {
				TConstraint cons = col.getConstraints().getConstraint(j);
				if (cons.getConstraint_type().toString().equals("primary_key"))
					strKeyColName = colName;
				else if (cons.getConstraint_type().toString()
						.equals("foreign_key")) {
					if (cons.getReferencedColumnList().size() == 1) {
						refColumn = cons.getReferencedColumnList().toString();
						refTable = cons.getReferencedObject().toString();
						String ref = refTable + "." + refColumn;
						htblColNameRefs.put(colName, ref);
					}
				}
			}
		}
		for (int i = 0; i < stmnt.getTableConstraints().size(); i++) {
			TConstraint cons = stmnt.getTableConstraints().getConstraint(i);
			if (cons.getColumnList().size() == 1) {
				colName = cons.getColumnList().toString();
				if (cons.getConstraint_type().toString().equals("primary_key"))
					strKeyColName = colName;
				else if (cons.getConstraint_type().toString()
						.equals("foreign_key")) {
					if (cons.getReferencedColumnList().size() == 1) {
						refColumn = cons.getReferencedColumnList().toString();
						refTable = cons.getReferencedObject().toString();
						String ref = refTable + "." + refColumn;
						htblColNameRefs.put(colName, ref);
					}
				}
			}
		}
		dbEngine.createTable(tableName, htblColNameType, htblColNameRefs,
				strKeyColName);
		return "Table " + tableName + " Created";
	}
}
