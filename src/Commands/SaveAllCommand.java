package Commands;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Hashtable;

import Exceptions.DBEngineException;
import Interfaces.DBNonQueryCommand;
import Utilities.Column;
import Utilities.Table;

public class SaveAllCommand implements DBNonQueryCommand{
	private File metaData;
	
	public SaveAllCommand(File metaData){
		this.metaData = metaData;
	}
	
	public void execute() throws DBEngineException{
		return;
	}
}
