package chapter3;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

class TrueCountingTask extends RecursiveTask<Integer> {
	private static final int SEQUENTIAL_THRESHOLD = 100;
	private static final int SPLIT_FACTOR = 5;

	private boolean[] array;
	private int start;
	private int end;

	public TrueCountingTask(boolean[] array, int start, int end) {
		this.array = array;
		this.start = start;
		this.end = end;
	}

	protected Integer compute() {
		if (end - start + 1 <= SEQUENTIAL_THRESHOLD) {
			int result = 0;
			for (int i = start; i <= end; i++) {
				if (array[i]) {
					result++;
				}
			}
			return result;
		} else {
			int s = start;
			int e;
			int howMany = (end - start + 1) / SPLIT_FACTOR;

			TrueCountingTask[] tasks =
					new TrueCountingTask[SPLIT_FACTOR];
			for (int i = 0; i < SPLIT_FACTOR; i++) {
				if (i < SPLIT_FACTOR - 1) {
					e = s + howMany - 1;
				} else {
					e = end;
				}
				tasks[i] = new TrueCountingTask(array, s, e);
				tasks[i].fork();
				s = e + 1;
			}
			int result = 0;
			for (int i = 0; i < SPLIT_FACTOR; i++) {
				result += tasks[i].join();
			}
			return result;
		}
	}
}

public class AsynchRequestForkJoin {
	private static final int ARRAY_SIZE = 100000;

	public static void main(String[] args) {
		boolean[] array = new boolean[ARRAY_SIZE];
		for (int i = 0; i < ARRAY_SIZE; i++) {
			array[i] = true;
		}

		// Startzeit messen
		long startTime = System.currentTimeMillis();

		// Ergebnis berechnen
		ForkJoinPool pool = new ForkJoinPool(5);
		TrueCountingTask task = new TrueCountingTask(array, 0,
				array.length - 1);
		int result = pool.invoke(task);

		// Endzeit messen
		long endTime = System.currentTimeMillis();
		float time = (endTime - startTime) / 1000.0f;
		System.out.println("Rechenzeit: " + time);

		// Ergebnis ausgeben
		System.out.println("Ergebnis: " + result);
	}
}
