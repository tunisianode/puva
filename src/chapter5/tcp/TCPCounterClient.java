package chapter5.tcp;

public class TCPCounterClient {
	public static void main(String args[]) {
		if (args.length != 2) {
			System.out.println("Notwendige Kommandozeilenargumente:"
					+ " <Name des Server-Rechners>"
					+ " <Anzahl>");
			return;
		}

		// create socket connection
		System.out.println("Aufbau der Verbindung");
		try (TCPSocket tcpSocket = new TCPSocket(args[0], 1250)) {
			// set counter to zero
			System.out.println("Z�hler zur�cksetzen");
			tcpSocket.sendLine("reset");
			String reply = tcpSocket.receiveLine();

			// get count, initialize start time
			System.out.println("Z�hler erh�hen");
			int count = new Integer(args[1]).intValue();
			long startTime = System.currentTimeMillis();

			// perform increment "count" number of times
			for (int i = 0; i < count; i++) {
				tcpSocket.sendLine("increment");
				reply = tcpSocket.receiveLine();
			}

			// display statistics
			long stopTime = System.currentTimeMillis();
			long duration = stopTime - startTime;
			System.out.println("Gesamtzeit = " + duration
					+ " msecs");
			if (count > 0) {
				System.out.println("Durchschnittszeit = "
						+ ((duration) / (float) count)
						+ " msecs");
			}
			System.out.println("Letzter Z�hlerstand: " + reply);
		} catch (Exception e) {
			System.out.println(e);
		}
		System.out.println("TCP-Verbindung wurde geschlossen");
	}
}
