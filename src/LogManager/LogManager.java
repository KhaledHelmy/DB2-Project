package LogManager;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Hashtable;

public class LogManager implements LogManagerInterface {
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
		try {
			br = new BufferedReader(new FileReader(new File(
					"config/DBApp.properties")));
			String line;
			while ((!((line = br.readLine()).split("=")[0] == null))
					&& (!line.split("=")[0].equals("LogfilePath")))
				;
			path = line.split("=")[1];
			br.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public synchronized void flushLog() {
		try {
			fw = new FileWriter(path, true);
			fw.append(dataToBeFlushed);
			fw.close();
			dataToBeFlushed = "";
		} catch (IOException e) {
			e.printStackTrace();
		}

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
