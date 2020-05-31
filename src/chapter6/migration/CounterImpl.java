package chapter6.migration;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

@SuppressWarnings("serial")
public class CounterImpl implements Counter, Serializable {
	private int counter;

	public int reset() throws RemoteException {
		counter = 0;
		System.out.println("---> Zaehler wurde zurueckgesetzt <---");
		return counter;
	}

	public int increment() throws RemoteException {
		counter++;
		System.out.println("---> Zaehler wurde erhoeht, "
				+ "neuer Stand: " + counter
				+ " <---");
		return counter;
	}

	public Counter comeBack() throws RemoteException {
		UnicastRemoteObject.unexportObject(this, true);
		return this;
	}
}
