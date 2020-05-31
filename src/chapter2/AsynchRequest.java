package chapter2;

class Service implements Runnable {
	private boolean[] array;
	private int start;
	private int end;
	private int result;

	public Service(boolean[] array, int start, int end) {
		this.array = array;
		this.start = start;
		this.end = end;
	}

	public int getResult() {
		return result;
	}

	public void run() {
		for (int i = start; i <= end; i++) {
			if (array[i]) {
				result++;
			}
		}
	}
}

public class AsynchRequest {
	private static final int ARRAY_SIZE = 1000000000;
	private static final int NUMBER_OF_SERVERS = 10;

	public static void main(String[] args) {
		/*
		 * Feld erzeugen, jeder 10. Wert ist true
		 */
		boolean[] array = new boolean[ARRAY_SIZE];
		for (int i = 0; i < ARRAY_SIZE; i++) {
			if (i % 10 == 0) //alternativ: if(Math.random() < 0.1)
			{
				array[i] = true;
			} else {
				array[i] = false;
			}
		}

		// Startzeit messen
		long startTime = System.currentTimeMillis();

		// Feld f�r Services und Threads erzeugen
		Service[] service = new Service[NUMBER_OF_SERVERS];
		Thread[] serverThread = new Thread[NUMBER_OF_SERVERS];

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
			service[i] = new Service(array, start, end);
			serverThread[i] = new Thread(service[i]);
			serverThread[i].start();
			start = end + 1;
		}

		// Synchronisation mit Servern (auf Serverende warten)
		for (int i = 0; i < NUMBER_OF_SERVERS; i++) {
			try {
				serverThread[i].join();
			} catch (InterruptedException e) {
			}
		}

		// Gesamtergebnis aus Teilergebnissen berechnen
		int result = 0;
		for (int i = 0; i < NUMBER_OF_SERVERS; i++) {
			result += service[i].getResult();
		}

		// Endzeit messen
		long endTime = System.currentTimeMillis();
		float time = (endTime - startTime) / 1000.0f;
		System.out.println("Rechenzeit: " + time);

		// Ergebnis ausgeben
		System.out.println("Ergebnis: " + result);
	}
}
