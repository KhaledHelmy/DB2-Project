package LogManager;

import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Hashtable;

import Interfaces.DBFileSystem;
import Utilities.Memory;

public class LogManager implements LogManagerInterface {
	DBFileSystem fs = Memory.getMemory();
	BufferedReader br;
	FileWriter fw;
	String path;
	String dataToBeFlushed = "";

	public static void main(String[] args) throws IOException {

		LogManager x = new LogManager();
		x.init();
		x.recordStart("miro");
		Hashtable<String, String> old = new Hashtable<String,String>();
		old.put("rab3a","ramz el somood");
		Hashtable<String, String> new1 = new Hashtable<String,String>();
		new1.put("rabia","fel qalb");
		x.recordUpdate("miro", "25-4968", "Name", old, new1);
		x.recordInsert("miro", old);
		x.recordDelete("miro", "25-4968", new1);
		x.recordCommit("miro");
		x.flushLog();

	}

	@Override
	public void init() {
		path = fs.getProperty("LogfilePath");
	}

	@Override
	public synchronized void flushLog() {
		fs.appendToFile(dataToBeFlushed, path);
		dataToBeFlushed = "";
	}

	@Override
	public synchronized void recordStart(String strTransID) {
		dataToBeFlushed += "start," + strTransID + "\n";
	}

	@Override
	public synchronized void recordUpdate(String strTransID,
			String strKeyValue, String strColName, Object objOld, Object objNew) {
		dataToBeFlushed += "update," + strTransID + "," + strKeyValue + ","
				+ strColName + "," + objOld.toString() + ","
				+ objNew.toString() + "\n";

	}

	@Override
	public synchronized void recordInsert(String strTransID,
			Hashtable<String, String> htblColValues) {
		dataToBeFlushed += "insert," + strTransID + ","
				+ htblColValues.toString() + "\n";

	}

	@Override
	public synchronized void recordDelete(String strTransID,
			String strKeyValue, Hashtable<String, String> htblColValues) {
		dataToBeFlushed += "delete," + strTransID + "," + strKeyValue + ","
				+ htblColValues.toString() + "\n";

	}

	@Override
	public synchronized void recordCommit(String strTransID) {
		dataToBeFlushed += "commit," + strTransID + "\n";
	}

}
