package chapter2;

import java.util.ArrayList;
import java.util.List;

interface Task<T> {
	public boolean isDivisible();

	public List<Task<T>> split();

	public T execute();

	public T combine(List<T> results);
}

class TaskNodeExecutor<T> extends Thread {
	private Task<T> task;
	private T result;

	public TaskNodeExecutor(Task<T> task) {
		this.task = task;
	}

	public static <T> T executeAll(Task<T> task) {
		TaskNodeExecutor<T> root = new TaskNodeExecutor<T>(task);
		root.run();
		return root.getResult();
	}

	public void run() {
		if (task.isDivisible()) {
			List<Task<T>> subtasks = task.split();
			List<TaskNodeExecutor<T>> threads = new ArrayList<TaskNodeExecutor<T>>();
			for (Task<T> subtask : subtasks) {
				TaskNodeExecutor<T> thread = new TaskNodeExecutor<T>(subtask);
				threads.add(thread);
				thread.start();
			}
			List<T> subresults = new ArrayList<T>();
			for (TaskNodeExecutor<T> thread : threads) {
				try {
					thread.join();
				} catch (InterruptedException e) {
				}
				subresults.add(thread.getResult());
			}
			result = task.combine(subresults);
		} else //!task.isDivisible()
		{
			result = task.execute();
		}
	}

	public T getResult() {
		return result;
	}
}

class SleepResult {
	private double sleepTime;
	private int depth;
	private int numberOfTasks;

	public SleepResult(double sleepTime, int depth, int numberOfTasks) {
		this.sleepTime = sleepTime;
		this.depth = depth;
		this.numberOfTasks = numberOfTasks;
	}

	public double getSleepTime() {
		return sleepTime;
	}

	public int getDepth() {
		return depth;
	}

	public int getNumberOfTasks() {
		return numberOfTasks;
	}
}

class SleepTask implements Task<SleepResult> {
	private double sleepTime;
	private int splitFactor;

	public SleepTask(double sleepTime, int splitFactor) {
		this.sleepTime = sleepTime;
		this.splitFactor = splitFactor;
	}

	public boolean isDivisible() {
		return sleepTime > 100;
	}

	public List<Task<SleepResult>> split() {
		ArrayList<Task<SleepResult>> subtasks = new ArrayList<>();
		for (int i = 0; i < splitFactor; i++) {
			subtasks.add(new SleepTask(sleepTime / splitFactor,
					splitFactor));
		}
		return subtasks;
	}

	public SleepResult execute() {
		try {
			Thread.sleep((long) sleepTime);
		} catch (InterruptedException e) {
		}
		return new SleepResult(sleepTime, 1, 1);
	}

	public SleepResult combine(List<SleepResult> results) {
		SleepResult subResult = results.get(0);
		return new SleepResult(subResult.getSleepTime(),
				subResult.getDepth() + 1,
				subResult.getNumberOfTasks() * results.size() + 1);
	}
}

class FibonacciTask implements Task<Integer> {
	private int n;

	public FibonacciTask(int n) {
		this.n = n;
	}

	public boolean isDivisible() {
		return n > 1;
	}

	public List<Task<Integer>> split() {
		ArrayList<Task<Integer>> subtasks = new ArrayList<>();
		subtasks.add(new FibonacciTask(n - 1));
		subtasks.add(new FibonacciTask(n - 2));
		return subtasks;
	}

	public Integer execute() {
		if (n == 1) {
			return 1;
		} else if (n == 0) {
			return 0;
		} else {
			return null;
		}
	}

	public Integer combine(List<Integer> results) {
		int r = 0;
		for (Integer result : results) {
			r += result.intValue();
		}
		return new Integer(r);
	}
}

class BooleanCountingResult {
	private int result;
	private int numberOfTasks;
	private int depth;

	public BooleanCountingResult(int result, int numberOfTasks, int depth) {
		this.result = result;
		this.numberOfTasks = numberOfTasks;
		this.depth = depth;
	}

	public BooleanCountingResult(int result) {
		this(result, 1, 1);
	}

	public int getResult() {
		return result;
	}

	public int getNumberOfTasks() {
		return numberOfTasks;
	}

	public int getDepth() {
		return depth;
	}
}

class BooleanCountingTask implements Task<BooleanCountingResult> {
	private static final int MIN_LENGTH = 100;

	private boolean[] array;
	private int start;
	private int end;
	private int splitFactor;

	public BooleanCountingTask(boolean[] array, int start, int end, int splitFactor) {
		this.array = array;
		this.start = start;
		this.end = end;
		this.splitFactor = splitFactor;
	}

	public BooleanCountingTask(boolean[] array, int splitFactor) {
		this(array, 0, array.length - 1, splitFactor);
	}

	public BooleanCountingTask(boolean[] array) {
		this(array, 2);
	}

	public boolean isDivisible() {
		return end - start + 1 > MIN_LENGTH;
	}

	public List<Task<BooleanCountingResult>> split() {
		int tempStart = start;
		int tempEnd;
		int howMany = (end - start + 1) / splitFactor;
		int numberOfTasks = splitFactor;
		if (howMany < 1) {
			howMany = 1;
			numberOfTasks = end - start + 1;
		}
		List<Task<BooleanCountingResult>> tasks = new ArrayList<>();
		for (int i = 0; i < numberOfTasks; i++) {
			if (i < numberOfTasks - 1) {
				tempEnd = tempStart + howMany - 1;
			} else {
				tempEnd = end;
			}
			tasks.add(new BooleanCountingTask(array, tempStart, tempEnd, splitFactor));
			tempStart = tempEnd + 1;
		}
		return tasks;
	}

	public BooleanCountingResult execute() {
		int result = 0;
		for (int i = start; i <= end; i++) {
			if (array[i]) {
				result++;
			}
		}
		return new BooleanCountingResult(result);
	}

	public BooleanCountingResult combine(List<BooleanCountingResult> results) {
		int r = 0;
		int numberOfTasks = 0;
		int depth = 0;
		for (BooleanCountingResult result : results) {
			r += result.getResult();
			numberOfTasks += result.getNumberOfTasks();
			if (depth < result.getDepth()) {
				depth = result.getDepth();
			}
		}
		return new BooleanCountingResult(r, numberOfTasks + 1, depth + 1);
	}
}

class QuickSortTask implements Task<Void> {
	private int[] data;
	private int left;
	private int right;

	public QuickSortTask(int[] data) {
		this(data, 0, data.length - 1);
	}

	private QuickSortTask(int[] data, int left, int right) {
		this.data = data;
		this.left = left;
		this.right = right;
	}

	@Override
	public boolean isDivisible() {
		return left < right;
	}

	@Override
	public List<Task<Void>> split() {
		ArrayList<Task<Void>> subtasks = new ArrayList<>();
		int pivotIndex = left + ((right - left) / 2);
		pivotIndex = partition(pivotIndex);
		Task<Void> subtaskLeft = new QuickSortTask(data, left, pivotIndex - 1);
		Task<Void> subtaskRight = new QuickSortTask(data, pivotIndex + 1, right);
		subtasks.add(subtaskLeft);
		subtasks.add(subtaskRight);

		return subtasks;
	}

	private int partition(int pivotIndex) {
		int pivotValue = data[pivotIndex];

		swap(pivotIndex, right);

		int storeIndex = left;
		for (int i = left; i < right; i++) {
			if (data[i] < pivotValue) {
				swap(i, storeIndex);
				storeIndex++;
			}
		}

		swap(storeIndex, right);

		return storeIndex;
	}

	private void swap(int i, int j) {
		if (i != j) {
			int iValue = data[i];
			data[i] = data[j];
			data[j] = iValue;
		}
	}

	@Override
	public Void execute() {
		return null;
	}

	@Override
	public Void combine(List<Void> results) {
		return null;
	}
}

public class TaskTreeExecutor {
	@SuppressWarnings("unused")
	private static void mainSleep() {
		for (double s = 500; s <= 5000; s += 500) {
			for (int splitFactor = 2; splitFactor <= 20; splitFactor++) {
				long startTime = System.currentTimeMillis();
				SleepResult result = (SleepResult) TaskNodeExecutor.executeAll(new SleepTask(s, splitFactor));
				long endTime = System.currentTimeMillis();
				long actualTime = endTime - startTime;
				long estimatedTime = Math.round(result.getSleepTime());
				System.out.println("SleepTask(" +
						s + ", " + splitFactor + ") in " +
						actualTime + " msecs (estimated: " +
						estimatedTime + " msecs, depth: " +
						result.getDepth() + ", tasks: " +
						result.getNumberOfTasks() + ")");
			}
		}
	}

	@SuppressWarnings("unused")
	private static void mainFibonacci() {
		for (int i = 0; i <= 17; i++) {
			Integer result = TaskNodeExecutor.executeAll(new FibonacciTask(i));
			System.out.println("fib(" + i + ") = " + result);
		}
	}

	private static void mainBooleanCounting() {
		boolean[] array = new boolean[100000];
		for (int i = 0; i < array.length; i++) {
			if (i % 2 == 0) {
				array[i] = true;
			}
		}
		for (int i = 2; i <= 150; i++) {
			BooleanCountingResult result = TaskNodeExecutor.executeAll(new BooleanCountingTask(array, i));
			if (result.getResult() != array.length / 2) {
				System.out.print(">>>>>ERROR>>>>>");
			}
			System.out.println("SearchResult for split factor " + i + ": " +
					result.getResult() + " (threads: " +
					result.getNumberOfTasks() + ", depth: " +
					result.getDepth() + ")");
		}
	}

	@SuppressWarnings("unused")
	private static void mainQuickSort() {
		int[] array = new int[300];
		for (int i = 0; i < array.length; i++) {
			array[i] = (int) (Math.random() * 1000);
		}
		TaskNodeExecutor.executeAll(new QuickSortTask(array));
		int count = 0;
		for (int a : array) {
			System.out.print(a + " ");
			count++;
			if (count == 30) {
				System.out.println();
				count = 0;
			}
		}
		System.out.println();
		for (int i = 1; i < array.length; i++) {
			if (array[i - 1] > array[i]) {
				System.err.println("Fehler bei Index " + i + "!!!");
			}
		}
	}

	public static void main(String[] args) {
		//mainSleep();
		//mainFibonacci();
		mainBooleanCounting();
		//mainQuickSort();
	}
}
