package chapter3;

public class PhilosophersWithSemaphoresNaive implements Runnable {
	private static final int NUMBER = 5;
	private Semaphore[] sems;
	private int number;
	private int left, right;

	public PhilosophersWithSemaphoresNaive(Semaphore[] sems,
										   int number) {
		this.sems = sems;
		this.number = number;
		left = number;
		right = number + 1;
		if (right == sems.length) {
			right = 0;
		}
	}

	public static void main(String[] args) {
		Semaphore[] sems = new Semaphore[NUMBER];
		for (int i = 0; i < NUMBER; i++) {
			sems[i] = new Semaphore(1);
		}
		for (int i = 0; i < NUMBER; i++) {
			new Thread(new PhilosophersWithSemaphoresNaive(sems, i)).
					start();
		}
	}

	public void run() {
		while (true) {
			think(number);
			sems[left].p();
			sems[right].p();
			eat(number);
			sems[left].v();
			sems[right].v();
		}
	}

	private void think(int number) {
		System.out.println("Phlosoph Nr. " + number + " denkt.");
	}

	private void eat(int number) {
		System.out.println("Phlosoph Nr. " + number + " isst.");
	}
}
