
package Interfaces;

import java.util.Hashtable;

import Exceptions.CheckerException;

public interface ConstraintsChecker {
	public boolean check(String tableName, Hashtable<String, String> params) throws CheckerException;
}
