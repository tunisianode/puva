package chapter2;

public class SomethingToRun implements Runnable {
	public static void main(String[] args) {
		SomethingToRun runner = new SomethingToRun();
		Thread t = new Thread(runner);
		t.start();
	}

	public void run() {
		System.out.println("Hallo Welt");
	}
}
