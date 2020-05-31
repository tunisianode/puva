package chapter6.migration;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class Client {
	public static void main(String args[]) {
		BufferedReader sysIn = new BufferedReader(
				new InputStreamReader(System.in));
		try {
			Counter counter = new CounterImpl();
			Mediator mediator = new Mediator(counter);
			int value;

			System.out.print("erhoehen ... (Eingabetaste druecken)");
			sysIn.readLine();
			value = mediator.increment();
			System.out.println("neuer Zaehlerwert: " + value);

			System.out.print("erhoehen ... (Eingabetaste druecken)");
			sysIn.readLine();
			value = mediator.increment();
			System.out.println("neuer Zaehlerwert: " + value);

			System.out.print("migrieren ... (Eingabetaste druecken)");
			sysIn.readLine();
			mediator.migrate("localhost");

			System.out.print("erhoehen ... (Eingabetaste druecken)");
			sysIn.readLine();
			value = mediator.increment();
			System.out.println("neuer Zaehlerwert: " + value);

			System.out.print("erhoehen ... (Eingabetaste druecken)");
			sysIn.readLine();
			value = mediator.increment();
			System.out.println("neuer Zaehlerwert: " + value);

			System.out.print("zurueck holen ... (Eingabetaste druecken)");
			sysIn.readLine();
			mediator.comeBack();

			System.out.print("erhoehen ... (Eingabetaste druecken)");
			sysIn.readLine();
			value = mediator.increment();
			System.out.println("neuer Zaehlerwert: " + value);

			System.out.print("erhoehen ... (Eingabetaste druecken)");
			sysIn.readLine();
			value = mediator.increment();
			System.out.println("neuer Zaehlerwert: " + value);
		} catch (Exception e) {
			System.out.println("Ausnahme: " + e);
		}
	}
}
