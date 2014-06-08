package Steps;

import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;

import Abstracts.Step;
import Exceptions.DBEngineException;
import Transactions.Transaction;
import Utilities.PageID;
import Utilities.Record;
import Utilities.Table;

public class TupleUpdate extends Step {

	@Override
	public void execute(Transaction parentTransaction) throws DBEngineException {
		Table table = (Table) values.get("table");
		Hashtable<String, String> newVals = (Hashtable<String, String>) values
				.get("recordInput");
		List<Hashtable<String, String>> iter1 = table.updateRecord(newVals,
				(Hashtable<String, String>) values.get("condition"),
				(String) values.get("operator"));
		Iterator<Hashtable<String, String>> iter = iter1.iterator();
		while (iter.hasNext()) {
			Hashtable<String, String> r = iter.next();
			for (String colName : newVals.keySet()) {
				String valOld = r.get(colName);
				// r.setColumnValue(colName, newVals.get(colName));
				String valNew = newVals.get(colName);
				parentTransaction.getLogManager().recordUpdate(
						parentTransaction.getID(),
						(PageID) values.get("pageID"),
						r.get(Table.getInstance(table.getName())
								.getKeyColName()), colName, valOld, valNew);
			}
		}
	}

}
