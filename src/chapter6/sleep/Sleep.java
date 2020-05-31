package chapter6.sleep;

public interface Sleep extends java.rmi.Remote {
	public void sleep(int secs) throws java.rmi.RemoteException;
}
