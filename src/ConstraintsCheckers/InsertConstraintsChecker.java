package ConstraintsCheckers;

import java.util.Hashtable;

import Exceptions.CheckerException;
import Factories.ConstraintsCheckerFactory;
import Interfaces.ConstraintsChecker;

public class InsertConstraintsChecker implements ConstraintsChecker{
	
	public boolean check(String tableName, Hashtable<String, String> params) throws CheckerException{
		ConstraintsChecker primaryKeyCheck = ConstraintsCheckerFactory.getInstance(ConstraintsCheckersType.PrimaryKeyConstraintChecker);
		if(!primaryKeyCheck.check(tableName, params)){
			throw new CheckerException("Primary Key Exception");
		}
		
		ConstraintsChecker refsCheck = ConstraintsCheckerFactory.getInstance(ConstraintsCheckersType.ReferencesConstraintChecker);
		if(!refsCheck.check(tableName, params)){
			throw new CheckerException("Reference Key Exception");
		}
		
//		ConstraintsChecker colValueCheck = ConstraintsCheckerFactory.getInstance(ConstraintsCheckersType.InsertedColumnValueTypeChecker);
//		if(!colValueCheck.check(tableName, params)){
//			throw new CheckerException("Inserted column value(s) are not compatible with column type");
//		}
		
		return true;
	}
}
