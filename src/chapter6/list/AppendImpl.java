package chapter6.list;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class AppendImpl extends UnicastRemoteObject
		implements Append {
	public AppendImpl() throws RemoteException {
	}

	public void tryToAppend(List l) throws RemoteException {
		System.out.print("erhaltene Liste: ");
		l.print();

		l.append(4711);
		System.out.print("manipulierte Liste: ");
		l.print();
	}
}
