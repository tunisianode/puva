package chapter6.chat;

import javafx.application.Platform;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class ChatClientImpl extends UnicastRemoteObject implements ChatClient {
	private String name;
	private ChatClientGuiView view;

	public ChatClientImpl(String name, ChatClientGuiView view) throws RemoteException {
		this.name = name;
		this.view = view;
	}

	public String getName() throws RemoteException {
		return name;
	}

	public void print(String msg) throws RemoteException {
		Platform.runLater(() -> view.showMessage(msg));
	}
}
