package Components.BufferManager;

import java.util.PriorityQueue;

import Interfaces.BufferManagerInterface;
import Utilities.Page;
import Utilities.PageID;
import Utilities.Memory;

public class BufferManager implements BufferManagerInterface {
	private static BufferManager bufferManager = new BufferManager();  
	
	private static final int SIZE = 1000;
	private Page[] memory;
	
	private int minEmptySlotSize, maxUsedSlotSize;
	private PriorityQueue<Integer> emptySlots, usedSlots;
	
	private BufferManager() {
		memory = new Page[SIZE];
	}
	
	public static BufferManager getInstance() {
		return bufferManager;
	}

	public void init() {
		setSlotSizes();
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
