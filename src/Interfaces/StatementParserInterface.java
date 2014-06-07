package Interfaces;

import DataBase.DBApp;
import Exceptions.DBAppException;

public interface StatementParserInterface {
	DBApp dbEngine = DBApp.getInstance();
	public String parse() throws DBAppException;
}
