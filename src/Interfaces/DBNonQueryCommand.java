package Interfaces;

import Exceptions.DBEngineException;

public interface DBNonQueryCommand {
	public void execute() throws DBEngineException;
}
