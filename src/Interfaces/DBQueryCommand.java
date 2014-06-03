package Interfaces;

import java.util.Iterator;

import Exceptions.DBEngineException;

public interface DBQueryCommand {
	public Iterator execute() throws DBEngineException;
}
