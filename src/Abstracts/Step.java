package Abstracts;

import java.util.Hashtable;

import Exceptions.DBEngineException;
import Utilities.Page;
import Utilities.PageID;
import Utilities.Record;
import Transactions.Transaction;

public abstract class Step {
	protected Hashtable<String, Object> values;

	public abstract void execute(Transaction parentTransaction)
			throws DBEngineException;

	public void init(Hashtable<String, Object> param) {
		values = param;
	}
}
