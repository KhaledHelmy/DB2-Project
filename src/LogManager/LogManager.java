package LogManager;

import java.util.Hashtable;

public interface LogManager {
	public void init();

	public void flushLog();

	public void recordStart(String strTransID);

	public void recordUpdate(String strTransID, /* PageID page, */
			String strKeyValue, String strColName, Object objOld, Object objNew);

	public void recordInsert(String strTransID,
	/* PageID page, */
	Hashtable<String, String> htblColValues);

	public void recordDelete(String strTransID, /*PageID page,*/
			String strKeyValue, Hashtable<String, String> htblColValues);

	public void recordCommit(String strTransID);
}
