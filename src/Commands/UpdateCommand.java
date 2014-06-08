package Commands;

import java.util.Hashtable;
import java.util.Vector;

import Abstracts.Step;
import Exceptions.DBEngineException;
import Interfaces.DBNonQueryCommand;
import Steps.PageRead;
import Steps.PageWrite;
import Steps.TupleUpdate;
import Transactions.Transaction;
import Transactions.TransactionManager;
import Utilities.Page;
import Utilities.PageID;
import Utilities.Table;

public class UpdateCommand implements DBNonQueryCommand {
	private String strTableName, strOperator;
	private Hashtable<String, String> htblColNameValueUpdate,
			htblColNameValueCondition;

	public UpdateCommand(String strTableName,
			Hashtable<String, String> htblColNameValue,
			Hashtable<String, String> htblColNameValueCondition,
			String strOperator) {
		this.strTableName = strTableName;
		htblColNameValueUpdate = htblColNameValue;
		this.htblColNameValueCondition = htblColNameValueCondition;
		this.strOperator = strOperator;
	}

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
		HT.put("modify", new Boolean(true));
		PageRead readStep = new PageRead();
		readStep.init(HT);
		steps.add(readStep);
		
		// Insert Step
		HT.put("recordInput", htblColNameValueUpdate);
		HT.put("condition", htblColNameValueCondition);
		HT.put("table", table);
		HT.put("pageID", pageID);
		TupleUpdate updateStep = new TupleUpdate();
		updateStep.init(HT);
		steps.add(updateStep);
		
		// Page Write
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
