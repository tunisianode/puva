package chapter6.chat;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.Iterator;

public class ChatServerImpl extends UnicastRemoteObject implements ChatServer {
	private static final long serialVersionUID = 7815211110181595789L;

	private ArrayList<ChatClient> allClients;

	public ChatServerImpl() throws RemoteException {
		allClients = new ArrayList<ChatClient>();
	}

	public synchronized boolean addClient(ChatClient objRef) throws RemoteException {
		String name = objRef.getName();
		for (Iterator<ChatClient> iter = allClients.iterator(); iter.hasNext(); ) {
			ChatClient cc = iter.next();
			try {
				if (cc.getName().equals(name)) {
					return false;
				}
			} catch (RemoteException exc) {
				iter.remove();
				System.out.println("client automatically " +
						"removed (number of clients: " +
						allClients.size() + ")");
			}
		}
		allClients.add(objRef);
		System.out.println("new client '" + name +
				"' registered (number of clients: " +
				allClients.size() + ")");
		return true;
	}

	public synchronized void removeClient(ChatClient objRef) throws RemoteException {
		String name = objRef.getName();
		allClients.remove(objRef);
		System.out.println("client '" + name +
				"' removed (number of clients: " +
				allClients.size() + ")");
	}

	public synchronized void sendMessage(String name, String msg) throws RemoteException {
		for (Iterator<ChatClient> iter = allClients.iterator(); iter.hasNext(); ) {
			ChatClient cc = iter.next();
			try {
				try {
					Thread.sleep(400);
				} catch (InterruptedException e) {
				}
				cc.print(name + ": " + msg);
			} catch (RemoteException exc) {
				iter.remove();
				System.out.println("client automatically " +
						"removed (number of clients: " +
						allClients.size() + ")");
			}
		}
	}
}
