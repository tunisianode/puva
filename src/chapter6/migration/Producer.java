package chapter6.migration;

import java.rmi.RemoteException;

public interface Producer<T> {
	public T execute() throws RemoteException;
}
