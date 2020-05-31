package chapter6.chat;

import java.rmi.Naming;

public class ChatClientMainAutomatic {
	public static void main(String[] args) {
		if (args.length != 3) {
			System.out.println("usage: java rmi.chat.ChatClientMainAutomatic "
					+ "<server> <nick name> <time limit in secs>");
			return;
		}

		try {
			int timeLimit = Integer.parseInt(args[2]);
			ChatServer server = (ChatServer) Naming.lookup("rmi://" + args[0]
					+ "/ChatServer");
			System.out.println("Server contacted.");

			ChatClientImplSimple client = new ChatClientImplSimple(args[1]);
			if (server.addClient(client)) {
				sendInputToServer(server, args[1], timeLimit);
				server.removeClient(client);
				System.exit(0);
			} else {
				System.out.println("name already defined at server");
				System.exit(0);
			}
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	private static void sendInputToServer(ChatServer server, String name,
										  int timeLimit) {
		try {
			int time = 0;
			String msg = "na, also, wie gesagt, ich sag das mal einfach mal so";
			while (time < timeLimit) {
				int pauseTime = (int) (Math.random() * 20.0f);
				Thread.sleep(pauseTime * 1000);
				time += pauseTime;
				server.sendMessage(name, msg);
			}
		} catch (Exception e) {
			System.out.println(e);
		}
	}
}
