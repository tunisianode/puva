package chapter7.ServletsJSF.src.chapter7.servlets;

public class Counter {
	private int counter;

	public synchronized int increment() {
		counter++;
		return counter;
	}

	public synchronized int reset() {
		counter = 0;
		return counter;
	}

	public synchronized int get() {
		return counter;
	}

	public synchronized void set(int c) {
		this.counter = c;
	}
}
