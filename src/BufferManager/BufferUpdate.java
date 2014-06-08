package BufferManager;

public class BufferUpdate extends Thread {
	public void run() {
		try {
			while (true) {
				long millis = 120000;
				Thread.sleep(millis);
				BufferManager bufferManager = BufferManager.getBufferManager();
				bufferManager.runLRU();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
