package chapter2;

public class MyThread extends Thread {
	public static void main(String[] args) {
		MyThread t = new MyThread();
		t.start();
	}

	public void run() {
		System.out.println("Hallo Welt");
	}
}
