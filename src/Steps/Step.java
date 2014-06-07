package Steps;

import Utilities.Transaction;

public abstract class Step {
	public abstract void execute(Transaction parentTransaction);
}
