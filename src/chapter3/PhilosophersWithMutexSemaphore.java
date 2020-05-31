package chapter3;

public class PhilosophersWithMutexSemaphore implements Runnable {
	private static final int NUMBER = 5;
	private Semaphore mutex;
	private int number;

	public PhilosophersWithMutexSemaphore(Semaphore mutex,
										  int number) {
		this.mutex = mutex;
		this.number = number;
	}

	public static void main(String[] args) {
		Semaphore mutex = new Semaphore(1);
		for (int i = 0; i < NUMBER; i++) {
			new Thread(new PhilosophersWithMutexSemaphore(mutex, i)).start();
		}
	}

	public void run() {
		while (true) {
			think(number);
			mutex.p();
			eat(number);
			mutex.v();
		}
	}

	private void think(int number) {
		System.out.println("Phlosoph Nr. " + number + " denkt.");
	}

	private void eat(int number) {
		System.out.println("Phlosoph Nr. " + number + " isst.");
	}
}
