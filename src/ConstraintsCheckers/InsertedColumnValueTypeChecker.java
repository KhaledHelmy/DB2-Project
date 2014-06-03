package ConstraintsCheckers;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Enumeration;
import java.util.Hashtable;

import Exceptions.CheckerException;
import Interfaces.ConstraintsChecker;
import Utilities.Table;

public class InsertedColumnValueTypeChecker implements ConstraintsChecker{
	@Override
	public boolean check(String tableName, Hashtable<String, String> params)
			throws CheckerException {
		Table table = Table.getInstance(tableName);
		Enumeration<String> columnNames = params.keys();
		while(columnNames.hasMoreElements()){
			String colName = (String) columnNames.nextElement();
			String columnTypeName = table.getColumn(colName).getColType();
			String value = params.get(colName);
			
			if(value.equalsIgnoreCase("null")){
				return true;
			}
			
			try{
				Class<?> type = Class.forName(columnTypeName);
				Method method = type.getDeclaredMethod("valueOf", String.class);
				
				Object obj = method.invoke(null, value);
			} catch(InvocationTargetException e){
				throw new CheckerException(value + " is not of type " + columnTypeName);
			} catch(ClassNotFoundException e){
				throw new CheckerException("Internal Error: Class not found");
			} catch(NoSuchMethodException e){
				throw new CheckerException("Internal Error: Method not found");
			} catch (IllegalAccessException e) {
				throw new CheckerException("Internal Error: Method not accessable");
			} catch (IllegalArgumentException e) {
				throw new CheckerException("Internal Error: Column type is invalid");
			}
		}
		return true;
	}
}
