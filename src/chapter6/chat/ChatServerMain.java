package chapter6.chat;

import java.rmi.Naming;
import java.rmi.registry.LocateRegistry;

public class ChatServerMain {
	public static void main(String[] args) {
		try {
			LocateRegistry.createRegistry(1099);
			ChatServerImpl server = new ChatServerImpl();
			Naming.rebind("ChatServer", server);
		} catch (Exception e) {
			System.out.println(e);
			e.printStackTrace();
		}
	}
}
