package RecoveryManager;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Stack;

public class RecoveryManager implements RecoveryManagerInterface {
	String path;
	BufferedReader br;
	ArrayList<String> committed = new ArrayList<String>(); //names of committed transactions
	Stack<String> logFileReveresed = new Stack<String>();
	Stack<String> logFileMa3dool = new Stack<String>();

	public static void main(String[] args) {
		RecoveryManagerInterface x = new RecoveryManager();
		x.recover();
	}

	@Override
	public void recover() {
		getPath();
		try {
			br = new BufferedReader(new FileReader(new File(path)));
			System.out.println(br.readLine());
		} catch (IOException e) {
			e.printStackTrace();
		}
		ta7tLeFoo2();
		Foo2LeTa7t();
	}

	private void ta7tLeFoo2() {
		String line;
		try {
			while ((line = br.readLine())!=null) {
				if (line.split(",")[0]!=null&&line.split(",")[0].equals("start")) {
					continue;
				}
				else if (line.split(",")[0]!=null&&line.split(",")[0].equals("commit")) {
					committed.add(line.split(",")[1]);
				}
				else {
					logFileReveresed.push(line);
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		String transID;
		String task;
		while (!logFileReveresed.isEmpty()) {
			line = logFileReveresed.pop();
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

	private void Foo2LeTa7t() {
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

	private void getPath() {
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

}
