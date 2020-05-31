package chapter6.semaphore;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface RMISemaphore extends Remote {
	public void p() throws RemoteException;

	public void v() throws RemoteException;
}
