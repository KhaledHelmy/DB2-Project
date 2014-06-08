package Steps;

import Transactions.Transaction;
import Utilities.Page;
import Utilities.PageID;
import Abstracts.Step;
import Components.BufferManager.BufferManager;
import Exceptions.DBEngineException;

public class PageWrite extends Step {

	@Override
	public void execute(Transaction parentTransaction) throws DBEngineException {
		BufferManager bm = parentTransaction.getBufferManager();
		bm.write((PageID) values.get("pageID"), (Page) values.get("page"));
	}

}
