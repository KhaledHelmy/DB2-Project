package Components.BufferManager;

import java.util.PriorityQueue;

import Utilities.Page;

public class BufferManager {
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
}
