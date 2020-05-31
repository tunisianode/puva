package chapter3;

import java.util.concurrent.Exchanger;

class ExchangeThread extends Thread {
	private int startValue;
	private int numberOfValues;
	private Exchanger<Integer> exchanger;

	public ExchangeThread(String name, int s, int n,
						  Exchanger<Integer> e) {
		super(name);
		this.startValue = s;
		this.numberOfValues = n;
		this.exchanger = e;
	}

	public void run() {
		try {
			for (int i = startValue; i < startValue + numberOfValues;
				 i++) {
				Integer iToSend = new Integer(i);
				Integer iReceived = exchanger.exchange(iToSend);
				System.out.println("Thread "
						+ Thread.currentThread().getName()
						+ ": gegeben " + iToSend
						+ ", genommen " + iReceived);
			}
		} catch (InterruptedException ex) {
		}
	}
}

public class ExchangerDemo {
	public static void main(String[] args) {
		Exchanger<Integer> exchanger = new Exchanger<Integer>();
		ExchangeThread t1 = new ExchangeThread("T1", 101, 2,
				exchanger);
		ExchangeThread t2 = new ExchangeThread("T2", 201, 2,
				exchanger);
		t1.start();
		t2.start();
	}

}
