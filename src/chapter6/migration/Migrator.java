package chapter6.migration;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Migrator extends Remote {
	public Counter migrate(Counter counter) throws RemoteException;
}
