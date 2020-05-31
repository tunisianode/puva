package chapter2;

class TimestampThread extends Thread {
	private long[] timestamps;

	public TimestampThread(int capacity) {
		timestamps = new long[capacity];
	}

	public void run() {
		for (int i = 0; i < timestamps.length; i++) {
			timestamps[i] = System.nanoTime();
		}
	}

	public long getTimestamps(int i) {
		if (i >= 0 && i < timestamps.length) {
			return timestamps[i];
		}
		return Long.MAX_VALUE;
	}
}

public class SchedulingObserver {
	private static final int NUMBER_OF_ITERATIONS = 1000000;

	public static void main(String[] args) {
		if (args.length == 0) {
			System.out.println("Argumente: Liste von Priorit�ten");
			return;
		}

		Thread.currentThread().setPriority(Thread.MAX_PRIORITY);
		TimestampThread[] t = new TimestampThread[args.length];
		for (int i = 0; i < t.length; i++) {
			t[i] = new TimestampThread(NUMBER_OF_ITERATIONS);

			int priority = Thread.NORM_PRIORITY;
			try {
				priority = Integer.parseInt(args[i]);
				if (priority < Thread.MIN_PRIORITY ||
						priority > Thread.MAX_PRIORITY) {
					throw new NumberFormatException();
				}
			} catch (NumberFormatException e) {
				System.out.println("ung�ltiger Priorit�tswert: "
						+ args[i]);
				return;
			}
			t[i].setPriority(priority);
		}
		for (int i = 0; i < t.length; i++) {
			t[i].start();
		}
		for (int i = 0; i < t.length; i++) {
			try {
				t[i].join();
			} catch (InterruptedException e) {
			}
		}

		for (int i = 0; i < t.length; i++) {
			System.out.println("Priorit�t von Thread " + i
					+ ": " + t[i].getPriority());
		}
		System.out.println("=======================");

		int currentThread = -1;
		int runs = 1;
		int[] currentIndices = new int[t.length];
		for (int i = 0; i < NUMBER_OF_ITERATIONS * t.length; i++) {
			int previousThread = currentThread;
			currentThread = -1;
			long minimum = Long.MAX_VALUE;
			for (int j = 0; j < t.length; j++) {
				if (t[j].getTimestamps(currentIndices[j]) <= minimum) {
					currentThread = j;
					minimum = t[j].getTimestamps(currentIndices[j]);
				}
			}
			currentIndices[currentThread]++;
			if (currentThread == previousThread) {
				runs++;
			} else if (previousThread != -1) {
				System.out.println("Thread " + previousThread
						+ ": " + runs);
				runs = 1;
			}
		}
		System.out.println("Thread " + currentThread
				+ ": " + runs);

		System.out.println("=======================");
	}
}
