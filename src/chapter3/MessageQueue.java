package chapter3;

public class MessageQueue {
	private byte[][] msgQueue = null;
	private int qsize = 0;
	private int head = 0;
	private int tail = 0;

	public MessageQueue(int capacity) {
		// Abpr�fen von capacity <= 0 ausgelassen ...
		msgQueue = new byte[capacity][];
	}

	public int getCapacity() {
		return msgQueue.length;
	}

	public synchronized void send(byte[] msg) {
		while (qsize == msgQueue.length) {
			try {
				wait();
			} catch (InterruptedException e) {
			}
		}
		msgQueue[tail] = new byte[msg.length];
		for (int i = 0; i < msg.length; i++) {
			msgQueue[tail][i] = msg[i];
		}
		qsize++;
		tail++;
		if (tail == msgQueue.length) {
			tail = 0;
		}
		notifyAll(); // nicht notify
	}

	public synchronized byte[] receive() {
		while (qsize == 0) {
			try {
				wait();
			} catch (InterruptedException e) {
			}
		}
		byte[] result = msgQueue[head];
		msgQueue[head] = null;
		qsize--;
		head++;
		if (head == msgQueue.length) {
			head = 0;
		}
		notifyAll(); // nicht notify
		return result;
	}
}
