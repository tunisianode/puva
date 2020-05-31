package chapter6.list;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class SerializeExample {
	public static void main(String[] args) {
		if (args.length < 2) {
			usage();
		} else if (args[0].equals("lesen")) {
			read(args);
		} else if (args[0].equals("schreiben")) {
			write(args);
		} else {
			usage();
		}
	}

	private static void usage() {
		System.out.println("N�tige Kommandozeilenargumente: ");
		System.out.println("lesen <Name der Datei>");
		System.out.println("ODER");
		System.out.println("schreiben <Name der Datei> "
				+ "<Zahl 1> <Zahl 2> ... <Zahl N>");
	}

	private static void write(String[] args) {
		List l = new List();
		for (int i = 2; i < args.length; i++) {
			int value = Integer.parseInt(args[i]);
			l.append(value);
		}
		try (FileOutputStream fOutput =
					 new FileOutputStream(args[1]);
			 ObjectOutputStream output =
					 new ObjectOutputStream(fOutput)) {
			output.writeObject(l);
			output.flush();
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	private static void read(String[] args) {
		try (FileInputStream fInput = new FileInputStream(args[1]);
			 ObjectInputStream input = new ObjectInputStream(fInput)) {
			List l = (List) input.readObject();
			l.print();
		} catch (Exception e) {
			System.out.println(e);
		}
	}
}
