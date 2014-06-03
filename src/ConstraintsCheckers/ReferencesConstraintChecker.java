package ConstraintsCheckers;

import java.util.Collection;
import java.util.Hashtable;

import Exceptions.CheckerException;
import Interfaces.ConstraintsChecker;
import Utilities.Column;
import Utilities.Table;

public class ReferencesConstraintChecker implements ConstraintsChecker{

	@Override
	public boolean check(String tableName, Hashtable<String, String> params)
			throws CheckerException {
		Table table = Table.getInstance(tableName);
		Collection<String> columnNames = table.getColsName();
		
		for(String colName : columnNames){
			Column column = table.getColumn(colName);
			if(column.HasReference()){
				String value = params.get(colName);
				if(value == null || value.equalsIgnoreCase("null")){
					continue;
				}
				
				String reference = column.getReference();
				String[] tmp = reference.trim().split("\\.");
				for(int i = 0; i < tmp.length; i++){
					tmp[i] = tmp[i].trim();
				}
				
				Table referencedTable = Table.getInstance(tmp[0]);
				String referencedColumn = tmp[1];
				
				
				
				if(!referencedTable.valueExists(referencedColumn, value)){
					throw new CheckerException("References Error: Column " 
												+ referencedColumn 
												+ " in Table " 
												+ referencedTable.getName() 
												+ " doesn't contain value "
												+ value);
				}
			}
		}
		
		return true;
	}

}
