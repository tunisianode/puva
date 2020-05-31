package chapter5.tcp;

public class TCPSleepClient {
	public static void main(String args[]) {
		if (args.length < 2) {
			System.out.println("Notwendige Kommandozeilenargumente:"
					+ " <Name des Server-Rechners>"
					+ " <Sekundenzahl1> ..."
					+ " < SekundenzahlN>");
			return;
		}

		// create socket connection
		System.out.println("Aufbau der Verbindung");
		try (TCPSocket tcpSocket = new TCPSocket(args[0], 1250)) {
			// perform sleep operations
			for (int i = 1; i < args.length; i++) {
				tcpSocket.sendLine(args[i]);
				String result = tcpSocket.receiveLine();
				System.out.println(result);
			}
		} catch (Exception e) {
			System.out.println(e);
		}
		System.out.println("Verbindung geschlossen");
	}
}
