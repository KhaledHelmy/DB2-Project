package RecoveryManager;

import java.io.BufferedReader;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Stack;

import DataBase.DBApp;
import Interfaces.DBAppInterface;
import Interfaces.DBFileSystem;
import Utilities.Memory;

public class RecoveryManager implements RecoveryManagerInterface {
	DBFileSystem fs = Memory.getMemory();
	DBAppInterface dbApp = DBApp.getInstance();
	String path;
	BufferedReader br;
	ArrayList<String> committed = new ArrayList<String>(); //names of committed transactions
	Stack<String> logFileReversed = new Stack<String>();
	Stack<String> logFileMa3dool = new Stack<String>();
	static RecoveryManager recoveryManager = null;

	public static void main(String[] args) {
		RecoveryManagerInterface x = new RecoveryManager();
		x.recover();
	}
	
	private RecoveryManager() {
		
	}
	
	public static RecoveryManager getInstance() {
		if (recoveryManager==null) {
			recoveryManager=new RecoveryManager();
		}
		return recoveryManager;
	}

	@Override
	public void recover() {
		fs.readLog(committed, logFileReversed);
		undo();
		redo();
	}

	private void undo() {
		String line;
		String transID;
		String task;
		while (!logFileReversed.isEmpty()) {
			line = logFileReversed.pop();
			transID = line.split(",")[1];
			if (isCommitted(transID)) {
				logFileMa3dool.push(line);
			}
			else {
				//undo logging
				task = line.split(",")[0];
				String tableName = line.split(",")[2].split("_")[0];
				//System.out.println(tableName);
				Hashtable<String, String> htblColVal = new Hashtable<String,String>();
				if (task.equals("insert")) {
					String[] splitted = line.split(",");
					String htStr = splitted[3];
					for (int i = 4;i<splitted.length;i++) {
						htStr = htStr.concat(","+splitted[i]);
					}
					htblColVal = buildHTfromString(htStr);
					//System.out.println(htblColVal.toString());
					try {
						dbApp.deleteFromTable(tableName, htblColVal, "AND");
					} catch (Exception e) {
						
					}
				}
				else if (task.equals("delete")) {
					String[] splitted = line.split(",");
					String htStr = splitted[4];
					for (int i = 5;i<splitted.length;i++) {
						htStr = htStr.concat(","+splitted[i]);
					}
					htblColVal = buildHTfromString(htStr);
					//System.out.println(htblColVal.toString());
					try {
						dbApp.insertIntoTable(tableName, htblColVal);
					} catch (Exception e) {
						
					}
				}
				else if (task.equals("update")) {
					String[] splitted = line.split(",");
					String htStr = splitted[5];
					for (int i = 6;i<splitted.length;i++) {
						htStr = htStr.concat(","+splitted[i]);
						if (splitted[i].charAt(splitted[i].length()-1)=='}') {
							break;
						}
					}
					htblColVal = buildHTfromString(htStr); //old object
					//System.out.println("aaa   "+htblColVal.toString());
					Hashtable<String,String> newObject;
					newObject = buildHTfromString(line.split("},")[1]);
					//System.out.println("brns   "+newObj.toString());
					//call el update hena surrounded with try/catch w pass htblColVal as newObj w newObject as oldObj
				}
			}
		}
		
	}
	
	private boolean isCommitted(String transID) {
		for (String s : committed) {
			if (s.equals(transID)) {
				return true;
			}
		}
		return false;
	}

	private void redo() {
		String line;
		String task;
		while (!logFileMa3dool.isEmpty()) {
			line = logFileMa3dool.pop();
			task = line.split(",")[0];
			String tableName = line.split(",")[2].split("_")[0];
			//System.out.println(tableName);
			Hashtable<String, String> htblColVal = new Hashtable<String,String>();
			if (task.equals("insert")) {
				String[] splitted = line.split(",");
				String htStr = splitted[3];
				for (int i = 4;i<splitted.length;i++) {
					htStr = htStr.concat(","+splitted[i]);
				}
				//System.out.println(htStr);
				htblColVal = buildHTfromString(htStr);
				try {
					dbApp.insertIntoTable(tableName, htblColVal);
				} catch (Exception e) {
					
				}
			}
			else if (task.equals("delete")) {
				String[] splitted = line.split(",");
				String htStr = splitted[4];
				for (int i = 5;i<splitted.length;i++) {
					htStr = htStr.concat(","+splitted[i]);
				}
				htblColVal = buildHTfromString(htStr);
				try {
					dbApp.deleteFromTable(tableName, htblColVal, "AND");
				} catch (Exception e) {
				}
			}
			else if (task.equals("update")) {
				String[] splitted = line.split(",");
				String htStr = splitted[5];
				for (int i = 6;i<splitted.length;i++) {
					htStr = htStr.concat(","+splitted[i]);
					if (splitted[i].charAt(splitted[i].length()-1)=='}') {
						break;
					}
				}
				htblColVal = buildHTfromString(htStr); //old object
				//System.out.println("aaa   "+htblColVal.toString());
				Hashtable<String,String> newObject;
				newObject = buildHTfromString(line.split("},")[1]);
				//System.out.println("brns   "+newObject.toString());
				//call el update hena surrounded with try/catch w pass htblColVal as oldObj w newObject as newObj
			}
		}
	}

	private Hashtable<String, String> buildHTfromString(String htStr) {
		Hashtable<String,String> res = new Hashtable<String,String>();
		htStr = htStr.substring(1, htStr.length()-1);
		String[] elements = htStr.split(", ");
		for (int i=0;i<elements.length;i++) {
			res.put(elements[i].split("=")[0], elements[i].split("=")[1]);
		}
		return res;
	}

	/*private void getPath() {
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
	}*/

}
