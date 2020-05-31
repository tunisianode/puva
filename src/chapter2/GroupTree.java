package chapter2;

class NeverEndingThread extends Thread {
	public NeverEndingThread(ThreadGroup group, String name) {
		super(group, name);
		start();
	}

	public void run() {
		try {
			this.join();
		} catch (InterruptedException e) {
		}
	}
}

public class GroupTree {
	private static ThreadGroup getRoot() {
		ThreadGroup result =
				Thread.currentThread().getThreadGroup();
		while (result.getParent() != null) {
			result = result.getParent();
		}
		return result;
	}

	private static void dump(ThreadGroup group, int blanks) {
		for (int i = 0; i < blanks; i++) {
			System.out.print(" ");
		}
		System.out.println(group);

		int numberOfThreads = group.activeCount();
		Thread[] threadList = new Thread[numberOfThreads];
		int threadNumber = group.enumerate(threadList, false);
		for (int i = 0; i < threadNumber; i++) {
			for (int j = 0; j < blanks + 3; j++) {
				System.out.print(" ");
			}
			System.out.println(threadList[i]);
		}

		int numberOfGroups = group.activeGroupCount();
		ThreadGroup[] threadgroupList =
				new ThreadGroup[numberOfGroups];
		int threadgroupNumber = group.enumerate(threadgroupList,
				false);
		for (int i = 0; i < threadgroupNumber; i++) {
			dump(threadgroupList[i], blanks + 3);
		}
	}

	public static void dumpAll() {
		dump(getRoot(), 0);
	}

	public static void main(String[] args) {
		ThreadGroup group1 = new ThreadGroup("eigene Obergruppe");
		ThreadGroup group2 = new ThreadGroup(group1,
				"eigene Untergruppe");
		new NeverEndingThread(group1, "erster Thread");
		new NeverEndingThread(group2, "zweiter Thread");
		new NeverEndingThread(group2, "dritter Thread");

		dumpAll();
		System.exit(0);
	}
}
