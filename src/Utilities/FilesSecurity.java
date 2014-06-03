package Utilities;

import java.io.File;

public abstract class FilesSecurity {
	public abstract void action() throws Exception;

	public boolean apply(String fileName) {
		unlockFile(fileName);
		try {
			action();
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		lockFile(fileName);
		return true;
	}

	public boolean apply(File file) {
		unlockFile(file.getPath());
		try {
			action();
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		lockFile(file.getPath());
		return true;
	}

	public void lockFile(String fileName) {
		File file = new File(fileName);
		file.setReadOnly();
		file.setWritable(false);
	}

	public void unlockFile(String fileName) {
		File file = new File(fileName);
		file.setWritable(true);
	}
}
