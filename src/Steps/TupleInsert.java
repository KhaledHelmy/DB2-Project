package Steps;

import java.util.Hashtable;

import Abstracts.Step;
import Exceptions.DBEngineException;
import Transactions.Transaction;
import Utilities.PageID;
import Utilities.Table;

public class TupleInsert extends Step {

	@Override
	public void execute(Transaction parentTransaction) throws DBEngineException {
		Table table = (Table) values.get("table");
		table.insertRecord((Hashtable<String, String>) values.get("recordInput"));
		parentTransaction.getLogManager().recordInsert(
				parentTransaction.getID(), (PageID) values.get("pageID"),
				(Hashtable<String, String>) values.get("recordInput"));
	}
}
