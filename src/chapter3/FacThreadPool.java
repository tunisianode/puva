package chapter3;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

class RecursiveFacCallable implements Callable<Integer> {
	private int n;
	private ThreadPoolExecutor pool;

	public RecursiveFacCallable(int n, ThreadPoolExecutor pool) {
		this.n = n;
		this.pool = pool;
	}

	public Integer call() {
		System.out.println("ausgef�hrt von " +
				Thread.currentThread().getName());
		if (n == 0) {
			return 1;
		}
		RecursiveFacCallable rcs = new RecursiveFacCallable(n - 1,
				pool);
		List<Callable<Integer>> list = new ArrayList<>();
		list.add(rcs);
		try {
			int r = pool.invokeAny(list);
			return n * r;
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return 0;
		}
	}
}

public class FacThreadPool {
	public static void main(String[] args) {

		ThreadPoolExecutor pool = new ThreadPoolExecutor(5, 5,
				0L, TimeUnit.SECONDS,
				new SynchronousQueue<Runnable>());

		RecursiveFacCallable c = new RecursiveFacCallable(5, pool);
		ArrayList<RecursiveFacCallable> reqs = new ArrayList<>();
		reqs.add(c);
		try {
			int result = pool.invokeAny(reqs);
			System.out.println("Ergebnis: " + result);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		pool.shutdown();
	}
}
