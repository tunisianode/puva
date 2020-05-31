package chapter6.counter;

import java.rmi.Naming;
import java.rmi.registry.LocateRegistry;

public class Server {
	public static void main(String args[]) {
		try {
			LocateRegistry.createRegistry(1099);
			CounterImpl myCounter = new CounterImpl();
			Naming.rebind("Counter", myCounter);
			System.out.println("Z�hler-Server bereit.");
		} catch (Exception e) {
			System.out.println("Ausnahme: " + e.getMessage());
			e.printStackTrace();
		}
	}
}
