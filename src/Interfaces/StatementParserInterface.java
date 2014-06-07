package Interfaces;

import Exceptions.DBAppException;

public interface StatementParserInterface {
	public String parse() throws DBAppException;
}
