package chapter6.chat;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class ChatClientImplSimple extends UnicastRemoteObject implements
		ChatClient {
	private static final long serialVersionUID = -2974565138909449441L;

	private String name;

	public ChatClientImplSimple(String n) throws RemoteException {
		name = n;
	}

	public String getName() throws RemoteException {
		return name;
	}

	public void print(String msg) throws RemoteException {
		System.out.println(msg);
	}
}
