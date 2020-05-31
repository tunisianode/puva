package chapter6.sleep;

import java.rmi.Naming;
import java.rmi.registry.LocateRegistry;

public class Server {
	public static void main(String[] args) {
		try {
			LocateRegistry.createRegistry(1099);
			SleepImpl server;
			server = new SleepImpl();
			Naming.rebind("SleepServer1", server);
			server = new SleepImpl();
			Naming.rebind("SleepServer2", server);
		} catch (Exception e) {
			System.out.println(e);
			e.printStackTrace();
		}
	}
}
