package Components.BufferManager;

import java.util.PriorityQueue;

import Interfaces.BufferManagerInterface;
import Utilities.Page;
import Utilities.PageID;
import Utilities.Memory;

public class BufferManager implements BufferManagerInterface {
	private static BufferManager bufferManager = null;  
	
	private static final int SIZE = 50;
	private Page[] memory;
	
	private int minEmptySlotSize, maxUsedSlotSize;
	private PriorityQueue<Integer> emptySlots, usedSlots;
	
	private BufferManager() {
		// Ask Fadwa about constructor and init
	}
	
	public static BufferManager getBufferManager() {
		if (bufferManager == null)
			bufferManager = new BufferManager();
		return bufferManager;
	}

	public void init() {
		memory = new Page[SIZE];
		setSlotSizes();
		emptySlots = new PriorityQueue<Integer>();
		usedSlots = new PriorityQueue<Integer>();
		for (int i=0; i<SIZE; i++) {
			emptySlots.add(new Integer(i));
		}
		// Run thread
	}

	public synchronized void read(PageID pageID, Page page, boolean bModify) {
		
	}

	public synchronized void write(PageID pageID, Page page) {
		
	}
	
	private void setSlotSizes() {
		setMinEmptySlotSize();
		setMaxUsedSlotSize();
	}

	private void setMinEmptySlotSize() {
		String property = Memory.getMemory().getProperty("MinimumEmptyBufferSlots");
		minEmptySlotSize = Integer.parseInt(property);
	}
	
	private void setMaxUsedSlotSize() {
		String property = Memory.getMemory().getProperty("MaximumUsedBufferSlots");
		maxUsedSlotSize = Integer.parseInt(property);
	}
}
