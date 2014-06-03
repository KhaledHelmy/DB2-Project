package Team3;

import java.util.Date;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Random;

import Exceptions.DBAppException;
import Exceptions.DBEngineException;
import Utilities.Record;
import Utilities.Table;

public class DBAppTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) throws DBAppException {
		DBApp dbEngine = DBApp.getInstance();

		String table, key, tableName, strOperator;
		Hashtable<String, String> types, refs, htblColNameValue;
		Iterator result;
		
		//initOmarMussab(dbEngine);
		
		//init(dbEngine);
//		
		// Deleting from Person: Name=Omar and Email=Omar@Omar.Omar
//
//		try {
//			tableName = "Mussab";
//			strOperator = "and";
//			htblColNameValue = new Hashtable<String, String>();
//			htblColNameValue.put("ID", "1");
//			//htblColNameValue.put("Name", "Omar");
//
//			dbEngine.deleteFromTable(tableName, htblColNameValue, strOperator);
//			System.out.println("Omar Deleted!");
//
//		} catch (DBEngineException e) {
//			System.out.println(e.getMessage());
//		}
//
//		// Deleting from Person: Name=Aly
//
//		try {
//			tableName = "Person";
//			strOperator = "and";
//			htblColNameValue = new Hashtable<String, String>();
//			htblColNameValue.put("Name", "Aly");
//
//			dbEngine.deleteFromTable(tableName, htblColNameValue, strOperator);
//			System.out.println("Aly Deleted!");
//
//		} catch (DBEngineException e) {
//			System.out.println(e.getMessage());
//		}
//
//		// Select from House: Employee=1
//
		try {
			tableName = "House";
			strOperator = "and";
			htblColNameValue = new Hashtable<String, String>();
			//htblColNameValue.put("Employee", "1");

			result = dbEngine.selectFromTable(tableName, htblColNameValue,
					strOperator);
			System.out.println("Result is:");
			int cnt = 0;
			while (result.hasNext()) {
				Record record = (Record) result.next();
				cnt ++;
				System.out.println("\t" + record);
			}
			System.out.println(cnt);
		} finally {

		}
//
		// Deleting from House: Employee=1

//		try {
//			tableName = "House";
//			strOperator = "and";
//			htblColNameValue = new Hashtable<String, String>();
//			htblColNameValue.put("Employee", "1");
//
//			dbEngine.deleteFromTable(tableName, htblColNameValue, strOperator);
//			System.out.println("Rehab and Tagamo3 are Deleted!");
//
//		} catch (DBEngineException e) {
//			System.out.println(e.getMessage());
//		}

		

//		// Add Index: Person:Email
//
//		try {
//			dbEngine.createIndex("Person", "Email");
//		} catch (DBEngineException e) {
//			System.out.println(e.getMessage());
//		}

//		// Select from Person: Name=Omar
//
//		try {
//			tableName = "Person";
//			strOperator = "and";
//			htblColNameValue = new Hashtable<String, String>();
//			htblColNameValue.put("Name", "Omar");
//
//			result = dbEngine.selectFromTable(tableName, htblColNameValue,
//					strOperator);
//			System.out.println("Result has ");
//			int cnt = 0;
//			while (result.hasNext()) {
//				result.next();
//				cnt++;
//			}
//			System.out.println(cnt + " record(s)");
//		} finally {
//
//		}
//
//		// Select from Person: Email=Omar@Omar.Omar
//
//		try {
//			tableName = "Person";
//			strOperator = "and";
//			htblColNameValue = new Hashtable<String, String>();
//			htblColNameValue.put("Email", "Omar@Omar.Omar");
//
//			result = dbEngine.selectFromTable(tableName, htblColNameValue,
//					strOperator);
//			System.out.println("Result has ");
//			int cnt = 0;
//			while (result.hasNext()) {
//				result.next();
//				cnt++;
//			}
//			System.out.println(cnt + " record(s)");
//		} finally {
//
//		}

		// // Select from House: ID=2
		//
		// try{
		// tableName = "House";
		// strOperator = "and";
		// htblColNameValue = new Hashtable<String, String>();
		// htblColNameValue.put("ID", "2");
		//
		// result = dbEngine.selectFromTable(tableName, htblColNameValue,
		// strOperator);
		// System.out.println("Result has ");
		// int cnt = 0;
		// while(result.hasNext()){
		// Record record = (Record) result.next();
		// System.out.println(record.getPageName());
		// cnt ++;
		// }
		// System.out.println(cnt + " record(s)");
		// } finally{
		//
		// }

//		// Select from Person: Name=Omar or Email=Aly@Aly.Aly
//		try {
//			tableName = "Person";
//			strOperator = "or";
//			htblColNameValue = new Hashtable<String, String>();
//			htblColNameValue.put("Name", "Omar");
//			htblColNameValue.put("Email", "Aly@Aly.Aly");
//
//			result = dbEngine.selectFromTable(tableName, htblColNameValue,
//					strOperator);
//			System.out.println("Result has ");
//			int cnt = 0;
//			while (result.hasNext()) {
//				Record record = (Record) result.next();
//				cnt++;
//			}
//			System.out.println(cnt + " record(s)");
//		} finally {
//
//		}
//
//		// Deleting from Person: Name=Omar or Email=Aly@Aly.Aly
//
//		try {
//			tableName = "Person";
//			strOperator = "or";
//			htblColNameValue = new Hashtable<String, String>();
//			htblColNameValue.put("Name", "Omar");
//			 htblColNameValue.put("Email", "Aly@Aly.Aly");
//
//			dbEngine.deleteFromTable(tableName, htblColNameValue, strOperator);
//			
//		} catch (DBEngineException e) {
//			System.out.println(e.getMessage());
//		}

//		// Select All
//
//		try {
//			tableName = "Person";
//			strOperator = "and";
//			htblColNameValue = new Hashtable<String, String>();
//
//			result = dbEngine.selectFromTable(tableName, htblColNameValue,
//					strOperator);
//			System.out.println("Result has ");
//			int cnt = 0;
//			while (result.hasNext()) {
//				Record record = (Record) result.next();
//				cnt++;
//			}
//			System.out.println(cnt + " record(s)");
//		} catch (DBEngineException e) {
//			System.out.println(e.getMessage());
//		}
//
//		// Select from Person: Name=Omar
//
//		try {
//			tableName = "Person";
//			strOperator = "and";
//			htblColNameValue = new Hashtable<String, String>();
//			htblColNameValue.put("Name", "Omar");
//
//			result = dbEngine.selectFromTable(tableName, htblColNameValue,
//					strOperator);
//			System.out.println("Result has ");
//			int cnt = 0;
//			while (result.hasNext()) {
//				result.next();
//				cnt++;
//			}
//			System.out.println(cnt + " record(s)");
//		} finally {
//
//		}

	}
	
	public static void initOmarMussab(DBApp dbEngine){
		String table, key, tableName, strOperator;
		Hashtable<String, String> types, refs, htblColNameValue;
		Iterator result;
		
		try {
			table = "Mussab";
			key = "ID";
			types = new Hashtable<String, String>();
			refs = new Hashtable<String, String>();
			types.put(key, Integer.class.getName());
			types.put("Name", String.class.getName());
			types.put("Email", String.class.getName());

			dbEngine.createTable(table, types, refs, key);
			System.out.println("Creating Person Done!");
		} catch (DBEngineException e) {
			System.out.println(e.getMessage());
		}

		// Create House

		try {
			table = "Omar";
			key = "ID";
			types = new Hashtable<String, String>();
			refs = new Hashtable<String, String>();
			types.put(key, Integer.class.getName());
			types.put("Employee", Integer.class.getName());
			types.put("Address", String.class.getName());
			refs.put("Employee", "Mussab.ID");

			dbEngine.createTable(table, types, refs, key);
			System.out.println("Creating House Done!");
		} catch (DBEngineException e) {
			System.out.println(e.getMessage());
		}
		
		Random rnd = new Random(System.currentTimeMillis());
		String[] names = { "Aly", "Mussab", "Omar", "Mohab", "Ahmed", "Hazem",
				"Momen", "Amr", "Zaky", "Adham" };
		for (int q = 0; q < 1; q++) {
			tableName = "Mussab";
			htblColNameValue = new Hashtable<String, String>();
			htblColNameValue.put("ID", 1 + "");
			String name = names[rnd.nextInt(10)];
			htblColNameValue.put("Name", "1");
			htblColNameValue.put("Email", name + " at " + name + " on " + name);
			try {
				dbEngine.insertIntoTable(tableName, htblColNameValue);
				System.out.println(q);
			} catch (DBEngineException e) {
				System.out.println(e.getMessage());
			}
		}
		
		for (int q = 0; q < 4; q++) {
			tableName = "Omar";
			htblColNameValue = new Hashtable<String, String>();
			htblColNameValue.put("ID", rnd.nextInt() + "");
			String name = names[rnd.nextInt(10)];
			htblColNameValue.put("Employee", "1");
			htblColNameValue.put("Address", name + " at " + name + " on " + name);
			try {
				dbEngine.insertIntoTable(tableName, htblColNameValue);
				System.out.println(q);
			} catch (DBEngineException e) {
				System.out.println(e.getMessage());
			}
		}
	}
	
	public static void init(DBApp dbEngine){
		String table, key, tableName, strOperator;
		Hashtable<String, String> types, refs, htblColNameValue;
		Iterator result;

		// Create Person
		try {
			table = "Person";
			key = "ID";
			types = new Hashtable<String, String>();
			refs = new Hashtable<String, String>();
			types.put(key, Integer.class.getName());
			types.put("Name", String.class.getName());
			types.put("Email", String.class.getName());

			dbEngine.createTable(table, types, refs, key);
			System.out.println("Creating Person Done!");
		} catch (DBEngineException e) {
			System.out.println(e.getMessage());
		}

		// Create House

		try {
			table = "House";
			key = "ID";
			types = new Hashtable<String, String>();
			refs = new Hashtable<String, String>();
			types.put(key, Integer.class.getName());
			types.put("Employee", Integer.class.getName());
			types.put("Address", String.class.getName());
			refs.put("Employee", "Person.ID");

			dbEngine.createTable(table, types, refs, key);
			System.out.println("Creating House Done!");
		} catch (DBEngineException e) {
			System.out.println(e.getMessage());
		}

		// Insert in Person: 1, Aly, Aly@Aly.Aly

		try {
			tableName = "Person";
			htblColNameValue = new Hashtable<String, String>();
			htblColNameValue.put("ID", "1");
			htblColNameValue.put("Name", "Aly");
			htblColNameValue.put("Email", "Aly@Aly.Aly");
			dbEngine.insertIntoTable(tableName, htblColNameValue);
			System.out.println("Inserting Aly Done!");
		} catch (DBEngineException e) {
			System.out.println(e.getMessage());
		}

		// Insert in Person: 1, Mussab, Mussab@Mussab.Mussab

		try {
			tableName = "Person";
			htblColNameValue = new Hashtable<String, String>();
			htblColNameValue.put("ID", "1");
			htblColNameValue.put("Name", "Mussab");
			htblColNameValue.put("Email", "Mussab@Mussab.Mussab");
			dbEngine.insertIntoTable(tableName, htblColNameValue);
			System.out.println("Inserting Mussab Done!");
		} catch (DBEngineException e) {
			System.out.println(e.getMessage());
		}

		// Insert in House: 1, 1, Rehab

		try {
			tableName = "House";
			htblColNameValue = new Hashtable<String, String>();
			htblColNameValue.put("ID", "1");
			htblColNameValue.put("Employee", "1");
			htblColNameValue.put("Address", "Rehab");
			dbEngine.insertIntoTable(tableName, htblColNameValue);
			System.out.println("Inserting Rehab Done!");
		} catch (DBEngineException e) {
			System.out.println(e.getMessage());
		}
		//
		// Insert in House: 2, null, Maadi

		try {
			tableName = "House";
			htblColNameValue = new Hashtable<String, String>();
			htblColNameValue.put("ID", "2");
			htblColNameValue.put("Employee", "null");
			htblColNameValue.put("Address", "Maadi");
			dbEngine.insertIntoTable(tableName, htblColNameValue);
			System.out.println("Inserting Maadi Done!");

		} catch (DBEngineException e) {
			System.out.println(e.getMessage());
		}

		// Insert in House: 1, null, Smouha
		try {
			tableName = "House";
			htblColNameValue = new Hashtable<String, String>();
			htblColNameValue.put("ID", "1");
			htblColNameValue.put("Employee", "null");
			htblColNameValue.put("Address", "Smouha");
			dbEngine.insertIntoTable(tableName, htblColNameValue);
			System.out.println("Inserting Smouha Done!");
		} catch (DBEngineException e) {
			System.out.println(e.getMessage());
		}
		//
		// Insert in House: 3, 1, Tagamo3
		try {
			tableName = "House";
			htblColNameValue = new Hashtable<String, String>();
			htblColNameValue.put("ID", "3");
			htblColNameValue.put("Employee", "1");
			htblColNameValue.put("Address", "Tagamo3");
			dbEngine.insertIntoTable(tableName, htblColNameValue);
			System.out.println("Inserting Tagamo3 Done!");
		} catch (DBEngineException e) {
			System.out.println(e.getMessage());
		}
		//
		// Insert in House: 4, 2, Redmond
		try {
			tableName = "House";
			htblColNameValue = new Hashtable<String, String>();
			htblColNameValue.put("ID", "4");
			htblColNameValue.put("Employee", "2");
			htblColNameValue.put("Address", "Redmond");
			dbEngine.insertIntoTable(tableName, htblColNameValue);
			System.out.println("Inserting Redmond Done!");
		} catch (DBEngineException e) {
			System.out.println(e.getMessage());
		}

		// Insert in Person: 2, Omar, Omar@Omar.Omar

		try {
			tableName = "Person";
			htblColNameValue = new Hashtable<String, String>();
			htblColNameValue.put("ID", "2");
			htblColNameValue.put("Name", "Omar");
			htblColNameValue.put("Email", "Omar@Omar.Omar");
			dbEngine.insertIntoTable(tableName, htblColNameValue);
			System.out.println("Inserting Omar Done!");
		} catch (DBEngineException e) {
			System.out.println(e.getMessage());
		}
		
		// Add 1000 Random records in Person

		Random rnd = new Random(System.currentTimeMillis());
		String[] names = { "Aly", "Mussab", "Omar", "Mohab", "Ahmed", "Hazem",
				"Momen", "Amr", "Zaky", "Adham" };
		for (int q = 0; q < 200; q++) {
			tableName = "House";
			htblColNameValue = new Hashtable<String, String>();
			htblColNameValue.put("ID", rnd.nextInt() + "");
			String name = names[rnd.nextInt(10)];
			htblColNameValue.put("Employee", "1");
			htblColNameValue.put("Address", name + " at " + name + " on " + name);
			try {
				dbEngine.insertIntoTable(tableName, htblColNameValue);
				System.out.println(q);
			} catch (DBEngineException e) {
				System.out.println(e.getMessage());
			}
		}
	}
	
	/*
	 * This method should execute the insertion and the pages should show the
	 * inserted data
	 */
	public static void testDBApp_Insert(DBApp dbEngine) {

		try {
			String tableName = "Employee";

			Hashtable<String, String> htblColNameValue = new Hashtable<String, String>();
			htblColNameValue.put("ID", (new Integer(1)).toString());
			htblColNameValue.put("Name", "Stevenson Morris");
			htblColNameValue.put("Dept", "Software");
			htblColNameValue.put("Start_Date", "12-12-2030");
			dbEngine.insertIntoTable(tableName, htblColNameValue);

			htblColNameValue.put("ID", (new Integer(2)).toString());
			htblColNameValue.put("Name", "John Smith");
			htblColNameValue.put("Dept", "Accounting");
			htblColNameValue.put("Start_Date", "12-08-2030");

			dbEngine.insertIntoTable(tableName, htblColNameValue);

			htblColNameValue.put("ID", (new Integer(3)).toString());
			htblColNameValue.put("Name", "Mary Smith");
			htblColNameValue.put("Dept", "IT");
			htblColNameValue.put("Start_Date", "12-08-2030");

			dbEngine.insertIntoTable(tableName, htblColNameValue);

			htblColNameValue.put("ID", (new Integer(4)).toString());
			htblColNameValue.put("Name", "Tom Robinson");
			htblColNameValue.put("Dept", "Accounting");
			htblColNameValue.put("Start_Date", "12-09-2010");

			dbEngine.insertIntoTable(tableName, htblColNameValue);
			htblColNameValue.put("ID", (new Integer(5)).toString());
			htblColNameValue.put("Name", "Adam Mathew");
			htblColNameValue.put("Dept", "HR");
			htblColNameValue.put("Start_Date", "2-09-2020");

			dbEngine.insertIntoTable(tableName, htblColNameValue);
			htblColNameValue.put("ID", (new Integer(6)).toString());
			htblColNameValue.put("Name", "Andrea Willson");
			htblColNameValue.put("Dept", "IT");
			htblColNameValue.put("Start_Date", "4-10-2013");

			dbEngine.insertIntoTable(tableName, htblColNameValue);

		} catch (DBAppException e) {
			e.printStackTrace();
		}
	}

	/*
	 * This method should throws an exception because there are duplicate
	 * entries with the same primary key
	 */
	public static void testDBApp_DublicateIDInsert(DBApp dbEngine) {

		try {
			String tableName = "Employee";

			Hashtable<String, String> htblColNameValue = new Hashtable<String, String>();
			htblColNameValue.put("ID", (new Integer(1)).toString());
			htblColNameValue.put("Name", "Stevenson Morris");
			htblColNameValue.put("Dept", "Software");
			htblColNameValue.put("Start_Date", "12-12-2030");

			dbEngine.insertIntoTable(tableName, htblColNameValue);

			htblColNameValue.put("ID", (new Integer(2)).toString());
			htblColNameValue.put("Name", "John Smith");
			htblColNameValue.put("Dept", "Accounting");
			htblColNameValue.put("Start_Date", "12-08-2030");

			dbEngine.insertIntoTable(tableName, htblColNameValue);

			htblColNameValue.put("ID", (new Integer(3)).toString());
			htblColNameValue.put("Name", "Mary Smith");
			htblColNameValue.put("Dept", "IT");
			htblColNameValue.put("Start_Date", "12-08-2030");

			dbEngine.insertIntoTable(tableName, htblColNameValue);

			htblColNameValue.put("ID", (new Integer(3)).toString());
			htblColNameValue.put("Name", "Tom Robinson");
			htblColNameValue.put("Dept", "Accounting");
			htblColNameValue.put("Start_Date", "12-09-2010");

			dbEngine.insertIntoTable(tableName, htblColNameValue);

		} catch (DBAppException e) {
			e.printStackTrace();
		}
	}

	/*
	 * This method should throw an exception because the insertion consists of a
	 * column name "End_Date" does not exist
	 */
	public static void testDBApp_NonExistingColumnInsert(DBApp dbEngine) {
		// Test

		try {
			String tableName = "Employee";

			Hashtable<String, String> htblColNameValue = new Hashtable<String, String>();
			htblColNameValue.put("ID", (new Integer(1)).toString());
			htblColNameValue.put("Name", "Stevenson Morris");
			htblColNameValue.put("Dept", "Software");
			htblColNameValue.put("Start_Date", "12-12-2030");

			dbEngine.insertIntoTable(tableName, htblColNameValue);

			htblColNameValue.put("ID", (new Integer(2)).toString());
			htblColNameValue.put("Name", "John Smith");
			htblColNameValue.put("Dept", "Accounting");
			htblColNameValue.put("End_Date", "12-08-2030");

			dbEngine.insertIntoTable(tableName, htblColNameValue);

		} catch (DBAppException e) {
			e.printStackTrace();
		}
	}

	/*
	 * This method should result on the deletions of all the records that have
	 * the Dept value equals IT or Accounting; this entries should have a
	 * deletion marker
	 */
	public static void testDBApp_DeleteOR(DBApp dbEngine) {

		try {
			String tableName = "Employee";
			Hashtable<String, String> htblColNameValue = new Hashtable<String, String>();
			htblColNameValue.put("Dept", "IT");
			htblColNameValue.put("Dept", "Accounting");

			dbEngine.deleteFromTable(tableName, htblColNameValue, "OR");

		} catch (DBAppException e) {
			e.printStackTrace();
		}
	}

	/*
	 * This method should result on the deletions of all the record that has
	 * this two conditions satisfied and this record should have a deletion
	 * marker.
	 */
	public static void testDBApp_DeleteAND(DBApp dbEngine) {

		try {
			String tableName = "Employee";
			Hashtable<String, String> htblColNameValue = new Hashtable<String, String>();
			htblColNameValue.put("Name", "John Smith");
			htblColNameValue.put("Dept", "Accounting");

			dbEngine.deleteFromTable(tableName, htblColNameValue, "AND");

		} catch (DBAppException e) {
			e.printStackTrace();
		}
	}

	/*
	 * This method should print all the rows numbers of the records thatmatch
	 * the selection
	 */
	public static void testDBApp_Select(DBApp dbEngine) {

		try {
			String tableName = "Employee";
			Hashtable<String, String> htblColNameValue = new Hashtable<String, String>();
			htblColNameValue.put("Dept", "Accounting");

			Iterator iter = dbEngine.selectFromTable(tableName,
					htblColNameValue, "OR");
			while (iter.hasNext()) {
				// keep printing rows numbers

			}

		} catch (DBAppException e) {
			e.printStackTrace();
		}
	}

	/*
	 * This method should print no records found since no records matched the
	 * selection
	 */
	public static void testDBApp_SelectNoRecordsFound(DBApp dbEngine) {

		try {
			String tableName = "Employee";
			Hashtable<String, String> htblColNameValue = new Hashtable<String, String>();
			htblColNameValue.put("Dept", "Management");

			Iterator iter = dbEngine.selectFromTable(tableName,
					htblColNameValue, "OR");
			if (iter == null) {
				System.out.println("No records found");
				return;
			}
			while (iter.hasNext()) {
				// keep printing rows numbers

			}

		} catch (DBAppException e) {
			e.printStackTrace();
		}
	}

	/*
	 * This method should throw an exception because the selectionis from a
	 * table "Employer" that does not exist.
	 */
	public static void testDBApp_SelectNoTable(DBApp dbEngine) {

		try {
			String tableName = "Employer";
			Hashtable<String, String> htblColNameValue = new Hashtable<String, String>();
			htblColNameValue.put("Dept", "Management");

			Iterator iter = dbEngine.selectFromTable(tableName,
					htblColNameValue, "OR");
			while (iter.hasNext()) {
				// keep printing rows numbers

			}

		} catch (DBAppException e) {
			e.printStackTrace();
		}
	}

	/*
	 * This method should create an index on the Dept attribute
	 */
	public static void testDBApp_CreateIndex(DBApp dbEngine) {

		try {
			String tableName = "Employee";

			dbEngine.createIndex(tableName, "Dept");
		} catch (DBAppException e) {
			e.printStackTrace();
		}
	}

	/*
	 * This method should return an exception since the requested index is on a
	 * variable that does not exist
	 */

	public static void testDBApp_NonExistingColumnCreateIndex(DBApp dbEngine) {

		try {
			String tableName = "Employee";

			dbEngine.createIndex(tableName, "Experience");
		} catch (DBAppException e) {
			e.printStackTrace();
		}
	}

	/*
	 * This method should create two tables Employee and Department where the
	 * Metadata file should hold all the information on these tables.
	 */
	public static void testDBApp_Meta() {

		try {
			DBApp dbEngine = DBApp.getInstance();
			Hashtable<String, String> htblColNameType = new Hashtable<String, String>();
			htblColNameType.put("ID", "java.lang.Integer");
			htblColNameType.put("Name", "java.lang.String");
			htblColNameType.put("Location", "java.lang.String");

			Hashtable<String, String> htblColNameRefs = new Hashtable<String, String>();
			htblColNameRefs.put("ID", "null");
			htblColNameRefs.put("Name", "null");
			htblColNameRefs.put("Location", "null");

			dbEngine.createTable("Department", htblColNameType,
					htblColNameRefs, "ID");

			String tableName = "Employee";
			htblColNameType = new Hashtable<String, String>();
			htblColNameType.put("ID", "java.lang.Integer");
			htblColNameType.put("Name", "java.lang.String");
			htblColNameType.put("Dept", "java.lang.String");
			htblColNameType.put("Start_Date", "java.util.Date");

			htblColNameRefs = new Hashtable<String, String>();
			htblColNameRefs.put("ID", "null");
			htblColNameRefs.put("Name", "null");
			htblColNameRefs.put("Dept", "Department.ID");
			htblColNameRefs.put("Start_Date", "null");

			dbEngine.createTable(tableName, htblColNameType, htblColNameRefs,
					"ID");

			dbEngine.saveAll();

		} catch (DBAppException e) {
			e.printStackTrace();
		}
	}

	/*
	 * This method should throw an exception because the creation of the first
	 * table specifies a column to be the primary key that does not exist
	 */
	public static void testDBApp_MetaError() {

		try {
			DBApp dbEngine = DBApp.getInstance();

			Hashtable<String, String> htblColNameType = new Hashtable<String, String>();
			htblColNameType.put("ID", "java.lang.Integer");
			htblColNameType.put("Name", "java.lang.String");
			htblColNameType.put("Location", "java.lang.String");

			Hashtable<String, String> htblColNameRefs = new Hashtable<String, String>();
			htblColNameRefs.put("ID", "null");
			htblColNameRefs.put("Name", "null");
			htblColNameRefs.put("Location", "null");

			dbEngine.createTable("Department", htblColNameType,
					htblColNameRefs, "ID");

			String tableName = "Employee";
			htblColNameType = new Hashtable<String, String>();
			htblColNameType.put("ID", "java.lang.Integer");
			htblColNameType.put("Name", "java.lang.String");
			htblColNameType.put("Dept", "java.lang.String");
			htblColNameType.put("Start_Date", "java.util.Date");

			htblColNameRefs = new Hashtable<String, String>();
			htblColNameRefs.put("ID", "null");
			htblColNameRefs.put("Name", "null");
			htblColNameRefs.put("Dept", "Department.ID");
			htblColNameRefs.put("Start_Date", "null");

			dbEngine.createTable(tableName, htblColNameType, htblColNameRefs,
					"Age");

			dbEngine.saveAll();

		} catch (DBAppException e) {
			e.printStackTrace();
		}
	}

	/*
	 * This method should throw an exception because there can't be duplicate
	 * tables
	 */
	public static void testDBApp_MetaDuplicate() {

		try {
			DBApp dbEngine = DBApp.getInstance();

			Hashtable<String, String> htblColNameType = new Hashtable<String, String>();

			htblColNameType.put("ID", "java.lang.Integer");
			htblColNameType.put("Name", "java.lang.String");
			htblColNameType.put("Location", "java.lang.String");

			Hashtable<String, String> htblColNameRefs = new Hashtable<String, String>();

			htblColNameRefs.put("ID", "null");
			htblColNameRefs.put("Name", "null");
			htblColNameRefs.put("Location", "null");

			dbEngine.createTable("Employee", htblColNameType, htblColNameRefs,
					"ID");

			String tableName = "Employee";
			htblColNameType = new Hashtable<String, String>();
			htblColNameType.put("ID", "java.lang.Integer");
			htblColNameType.put("Name", "java.lang.String");
			htblColNameType.put("Dept", "java.lang.String");
			htblColNameType.put("Start_Date", "java.util.Date");

			htblColNameRefs = new Hashtable<String, String>();
			htblColNameRefs.put("ID", "null");
			htblColNameRefs.put("Name", "null");
			htblColNameRefs.put("Dept", "Department.ID");
			htblColNameRefs.put("Start_Date", "null");

			dbEngine.createTable(tableName, htblColNameType, htblColNameRefs,
					"ID");

			dbEngine.saveAll();

		} catch (DBAppException e) {
			e.printStackTrace();
		}
	}

	/*
	 * This method should throw an exception because the second table is
	 * referencing another table "Company" that does not exist.
	 */
	public static void testDBApp_MetaRefrence() {

		try {
			DBApp dbEngine = DBApp.getInstance();

			// Second table
			Hashtable<String, String> htblColNameType = new Hashtable<String, String>();

			htblColNameType.put("ID", "java.lang.Integer");
			htblColNameType.put("Name", "java.lang.String");
			htblColNameType.put("Location", "java.lang.String");

			Hashtable<String, String> htblColNameRefs = new Hashtable<String, String>();
			htblColNameRefs.put("ID", "null");
			htblColNameRefs.put("Name", "null");
			htblColNameRefs.put("Location", "null");

			dbEngine.createTable("Department", htblColNameType,
					htblColNameRefs, "ID");

			String tableName = "Employee";
			htblColNameType = new Hashtable<String, String>();

			htblColNameType.put("ID", "java.lang.Integer");
			htblColNameType.put("Name", "java.lang.String");
			htblColNameType.put("Dept", "java.lang.String");
			htblColNameType.put("Start_Date", "java.util.Date");

			htblColNameRefs = new Hashtable<String, String>();
			htblColNameRefs.put("ID", "null");
			htblColNameRefs.put("Name", "null");
			htblColNameRefs.put("Dept", "Company.ID");
			htblColNameRefs.put("Start_Date", "null");

			dbEngine.createTable(tableName, htblColNameType, htblColNameRefs,
					"ID");

			dbEngine.saveAll();

		} catch (DBAppException e) {
			e.printStackTrace();
		}
	}

	public static void ourCreate(DBApp dbEngine) throws DBEngineException {
		String table = "Employee";
		String key = "ID";
		Hashtable<String, String> types = new Hashtable<String, String>();
		Hashtable<String, String> refs = new Hashtable<String, String>();
		types.put(key, Integer.class.getName());
		types.put("Name", String.class.getName());
		types.put("Dept", String.class.getName());
		types.put("Start_Date", Date.class.getName());

		dbEngine.createTable(table, types, refs, key);
	}

	public static void ourInsert(DBApp dbEngine) throws DBEngineException {
		testDBApp_Insert(dbEngine);

		String tableName = "Employee";

		Hashtable<String, String> htblColNameValue = new Hashtable<String, String>();
		htblColNameValue.put("ID", (new Integer(3)).toString());
		htblColNameValue.put("Name", "Stevenson Morris");
		htblColNameValue.put("Dept", "Software");
		htblColNameValue.put("Start_Date", "12-12-2030");
		dbEngine.insertIntoTable(tableName, htblColNameValue);

		for (int i = 0; i < 200; i++) {
			htblColNameValue = new Hashtable<String, String>();
			htblColNameValue.put("ID", (new Integer(1)).toString());
			htblColNameValue.put("Name", "Stevenson Morris");
			htblColNameValue.put("Dept", "Software");
			htblColNameValue.put("Start_Date", "12-12-2030");
			dbEngine.insertIntoTable(tableName, htblColNameValue);
		}
	}

}
