package Steps;

import Utilities.Page;
import Utilities.PageID;
import Utilities.Record;
import Utilities.Table;
import Transactions.Transaction;
import Abstracts.Step;

public class TupleDelete extends Step {

	@Override
	public void execute(Transaction parentTransaction) {
		Record r = (Record) values.get("record");
		int pageNum = r.getPageNumber();
		String pageName = r.getPageName();
		String tableName = pageName.split("_")[0].substring(5);
		Page p = Table.getInstance(tableName).getPage(pageNum);
		p.deleteRecord(r.getRowNumber());
		parentTransaction.getLogManager().recordDelete(parentTransaction.getID(),(PageID)values.get("pageID"),r.getValue(Table.getInstance(tableName).getKeyColName()),r.getHtblColNameValue());
	}

}
