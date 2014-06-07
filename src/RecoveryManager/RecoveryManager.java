package RecoveryManager;

import java.io.BufferedReader;
import java.util.ArrayList;
import java.util.Stack;

import Interfaces.DBFileSystem;
import Utilities.Memory;

public class RecoveryManager implements RecoveryManagerInterface {
	DBFileSystem fs = Memory.getMemory();
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
				if (task.equals("insert")) {
					//TODO
				}
				else if (task.equals("delete")) {
					//TODO
				}
				else if (task.equals("update")) {
					//TODO
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
			//redo logging
			task = line.split(",")[0];
			if (task.equals("insert")) {
				//TODO
			}
			else if (task.equals("delete")) {
				//TODO
			}
			else if (task.equals("update")) {
				//TODO
			}
		}
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
