package Steps;

import Transactions.Transaction;

public abstract class Step {
	public abstract void execute(Transaction parentTransaction);
}
