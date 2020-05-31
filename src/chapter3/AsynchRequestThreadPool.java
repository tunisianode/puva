package chapter3;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.*;

class PooledService implements Callable<Integer> {
	private boolean[] array;
	private int start;
	private int end;

	public PooledService(boolean[] array, int start, int end) {
		this.array = array;
		this.start = start;
		this.end = end;
	}

	public Integer call() throws Exception {
		int result = 0;
		for (int i = start; i <= end; i++) {
			if (array[i]) {
				result++;
			}
		}
		return result; //entspricht: return new Integer(result);
	}
}

public class AsynchRequestThreadPool {
	private static final int ARRAY_SIZE = 100000;
	private static final int NUMBER_OF_SERVERS = 1000;

	public static void main(String[] args) {
		long startTime = System.currentTimeMillis();

		boolean[] array = new boolean[ARRAY_SIZE];
		for (int i = 0; i < ARRAY_SIZE; i++) {
			if (i % 10 == 0) //alternativ: if(Math.random() < 0.1)
			{
				array[i] = true;
			} else {
				array[i] = false;
			}
		}

		LinkedList<Callable<Integer>> serviceList =
				new LinkedList<Callable<Integer>>();

		int start = 0;
		int end;
		int howMany = ARRAY_SIZE / NUMBER_OF_SERVERS;

		for (int i = 0; i < NUMBER_OF_SERVERS; i++) {
			if (i < NUMBER_OF_SERVERS - 1) {
				end = start + howMany - 1;
			} else {
				end = ARRAY_SIZE - 1;
			}
			serviceList.add(new PooledService(array, start, end));
			start = end + 1;
		}

		ThreadPoolExecutor pool =
				new ThreadPoolExecutor(NUMBER_OF_SERVERS,
						NUMBER_OF_SERVERS,
						0L, TimeUnit.SECONDS,
						new LinkedBlockingQueue<Runnable>());

		try {
			List<Future<Integer>> futureList;
			futureList = pool.invokeAll(serviceList);

			int result = 0;
			for (Future<Integer> future : futureList) {
				result += future.get();
			}

			long endTime = System.currentTimeMillis();
			float time = (endTime - startTime) / 1000.0f;
			System.out.println("Rechenzeit: " + time);

			System.out.println("Ergebnis: " + result);

			pool.shutdown();
		} catch (Exception e) {
		}
	}
}
