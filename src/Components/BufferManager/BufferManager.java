package Components.BufferManager;

import java.util.PriorityQueue;

import Interfaces.BufferManagerInterface;
import Utilities.Page;
import Utilities.PageID;

public class BufferManager implements BufferManagerInterface {
	private static BufferManager bufferManager = new BufferManager();  
	
	private static final int SIZE = 1000;
	private int minEmptySlotSize, maxUsedSlotSize;
	private PriorityQueue<Integer> emptySlots, usedSlots;
	private Page[] memory;
	
	private BufferManager() {
		
	}
	
	public static BufferManager getInstance() {
		return bufferManager;
	}

	public void init() {

	}

	public synchronized void read(PageID pageID, Page page, boolean bModify) {
		
	}

	public synchronized void write(PageID pageID, Page page) {
		
	}
}
