package chapter6.migration;

import java.rmi.Naming;
import java.rmi.registry.LocateRegistry;

public class MigratorServer {
	public static void main(String args[]) {
		try {
			LocateRegistry.createRegistry(1099);
			Migrator m = new MigratorImpl();
			Naming.rebind("Migrator", m);
			System.out.println("Server zur Aufnahme "
					+ "migrierender Element "
					+ "verfuegbar.");
		} catch (Exception e) {
			System.out.println("Exception: " + e.getMessage());
			e.printStackTrace();
		}
	}
}
