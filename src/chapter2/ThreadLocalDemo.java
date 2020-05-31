package chapter2;

import java.util.HashMap;

class CommonAndThreadLocalData {
	private int common;
	private ThreadLocal<Integer> local;
	//oder alternativ MyThreadLocal (s.u.) verwenden

	public CommonAndThreadLocalData() {
		common = 0;
		local = new ThreadLocal<Integer>();
	}

	public synchronized void next() {
		common++;
		Integer integer = local.get();
		if (integer == null) {
			integer = new Integer(0);
		}
		int localData = integer.intValue() + 1;
		local.set(new Integer(localData));
		System.out.println(Thread.currentThread().getName()
				+ ": common = " + common
				+ ", local = " + localData);
	}
}

class CommonAndThreadLocalThread extends Thread {
	private CommonAndThreadLocalData data;

	public CommonAndThreadLocalThread(CommonAndThreadLocalData data,
									  String name) {
		super(name);
		this.data = data;
	}

	public void run() {
		for (int i = 1; i <= 3; i++) {
			data.next();
			yield();
		}
	}
}

public class ThreadLocalDemo {
	public static void main(String[] args) {
		CommonAndThreadLocalData data =
				new CommonAndThreadLocalData();
		for (int i = 1; i <= 3; i++) {
			CommonAndThreadLocalThread t =
					new CommonAndThreadLocalThread(data,
							"T" + i);
			t.start();
		}
	}
}

class MyThreadLocal<T> {
	private HashMap<Thread, T> map;

	public MyThreadLocal() {
		map = new HashMap<Thread, T>();
	}

	public T get() {
		return map.get(Thread.currentThread());
	}

	public void set(T value) {
		map.put(Thread.currentThread(), value);
	}

	public void remove() {
		map.remove(Thread.currentThread());
	}
}

