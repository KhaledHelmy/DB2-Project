package Steps;

import java.util.Hashtable;

import Transactions.Transaction;
import Utilities.PageID;
import Utilities.Record;
import Utilities.Table;
import Abstracts.Step;
import Exceptions.DBEngineException;

public class TupleUpdate extends Step {

	@Override
	public void execute(Transaction parentTransaction) throws DBEngineException {
		Record r = (Record) values.get("record");
		String pageName = r.getPageName();
		String tableName = pageName.split("_")[0];
		Hashtable<String, String> newVals = (Hashtable<String, String>) values
				.get("newVals");
		for (String colName : newVals.keySet()) {
			String valOld = r.getValue(colName);
			r.setColumnValue(colName, newVals.get(colName));
			String valNew = newVals.get(colName);
			parentTransaction.getLogManager().recordUpdate(
					parentTransaction.getID(), (PageID) values.get("pageID"),
					r.getValue(Table.getInstance(tableName).getKeyColName()),
					colName, valOld, valNew);
		}
	}

}
