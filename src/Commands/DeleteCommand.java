package Commands;

import java.util.Hashtable;
import java.util.Vector;

import Abstracts.Step;
import Exceptions.DBEngineException;
import Interfaces.DBNonQueryCommand;
import Steps.PageRead;
import Steps.PageWrite;
import Steps.TupleDelete;
import Transactions.Transaction;
import Transactions.TransactionManager;
import Utilities.Page;
import Utilities.PageID;
import Utilities.Table;

public class DeleteCommand implements DBNonQueryCommand{
	private String strTableName, strOperator;
	private Hashtable<String, String> htblColNameValue;
	
	public DeleteCommand(	String strTableName, 
							Hashtable<String, String> htblColNameValue,
							String strOperator){
		this.strTableName = strTableName;
		this.strOperator = strOperator;
		this.htblColNameValue = htblColNameValue;
	}
	
	public void execute() throws DBEngineException{
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
		
		// Delete Step
		HT.put("recordInput", htblColNameValue);
		HT.put("table", table);
		HT.put("pageID", pageID);
		HT.put("operator", strOperator);
		TupleDelete deleteStep = new TupleDelete();
		deleteStep.init(HT);
		steps.add(deleteStep);
		
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
