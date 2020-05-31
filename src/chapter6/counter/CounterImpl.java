package chapter6.counter;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

@SuppressWarnings("serial")
public class CounterImpl extends UnicastRemoteObject
		implements Counter {
	private int counter;

	public CounterImpl() throws RemoteException {
	}

	public synchronized int reset() throws RemoteException {
		counter = 0;
		return counter;
	}

	public synchronized int increment() throws RemoteException {
		counter++;
		return counter;
	}
}
