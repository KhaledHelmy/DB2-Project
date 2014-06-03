package DataBase;
import java.util.Date;
import java.util.Hashtable;

import Exceptions.DBEngineException;

public class DBAppTest {
	public static void main(String[] args) {
		DBApp app = DBApp.getInstance();
		String table = "Employee";
		String key = "ID";
		Hashtable<String, String> types = new Hashtable<String, String>();
		Hashtable<String, String> refs = new Hashtable<String, String>();
		types.put(key, Integer.class.getName());
		types.put("Name", String.class.getName());
		types.put("Dept", String.class.getName());
		types.put("Start_Date", Date.class.getName());
		refs.put("Dept", "Department.ID");
		try {
			app.createTable(table, types, refs, key);
			app.saveAll();
		} catch (DBEngineException e) {
			e.printStackTrace();
		}
	}
}
