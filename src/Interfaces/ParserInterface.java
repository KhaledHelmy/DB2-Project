package Interfaces;

import gudusoft.gsqlparser.TCustomSqlStatement;
import Exceptions.DBAppException;

public interface ParserInterface {
	public void parse(String statements) throws DBAppException;

	public void parseFile(String statements) throws DBAppException;

	public String analyzeStmt(TCustomSqlStatement stmnt) throws DBAppException;
}
