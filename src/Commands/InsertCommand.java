package Commands;

import java.util.Hashtable;
import java.util.Vector;

import Abstracts.Step;
import Exceptions.DBEngineException;
import Interfaces.DBNonQueryCommand;
import Steps.PageRead;
import Steps.PageWrite;
import Steps.TupleInsert;
import Transactions.Transaction;
import Transactions.TransactionManager;
import Utilities.Page;
import Utilities.PageID;
import Utilities.Table;

public class InsertCommand implements DBNonQueryCommand {
	private String strTableName;
	private Hashtable<String, String> htblColNameValue;

	public InsertCommand(String strTableName,
			Hashtable<String, String> htblColNameValue) {
		this.strTableName = strTableName;
		this.htblColNameValue = htblColNameValue;
	}

	// / TransactionManager.getTransactionManager().getLogManager()
	// / Same with BufferManager
	public void execute() throws DBEngineException {
		Table table = Table.getInstance(strTableName);
		int intPageID = table.getNumberOfPages() - 1;
		PageID pageID = new PageID(intPageID + "", strTableName);
		Page page = table.getPage(intPageID);
		
		Vector<Step> steps = new Vector<Step>();
		// Add PageRead
		Hashtable<String, Object> HT = new Hashtable<String, Object>();
		HT.put("pageID", pageID);
		HT.put("page", page);
		HT.put("modify", true);
		PageRead readStep = new PageRead();
		readStep.init(HT);
		steps.add(readStep);
		
		// Insert Step
		HT.clear();
		HT.put("recordInput", htblColNameValue);
		HT.put("table", table);
		HT.put("pageID", pageID);
		TupleInsert insertStep = new TupleInsert();
		insertStep.init(HT);
		steps.add(insertStep);
		
		// Page Write
		HT.clear();
		HT.put("pageID", pageID);
		HT.put("page", table.getPage(intPageID));
		PageWrite writeStep = new PageWrite();
		writeStep.init(HT);
		steps.add(writeStep);
		
		Transaction transaction = new Transaction();
		transaction.init(TransactionManager.getInstance().getBufferManager(), TransactionManager.getInstance().getLogManager(), steps);
		transaction.execute();
	}
}
