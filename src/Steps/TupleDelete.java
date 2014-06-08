package Steps;

import java.util.Hashtable;

import Abstracts.Step;
import Transactions.Transaction;
import Utilities.PageID;
import Utilities.Table;

public class TupleDelete extends Step {

	@Override
	public void execute(Transaction parentTransaction) {
		Table table = (Table) values.get("table");
		table.deleteRecords((Hashtable<String, String>) values.get("recordInput"), (String) values.get("operator"));
		
		parentTransaction.getLogManager().recordDelete(
				parentTransaction.getID(), (PageID) values.get("pageID"),
				table.getKeyColName(),
				(Hashtable<String, String>) values.get("recordInput"));
	}

}
