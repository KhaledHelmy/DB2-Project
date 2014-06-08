package Transactions;

import java.util.Iterator;
import java.util.Vector;

import BufferManager.BufferManager;
import LogManager.LogManager;
import Steps.Step;

public class Transaction {

	private BufferManager bufferManager;
	private LogManager logManager;
	private Vector<Step> steps;
	private String ID;

	public String getID() {
		return ID;
	}

	public void setID(String ID) {
		this.ID = ID;
	}

	public BufferManager getBufferManager() {
		return bufferManager;
	}

	public void setBufferManager(BufferManager bufferManager) {
		this.bufferManager = bufferManager;
	}

	public LogManager getLogManager() {
		return logManager;
	}

	public void setLogManager(LogManager logManager) {
		this.logManager = logManager;
	}

	public Vector<Step> getSteps() {
		return steps;
	}

	public void setSteps(Vector<Step> steps) {
		this.steps = steps;
	}

	public Transaction() {
		setID(System.currentTimeMillis() + "" + Math.random());
	}

	public void init(BufferManager bufManager, LogManager logManager,
			Vector<Step> vSteps) {
		setBufferManager(bufManager);
		setLogManager(logManager);
		setSteps(vSteps);
	}

	public void kill() {
		setBufferManager(null);
		setLogManager(null);
		setSteps(null);
	}

	public void execute() {
		new Thread() {
			public void run() {
				getLogManager().recordStart(getID());
				for (Iterator<Step> it = getSteps().iterator(); it.hasNext();) {
					Step step = it.next();
					// TODO step.execute(this);
				}

				getLogManager().recordCommit(getID());
				getLogManager().flushLog();
				kill();
			}
		}.start();
	}
}
