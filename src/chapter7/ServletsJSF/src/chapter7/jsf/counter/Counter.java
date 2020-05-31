package chapter7.ServletsJSF.src.chapter7.jsf.counter;

public class Counter {
	private int counter;

	public synchronized void increment() {
		counter++;
	}

	public synchronized void reset() {
		counter = 0;
	}

	public synchronized int getCounter() {
		return counter;
	}
}
