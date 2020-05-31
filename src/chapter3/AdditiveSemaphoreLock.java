package chapter3;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class AdditiveSemaphoreLock {
	private int value;
	private ReentrantLock lock;
	private Condition condition;

	public AdditiveSemaphoreLock(int init) {
		if (init < 0) {
			throw new IllegalArgumentException("Parameter < 0");
		}
		this.value = init;
		lock = new ReentrantLock();
		condition = lock.newCondition();
	}

	public void p(int x) {
		if (x <= 0) {
			throw new IllegalArgumentException("Parameter < 0");
		}
		lock.lock();
		try {
			while (value - x < 0) {
				condition.awaitUninterruptibly();
			}
			value -= x;
		} finally {
			lock.unlock();
		}
	}

	public void v(int x) {
		if (x <= 0) {
			throw new IllegalArgumentException("Parameter < 0");
		}
		lock.lock();
		try {
			value += x;
			condition.signalAll();
		} finally {
			lock.unlock();
		}
	}
}
