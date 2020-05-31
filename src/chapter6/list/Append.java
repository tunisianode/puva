package chapter6.list;

public interface Append extends java.rmi.Remote {
	public void tryToAppend(List l) throws java.rmi.RemoteException;
}
