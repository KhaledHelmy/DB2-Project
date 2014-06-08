package Steps;

import java.util.Hashtable;

import Utilities.PageID;
import Utilities.Record;
import Transactions.Transaction;
import Abstracts.Step;
import Exceptions.DBEngineException;

public class TupleInsert extends Step {

	@Override
	public void execute(Transaction parentTransaction) throws DBEngineException {
		Record r = Record.createRecord((String) values.get("recordInput"),
				(String) values.get("pageName"),
				(Integer) values.get("insertionID"));
		parentTransaction.getLogManager().recordInsert(
				parentTransaction.getID(), (PageID) values.get("pageID"),
				r.getHtblColNameValue());
	}
}
