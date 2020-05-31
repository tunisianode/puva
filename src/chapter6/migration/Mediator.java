package chapter6.migration;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

public class Mediator {
	private Counter counter;

	public Mediator(Counter counter) {
		this.counter = counter;
	}

	public int increment() throws RemoteException {
		return counter.increment();
	}

	public int reset() throws RemoteException {
		return counter.reset();
	}

	public void migrate(String host) throws RemoteException {
		try {
			Migrator migrator = (Migrator) Naming.lookup("rmi://"
					+ host
					+ "/Migrator");
			counter = migrator.migrate(counter);
		} catch (MalformedURLException | NotBoundException e) {
		}
	}

	public void comeBack() throws RemoteException {
		counter = counter.comeBack();
	}
}
