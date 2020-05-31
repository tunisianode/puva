package chapter3;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

class BufferLock {
	private int head;
	private int tail;
	private int count;
	private int[] data;
	private ReentrantLock lock;
	private Condition notFull;
	private Condition notEmpty;

	public BufferLock(int n) {
		head = 0;
		tail = 0;
		count = 0;
		data = new int[n];
		lock = new ReentrantLock();
		notFull = lock.newCondition();
		notEmpty = lock.newCondition();
	}

	private void dump() {
		System.out.print("\t\t\tPufferinhalt: [ ");
		int index = head;
		for (int i = 0; i < count; i++) {
			System.out.print(data[index] + " ");
			index++;
			if (index == data.length) {
				index = 0;
			}
		}
		System.out.println("]");
	}

	public void put(int x) {
		lock.lock();
		try {
			while (count == data.length) {
				notFull.awaitUninterruptibly();
			}
			data[tail++] = x;
			if (tail == data.length) {
				tail = 0;
			}
			count++;
			dump();
			notEmpty.signal();
		} finally {
			lock.unlock();
		}
	}

	public int get() {
		lock.lock();
		try {
			while (count == 0) {
				notEmpty.awaitUninterruptibly();
			}
			int result = data[head++];
			if (head == data.length) {
				head = 0;
			}
			count--;
			dump();
			notFull.signal();
			return result;
		} finally {
			lock.unlock();
		}
	}
}

class ProducerLock extends Thread {
	private BufferLock buffer;
	private int start;

	public ProducerLock(BufferLock b, int s, String name) {
		super(name);
		buffer = b;
		start = s;
	}

	public void run() {
		for (int i = start; i < start + 100; i++) {
			buffer.put(i);
		}
	}
}

class ConsumerLock extends Thread {
	private BufferLock buffer;

	public ConsumerLock(BufferLock b, String name) {
		super(name);
		buffer = b;
	}

	public void run() {
		for (int i = 0; i < 100; i++) {
			int x = buffer.get();
			System.out.println("verbraucht " + x);
		}
	}
}

public class ProducerConsumerLock {
	public static void main(String[] args) {
		BufferLock p = new BufferLock(5);
		ConsumerLock v1 = new ConsumerLock(p, "V1");
		ConsumerLock v2 = new ConsumerLock(p, "V2");
		ConsumerLock v3 = new ConsumerLock(p, "V3");
		ProducerLock e1 = new ProducerLock(p, 1, "E1");
		ProducerLock e2 = new ProducerLock(p, 101, "E2");
		ProducerLock e3 = new ProducerLock(p, 201, "E3");
		v1.start();
		v2.start();
		v3.start();
		e1.start();
		e2.start();
		e3.start();
	}
}
