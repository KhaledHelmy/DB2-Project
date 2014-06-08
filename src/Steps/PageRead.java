package Steps;

import Transactions.Transaction;
import Utilities.Page;
import Utilities.PageID;
import Abstracts.Step;
import BufferManager.BufferManager;
import Exceptions.DBEngineException;

public class PageRead extends Step {

	@Override
	public void execute(Transaction parentTransaction) throws DBEngineException {
		BufferManager bm = parentTransaction.getBufferManager();
		bm.read((PageID) values.get("pageID"), (Page) values.get("page"),
				(Boolean) values.get("modify"));
	}

}
