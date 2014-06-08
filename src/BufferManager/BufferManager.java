package BufferManager;

import java.util.LinkedList;
import java.util.PriorityQueue;

import Interfaces.BufferManagerInterface;
import Utilities.Page;
import Utilities.PageID;
import Utilities.Memory;

public class BufferManager implements BufferManagerInterface {
	private static BufferManager bufferManager = null;  
	private BufferUpdate bufferUpdate;
	
	private int size;
	private FullPage[] memory;
	private boolean[] modified;
	private LinkedList<Integer> LRU;
	
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
		memory = new FullPage[size];
		modified = new boolean[size];
		LRU = new LinkedList<Integer>();
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
		if (LRU.size() == 0) {
			return;
		}
		else {
			int slot = LRU.getFirst().intValue();
			FullPage full = memory[slot];
			if (modified[slot] == true) {
				Page page = full.getPage();
				PageID id = full.getId();
				write(id, page);
			}
			memory[slot] = null;
			emptySlots.add(new Integer(slot));
			usedSlots.remove(new Integer(slot));
		}
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
