package chapter6.chat;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.rmi.Naming;

public class ChatClientMainSimple {
	public static void main(String[] args) {
		if (args.length != 3) {
			System.out.println("Argumente: Spitzname Server Chat-Gruppe");
			return;
		}

		try {
			ChatServer server = (ChatServer) Naming.lookup("rmi://" + args[1] +
					"/" + args[2]);
			System.out.println("Server kontaktiert.");

			ChatClientImplSimple client = new ChatClientImplSimple(args[0]);
			if (server.addClient(client)) {
				System.out.println("Ende durch Eingabe von 'ende'.");
				sendInputToServer(server, args[0]);
				server.removeClient(client);
			} else {
				System.out.println("Name auf Server schon bekannt.");
			}
		} catch (Exception e) {
			System.out.println(e);
		}
		System.exit(0);
	}

	private static void sendInputToServer(ChatServer server, String name) {
		try {
			BufferedReader input = new BufferedReader(
					new InputStreamReader(
							System.in));
			String line;
			while ((line = input.readLine()) != null) {
				if (line.equals("ende") || line.equals("halt")) {
					break;
				}
				server.sendMessage(name, line);
			}
		} catch (Exception e) {
			System.out.println(e);
		}
	}
}
