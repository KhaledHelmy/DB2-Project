package Components.BufferManager;

import java.util.PriorityQueue;

import Interfaces.BufferManagerInterface;
import Utilities.Page;
import Utilities.PageID;
import Utilities.Memory;

public class BufferManager implements BufferManagerInterface {
	private static BufferManager bufferManager = null;  
	private BufferUpdate bufferUpdate;
	
	private int size;
	private Page[] memory;
	
	private int minEmptySlotSize, maxUsedSlotSize;
	private PriorityQueue<Integer> emptySlots, usedSlots;
	
	private BufferManager() {
		bufferUpdate = new BufferUpdate();
		bufferUpdate.start();
	}
	
	public static BufferManager getBufferManager() {
		if (bufferManager == null)
			bufferManager = new BufferManager();
		return bufferManager;
	}

	public void init() {
		setSlotSizes();
		size = minEmptySlotSize + maxUsedSlotSize;
		memory = new Page[size];
		emptySlots = new PriorityQueue<Integer>();
		usedSlots = new PriorityQueue<Integer>();
		for (int i=0; i<size; i++) {
			emptySlots.add(new Integer(i));
		}
	}

	public synchronized void read(PageID pageID, Page page, boolean bModify) {
		
	}

	public synchronized void write(PageID pageID, Page page) {
		
	}
	
	public synchronized void runLRU() {
		
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
