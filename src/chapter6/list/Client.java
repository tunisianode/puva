package chapter6.list;

import java.rmi.Naming;

public class Client {
	public static void main(String[] args) {
		if (args.length < 2) {
			System.out.println("N�tige Kommandozeilenargumente:"
					+ " <Name der Servers>"
					+ " <Zahl 1> <Zahl 2> ... <Zahl N>");
			return;
		}

		try {
			Append server = (Append) Naming.lookup("rmi://"
					+ args[0]
					+ "/AppendServer");
			System.out.println("Kontakt zu Server hergestellt");
			List l = new List();
			for (int i = 1; i < args.length; i++) {
				int value = Integer.parseInt(args[i]);
				l.append(value);
			}
			System.out.print("Liste vor RMI-Aufruf: ");
			l.print();
			server.tryToAppend(l);
			System.out.print("Liste nach RMI-Aufruf: ");
			l.print();
		} catch (Exception e) {
			System.out.println(e);
			e.printStackTrace();
		}
	}
}
