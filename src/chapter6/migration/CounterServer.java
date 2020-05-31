package chapter6.migration;

import java.rmi.Naming;

public class CounterServer {
	public static void main(String args[]) {
		try {
			Counter c = new CounterImpl();
			Naming.rebind("Counter", c);
		} catch (Exception e) {
			System.out.println("Exception: " + e.getMessage());
			e.printStackTrace();
		}
	}
}
