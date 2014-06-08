package BufferManager;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.LinkedList;
import java.util.PriorityQueue;

import Interfaces.BufferManagerInterface;
import Utilities.Page;
import Utilities.PageID;
import Utilities.Memory;
import Utilities.Record;

public class BufferManager implements BufferManagerInterface {
	private static BufferManager bufferManager = null;
	private BufferUpdate bufferUpdate;

	private int size;
	private FullPage[] memory;
	private boolean[] modified, usedForRead, usedForWrite;
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
		size = maxUsedSlotSize;
		memory = new FullPage[size];
		modified = new boolean[size];
		usedForRead = new boolean[size];
		usedForWrite = new boolean[size];
		LRU = new LinkedList<Integer>();
		emptySlots = new PriorityQueue<Integer>();
		usedSlots = new PriorityQueue<Integer>();
		for (int i = 0; i < size; i++) {
			emptySlots.add(new Integer(i));
		}
	}

	public synchronized void read(PageID pageID, Page page, boolean bModify) {
		int slot = getSlotNumber(pageID);
		if (slot != -1) {
			if (usedForWrite[slot] == true
					|| (usedForRead[slot] == true && bModify == true)) {
				return;
			}
			page.setRecords(memory[slot].getPage().getRecords());
		} else {
			slot = emptySlots.poll().intValue();
			usedSlots.add(new Integer(slot));
			checkSizeConstraints();
			Hashtable<Integer, Record> records = Memory.getMemory().loadPage(
					pageID.getPageNameTrimmed());
			page.setRecords(records);
			if (bModify == true) {
				usedForWrite[slot] = true;
			} else {
				usedForRead[slot] = true;
			}
		}
		modified[slot] = bModify;
		Integer slotObj = new Integer(slot);
		LRU.remove(slotObj);
		LRU.add(slotObj);
	}

	private void checkSizeConstraints() {
		if (emptySlots.size() == minEmptySlotSize
				|| usedSlots.size() == maxUsedSlotSize) {
			runLRU();
		}
		return;
	}

	public synchronized void write(PageID pageID, Page page) {
		try {
			int slot = getSlotNumber(pageID);
			Integer slotObj = new Integer(slot);
			LRU.remove(slotObj);
			LRU.add(slotObj);
			final String path = "data/" + pageID.getPageName();
			ArrayList<Record> records = (ArrayList<Record>) page
					.getAllRecords();
			FileWriter fw = new FileWriter(path, true);
			for (int i = 0; i < records.size(); i++) {
				Record rec = records.get(i);
				fw.write(rec.toString() + "\n");
			}
			fw.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public synchronized void runLRU() {
		if (LRU.size() == 0) {
			return;
		} else {
			int slot = LRU.getFirst().intValue();
			LRU.removeFirst();
			FullPage full = memory[slot];
			if (modified[slot] == true) {
				Page page = full.getPage();
				PageID id = full.getId();
				write(id, page);
			}
			memory[slot] = null;
			modified[slot] = false;
			usedForRead[slot] = false;
			usedForWrite[slot] = false;
			Integer slotObj = new Integer(slot);
			emptySlots.add(slotObj);
			usedSlots.remove(slotObj);
		}
	}

	private void setSlotSizes() {
		setMinEmptySlotSize();
		setMaxUsedSlotSize();
	}

	private void setMinEmptySlotSize() {
		String property = Memory.getMemory().getProperty(
				"MinimumEmptyBufferSlots");
		minEmptySlotSize = Integer.parseInt(property);
	}

	private void setMaxUsedSlotSize() {
		String property = Memory.getMemory().getProperty(
				"MaximumUsedBufferSlots");
		maxUsedSlotSize = Integer.parseInt(property);
	}

	private int getSlotNumber(PageID page) {
		for (int i = 0; i < size; i++) {
			if (memory[i] != null && memory[i].getId().equals(page)) {
				return i;
			}
		}
		return -1;
	}
}
