package Interfaces;

import Utilities.Page;
import Utilities.PageID;

public interface BufferManagerInterface {
	public void init(); 
	public void read(PageID pageID, Page page, boolean bModify); 
	public void write(PageID pageID, Page page);
	public void runLRU();
}
