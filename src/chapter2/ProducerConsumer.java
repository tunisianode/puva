package chapter2;

class Buffer {
	private boolean available = false;
	private int data;

	public synchronized void put(int x) {
		while (available) {
			try {
				wait();
			} catch (InterruptedException e) {
			}
		}
		data = x;
		available = true;
		notifyAll();
	}

	public synchronized int get() {
		while (!available) {
			try {
				wait();
			} catch (InterruptedException e) {
			}
		}
		available = false;
		notifyAll();
		return data;
	}
}

class Producer extends Thread {
	private Buffer buffer;

	private int start;

	public Producer(Buffer b, int s) {
		buffer = b;
		start = s;
	}

	public void run() {
		for (int i = start; i < start + 100; i++) {
			buffer.put(i);
		}
	}
}

class Consumer extends Thread {
	private Buffer buffer;

	public Consumer(Buffer b) {
		buffer = b;
	}

	public void run() {
		for (int i = 0; i < 100; i++) {
			int x = buffer.get();
			System.out.println("gelesen: " + x);
		}
	}
}

public class ProducerConsumer {
	public static void main(String[] args) {
		Buffer b = new Buffer();
		Consumer c1 = new Consumer(b);
		Consumer c2 = new Consumer(b);
		Consumer c3 = new Consumer(b);
		Producer p1 = new Producer(b, 1);
		Producer p2 = new Producer(b, 101);
		Producer p3 = new Producer(b, 201);
		c1.start();
		c2.start();
		c3.start();
		p1.start();
		p2.start();
		p3.start();
	}
}
