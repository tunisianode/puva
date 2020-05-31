package chapter3;

public class SemaphoreGroup {
	private int[] values;

	public SemaphoreGroup(int numberOfMembers) {
		if (numberOfMembers <= 0) {
			throw new IllegalArgumentException("Parameter <= 0");
		}
		values = new int[numberOfMembers];
	}

	public int getNumberOfMembers() {
		return values.length;
	}

	public synchronized void changeValues(int[] deltas) {
		if (deltas.length != values.length) {
			throw new IllegalArgumentException("falsche Feldl�nge");
		}
		while (!canChange(deltas)) {
			try {
				wait();
			} catch (InterruptedException e) {
			}
		}
		doChange(deltas);
		notifyAll();
	}

	private boolean canChange(int[] deltas) {
		for (int i = 0; i < values.length; i++) {
			if (values[i] + deltas[i] < 0) {
				return false;
			}
		}
		return true;
	}

	private void doChange(int[] deltas) {
		for (int i = 0; i < values.length; i++) {
			values[i] = values[i] + deltas[i];
		}
	}
}
