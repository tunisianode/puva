package chapter6.migration;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Counter extends Remote {
	public int reset() throws RemoteException;

	public int increment() throws RemoteException;

	public Counter comeBack() throws RemoteException;
}
