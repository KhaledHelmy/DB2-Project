package ConstraintsCheckers;

import java.util.Hashtable;

import Interfaces.ConstraintsChecker;
import Utilities.Table;

public class PrimaryKeyConstraintChecker implements ConstraintsChecker{
	public boolean check(String tableName, Hashtable<String, String> params){
		Table table = Table.getInstance(tableName);
		String primaryKeyName = table.getKeyColName();
		String value = params.get(primaryKeyName);
		
		if(value.equalsIgnoreCase("null")){
			return false;
		}
		if(table.valueExists(primaryKeyName, value)){
			return false;
		}
		return true;
	}
}
