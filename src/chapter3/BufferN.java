package chapter3;

public class BufferN {
	private int head;
	private int tail;
	private int numberOfElements;
	private int[] data;

	public BufferN(int n) {
		// Abpr�fen von n <= 0 ausgelassen ...
		data = new int[n];

		//nicht n�tig, da automatisch mit 0 initialisiert:
		head = 0;
		tail = 0;
		numberOfElements = 0;
	}

	public synchronized void put(int x) {
		while (numberOfElements == data.length) {
			try {
				wait();
			} catch (InterruptedException e) {
			}
		}
		data[tail++] = x;
		if (tail == data.length) {
			tail = 0;
		}
		numberOfElements++;
		notifyAll();
	}

	public synchronized int get() {
		while (numberOfElements == 0) {
			try {
				wait();
			} catch (InterruptedException e) {
			}
		}
		int result = data[head++];
		if (head == data.length) {
			head = 0;
		}
		numberOfElements--;
		notifyAll();
		return result;
	}
}
