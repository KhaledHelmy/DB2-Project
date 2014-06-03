package Factories;

import ConstraintsCheckers.ConstraintsCheckersType;
import ConstraintsCheckers.InsertConstraintsChecker;
import ConstraintsCheckers.InsertedColumnValueTypeChecker;
import ConstraintsCheckers.PrimaryKeyConstraintChecker;
import ConstraintsCheckers.ReferencesConstraintChecker;
import Interfaces.ConstraintsChecker;

public class ConstraintsCheckerFactory{
	
	private static final ConstraintsChecker primaryKeyConstraintChecker = 
			new PrimaryKeyConstraintChecker();
	
	private static final ConstraintsChecker insertConstraintChecker = 
			new InsertConstraintsChecker();
	
	private static final ConstraintsChecker referencesConstraintChecker = 
			new ReferencesConstraintChecker();										
	
	private static final ConstraintsChecker insertColumnValueTypeChecker =
			new InsertedColumnValueTypeChecker();
	
	public static ConstraintsChecker getInstance(ConstraintsCheckersType constraintType){
		switch(constraintType){
			case InsertConstraintChecker:
				return insertConstraintChecker;
			
			case PrimaryKeyConstraintChecker:
				return primaryKeyConstraintChecker;
				
			case ReferencesConstraintChecker:
				return referencesConstraintChecker;
				
			case InsertedColumnValueTypeChecker:
				return insertColumnValueTypeChecker;
		}
		
		return null;
	}
}
