package chapter2;

interface ResultListener {
	public void putResult(int result);
}

class ServiceCallback implements Runnable {
	private boolean[] array;
	private int start;
	private int end;
	private ResultListener h;

	public ServiceCallback(boolean[] array, int start, int end,
						   ResultListener listener) {
		this.array = array;
		this.start = start;
		this.end = end;
		this.h = listener;
	}

	public void run() {
		int result = 0;
		for (int i = start; i <= end; i++) {
			if (array[i]) {
				result++;
			}
		}
		h.putResult(result);
	}
}

class ResultHandler implements ResultListener {
	private int result;
	private int numberOfResults;
	private int expectedNumberOfResults;

	public ResultHandler(int r) {
		expectedNumberOfResults = r;
	}

	public synchronized void putResult(int r) {
		result += r;
		numberOfResults++;
		if (numberOfResults == expectedNumberOfResults) {
			System.out.println("Ergebnis: " + result);
		}
	}
}

public class AsynchRequestCallback {
	private static final int ARRAY_SIZE = 500000000;
	private static final int NUMBER_OF_SERVERS = 10;

	public static void main(String[] args) {
		/*
		 * Feld erzeugen, Werte sind abwechselnd true und false
		 */
		boolean[] array = new boolean[ARRAY_SIZE];
		for (int i = 0; i < ARRAY_SIZE; i++) {
			if (i % 2 == 0) {
				array[i] = true;
			} else {
				array[i] = false;
			}
		}

		// ResultHandler erzeugen
		ResultHandler h = new ResultHandler(NUMBER_OF_SERVERS);

		// Threads erzeugen
		int start = 0;
		int end;
		int howMany = ARRAY_SIZE / NUMBER_OF_SERVERS;

		for (int i = 0; i < NUMBER_OF_SERVERS; i++) {
			if (i < NUMBER_OF_SERVERS - 1) {
				end = start + howMany - 1;
			} else {
				end = ARRAY_SIZE - 1;
			}
			ServiceCallback service = new ServiceCallback(array,
					start,
					end, h);
			Thread t = new Thread(service);
			t.start();
			start = end + 1;
		}
	}
}
