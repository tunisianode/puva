package chapter6.counter;

import java.rmi.Naming;

public class Client {
	public static void main(String args[]) {
		if (args.length != 2) {
			System.out.println("usage: java " + "rmi.counter.Client "
					+ "<server> <count>");
			return;
		}

		try {
			Counter myCounter = (Counter) Naming.lookup("rmi://" + args[0]
					+ "/" + "Counter");

			// set counter to initial value of 0
			System.out.println("setting counter to 0");
			myCounter.reset();
			System.out.println("incrementing");

			// calculate start time
			int count = new Integer(args[1]).intValue();
			long startTime = System.currentTimeMillis();

			// increment count times
			int result = 0;
			for (int i = 0; i < count; i++) {
				result = myCounter.increment();
			}

			// calculate stop time; print out statistics
			long stopTime = System.currentTimeMillis();
			long duration = stopTime - startTime;
			System.out.println("elapsed time = " + duration + " msecs");
			if (count > 0) {
				System.out.println("average ping = "
						+ ((duration) / (float) count)
						+ " msecs");
			}
			System.out.println("counter = " + result);
		} catch (Exception e) {
			System.out.println("Exception: " + e);
		}
	}
}
