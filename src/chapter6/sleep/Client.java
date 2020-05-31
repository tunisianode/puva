package chapter6.sleep;

import java.rmi.Naming;

public class Client {
	public static void main(String[] args) {
		if (args.length < 3) {
			System.out.println("Ben�tigte Kommandozeilenargumente:"
					+ " <Name des Servers>"
					+ " <Name des Objekts>"
					+ " <Sekunden 1> ... <Sekunden N>");
			return;
		}

		try {
			Sleep server = (Sleep) Naming.lookup("rmi://" + args[0]
					+ "/"
					+ args[1]);
			System.out.println("Kontakt zu Server hergestellt");
			for (int i = 2; i < args.length; i++) {
				int secs = Integer.parseInt(args[i]);
				System.out.println("Aufruf von sleep(" + args[i]
						+ ")");
				server.sleep(secs);
			}
		} catch (Exception e) {
			System.out.println(e);
			e.printStackTrace();
		}
	}
}
