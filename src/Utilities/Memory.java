package Utilities;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Properties;
import java.util.Set;
import java.util.Stack;
import java.util.TreeSet;

import DataBase.DBApp;
import Exceptions.DBEngineException;
import Exceptions.DBInvalidColumnNameException;
import Interfaces.DBFileSystem;

public class Memory implements DBFileSystem {

	public static Memory memory = null;
	private List<Column> columns;
	private Properties properties;

	private Memory() {
		init();
	}

	private void init() {
		File dataFolder = new File("data");
		if (!dataFolder.exists())
			dataFolder.mkdir();
		File configFolder = new File("config");
		if (!configFolder.exists())
			configFolder.mkdir();
		File metadata = new File("data/metadata.csv");
		if (!metadata.exists())
			try {
				metadata.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
	}

	public static DBFileSystem getMemory() {
		if (memory == null)
			memory = new Memory();
		return memory;
	}

	@Override
	public synchronized boolean createTable(String tableName,
			Hashtable<String, String> colNameType,
			Hashtable<String, String> colNameRef, String strKeyCol) {
		FileWriter fw = null;
		try {
			fw = new FileWriter(DBApp.getMetaData(), true);

			Set<String> set = new TreeSet<String>();
			set.addAll(colNameType.keySet());
			Iterator<String> iter = set.iterator();
			String itString, append = "";
			while (iter.hasNext()) {
				itString = iter.next();
				append += commamize(tableName);
				append += commamize(itString);
				append += commamize(colNameType.get(itString));
				if (itString.equalsIgnoreCase(strKeyCol))
					append += commamize("True, True");
				else
					append += commamize("False, False");
				append += colNameRef.get(itString) + "\n";
			}
			fw.append(append);
			fw.close();
			Properties properties = DBApp.getInstance().getProperties();
			properties.put(tableName, "0");
			updateProperties();
			return true;
		} catch (IOException e) {
			e.printStackTrace();
		}

		return false;
	}

	@Override
	public Table loadTable(String tableName) {
		Table table = Table.getInstance(tableName);
		int noOfPages = table.getNumberOfPages();
		for (int i = 0; i < noOfPages; i++)
			table.getPage(i);
		return table;
	}

	@Override
	public boolean createTree(String tableName, String colName) {
		try {
			OurBPlusTree.getOurRecordManagerInstance().commit();
			return true;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public OurBPlusTree loadTree(String tableName, String columnName) {
		return OurBPlusTree.getTree(tableName, columnName);
	}

	@Override
	public synchronized boolean addRecord(String tableName,
			Hashtable<String, String> htblColNameValue) {
		int noOfRec = 0;
		String tableInsertions = DBApp.getInstance().getProperties()
				.getProperty(tableName);
		if (tableInsertions != null)
			noOfRec = Integer.parseInt(tableInsertions);
		int max = DBApp.getInstance().getMaximumRowsCountinPage();

		final int pageno = noOfRec / max;
		final String path = "data/" + tableName + "_" + pageno + ".csv";
		Record rec = new Record(htblColNameValue, tableName + "_" + pageno, -1);
		FileWriter fw;
		try {
			fw = new FileWriter(path, true);
			fw.append(rec.toString() + "\n");
			fw.close();
			updateProperties();
			return true;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public void deleteRecord(Hashtable<String, Object> params) {
		throw new UnsupportedOperationException();
	}

	@Override
	public synchronized Properties loadProperties() {
		Properties prop = new Properties();
		try {
			if (DBApp.getDBAppProperties().exists()) {
				FileInputStream fis = new FileInputStream(
						DBApp.getDBAppProperties());
				prop.load(fis);
				fis.close();
			} else {
				prop.setProperty(DBApp.MaximumRowsCountinPage, 200 + "");
				prop.setProperty(DBApp.BPlusTreeN, 20 + "");
				FileOutputStream fos = new FileOutputStream(
						DBApp.getDBAppProperties());
				prop.store(fos, "Intializing with the default properties");
				fos.close();
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		properties = prop;
		return prop;
	}

	@Override
	public synchronized void loadMetaData() throws DBInvalidColumnNameException {
		columns = new ArrayList<Column>();
		FileReader fr;
		try {
			fr = new FileReader(DBApp.getMetaData());
			BufferedReader bf = new BufferedReader(fr);
			String temp = null;
			while (bf.ready()) {
				temp = bf.readLine();
				Column col = new Column(temp);
				columns.add(col);
			}
			fr.close();
			bf.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public synchronized void appendMetaData(final Table table) {
		FileWriter fw;
		try {
			fw = new FileWriter(DBApp.getMetaData(), true);
			Iterator<Column> iter = table.getColumns().values().iterator();
			while (iter.hasNext())
				fw.append(iter.next().toMeta());
			fw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public synchronized Hashtable<Integer, Record> loadPage(String pageName) {
		Hashtable<Integer, Record> htbl = new Hashtable<Integer, Record>();
		try {
			FileReader fr = new FileReader("data/" + pageName + ".csv");
			BufferedReader bf = new BufferedReader(fr);
			int i = 0;
			String temp = "";

			String[] pageNameSplit = pageName.split("_");
			int previousInsertionsCount = Integer.parseInt(pageNameSplit[1])
					* DBApp.getInstance().getMaximumRowsCountinPage();

			while (bf.ready()) {
				temp = bf.readLine();
				if (!temp.startsWith("\\0"))
					htbl.put(
							i,
							Record.createRecord(temp, pageName, i
									+ previousInsertionsCount));
				i++;
			}
			fr.close();
			bf.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (DBEngineException e) {
			e.printStackTrace();
		}
		return htbl;
	}

	@Override
	public synchronized void deleteRecord(final String pageName,
			final int rowNumber) {

		LinkedList<String> read = new LinkedList<String>();

		try {
			FileReader fr = new FileReader("data/" + pageName + ".csv");
			BufferedReader bf = new BufferedReader(fr);
			while (bf.ready())
				read.addLast(bf.readLine());
			fr.close();
			bf.close();
			FileWriter fw = new FileWriter("data/" + pageName + ".csv");

			String append = "";
			for (int i = 0; !read.isEmpty() && i < rowNumber; i++)
				append += read.removeFirst() + "\n";
			if (!read.isEmpty())
				append += "\\0" + read.removeFirst() + "\n";
			while (!read.isEmpty())
				append += read.removeFirst() + "\n";
			fw.append(append);
			fw.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private String commamize(String s) {
		return s + ", ";
	}

	@Override
	public synchronized void updateProperties() {
		try {
			FileOutputStream fos = new FileOutputStream(
					DBApp.getDBAppProperties());
			DBApp.getInstance().getProperties()
					.store(fos, "Updating insertions");
			fos.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public List<Column> getColumns() {
		return columns;
	}

	@Override
	public boolean saveTrees() {
		try {
			OurBPlusTree.getOurRecordManagerInstance().commit();
			return true;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public synchronized void saveMetaData() {
		Set<Column> set = Table.getAllCoLumns();
		FileWriter fw;
		try {
			fw = new FileWriter(DBApp.getMetaData());
			for (Column col : set) {
				fw.append(col.toMeta() + "\n");
			}
			fw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public synchronized String getProperty(String propertyName) {
		return properties.getProperty(propertyName);
	}

	@Override
	public synchronized void appendToFile(String toBeAppended, String path) {
		FileWriter fw;
		try {
			System.out.println("path hwa" + path);
			fw = new FileWriter(path, true);
			fw.append(toBeAppended);
			fw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	@Override
	public synchronized void readLog(ArrayList<String> committed,
			Stack<String> logFileReversed) {
		String path = Memory.getMemory().getProperty("LogfilePath");
		try {
			BufferedReader br = new BufferedReader(new FileReader(
					new File(path)));
			String line;
			while ((line = br.readLine()) != null) {
				if (line.split(",")[0] != null
						&& line.split(",")[0].equals("start")) {
					continue;
				} else if (line.split(",")[0] != null
						&& line.split(",")[0].equals("commit")) {
					committed.add(line.split(",")[1]);
				} else {
					logFileReversed.push(line);
				}
			}
			System.out.println(logFileReversed.toString());
			br.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	@Override
	public Properties getProperties() {
		return properties;
	}

}
