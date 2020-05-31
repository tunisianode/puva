package chapter6.list;

import java.rmi.Naming;
import java.rmi.registry.LocateRegistry;

public class Server {
	public static void main(String[] args) {
		try {
			LocateRegistry.createRegistry(1099);
			AppendImpl server = new AppendImpl();
			Naming.rebind("AppendServer", server);
		} catch (Exception e) {
			System.out.println(e);
			e.printStackTrace();
		}
	}
}
