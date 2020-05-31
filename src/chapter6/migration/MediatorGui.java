package chapter6.migration;

import javafx.application.Platform;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

public class MediatorGui {
	private Counter counter;

	public MediatorGui(Counter counter) {
		this.counter = counter;
	}

	public void increment(Consumer<Integer> fxCall) {
		if (counter instanceof CounterImpl) {
			syncCall(() -> counter.increment(), fxCall);
		} else {
			asyncCall(() -> counter.increment(), fxCall);
		}
	}

	public void reset(Consumer<Integer> fxCall) {
		if (counter instanceof CounterImpl) {
			syncCall(() -> counter.reset(), fxCall);
		} else {
			asyncCall(() -> counter.reset(), fxCall);
		}
	}

	public void migrate(String host) {
		if (counter instanceof CounterImpl) {
			asyncCall(() -> callMigrate(host), (stub) -> counter = stub);
		}
		//else: do nothing!!!
	}

	private Counter callMigrate(String host) throws RemoteException {
		try {
			Migrator migrator = (Migrator) Naming.lookup("rmi://"
					+ host
					+ "/Migrator");
			return migrator.migrate(counter);
		} catch (MalformedURLException | NotBoundException e) {
			throw new RemoteException(e.getMessage());
		}
	}

	public void comeBack() {
		if (!(counter instanceof CounterImpl)) {
			asyncCall(() -> counter.comeBack(), (impl) -> counter = impl);
		}
		//else: do nothing!!!
	}

	private <T> void syncCall(Producer<T> rmiCall, Consumer<T> fxCall) {
		try {
			T t = rmiCall.execute();
			fxCall.execute(t);
		} catch (RemoteException e) {
			System.err.println("RMI problems");
		}
	}

	private <T> void asyncCall(Producer<T> rmiCall, Consumer<T> fxCall) {
		new Thread(() -> doInThread(rmiCall, fxCall)).start();
	}

	private <T> void doInThread(Producer<T> rmiCall, Consumer<T> fxCall) {
		try {
			T t = rmiCall.execute();
			Platform.runLater(() -> fxCall.execute(t));
		} catch (RemoteException e) {
			System.err.println("RMI problems");
		}
	}
}
