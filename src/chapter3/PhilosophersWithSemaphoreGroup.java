package chapter3;

public class PhilosophersWithSemaphoreGroup extends Thread {
	private static final int NUMBER = 5;
	private SemaphoreGroup sems;
	private int leftFork;
	private int rightFork;

	public PhilosophersWithSemaphoreGroup(SemaphoreGroup sems,
										  int number) {
		this.sems = sems;
		leftFork = number;
		if (number + 1 < sems.getNumberOfMembers()) {
			rightFork = number + 1;
		} else {
			rightFork = 0;
		}
		start();
	}

	public static void main(String[] args) {
		SemaphoreGroup forks = new SemaphoreGroup(NUMBER);
		int[] init = new int[NUMBER];
		for (int i = 0; i < NUMBER; i++) {
			init[i] = 1;
		}
		forks.changeValues(init);
		for (int i = 0; i < NUMBER; i++) {
			new PhilosophersWithSemaphoreGroup(forks, i);
		}
	}

	public void run() {
		int[] deltas = new int[sems.getNumberOfMembers()];
		for (int i = 0; i < deltas.length; i++) {
			deltas[i] = 0;
		}
		int number = leftFork;
		while (true) {
			think(number);
			deltas[leftFork] = -1;
			deltas[rightFork] = -1;
			sems.changeValues(deltas);
			eat(number);
			deltas[leftFork] = 1;
			deltas[rightFork] = 1;
			sems.changeValues(deltas);
		}
	}

	private void think(int number) {
		System.out.println("Phlosoph Nr. " + number + " denkt.");
	}

	private void eat(int number) {
		System.out.println("Phlosoph Nr. " + number + " isst.");
	}
}
