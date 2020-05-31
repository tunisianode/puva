package chapter5.udp;

import java.net.InetAddress;

public class UDPCounterClient {
	private static final int TIMEOUT = 10000; // 10 seconds

	public static void main(String args[]) {
		if (args.length != 2) {
			System.out.println("Notwendige Kommandozeilenargumente:"
					+ " <Name des Server-Rechners>"
					+ " <Anzahl>");
			return;
		}

		// create datagram socket
		try (UDPSocket udpSocket = new UDPSocket()) {
			udpSocket.setTimeout(TIMEOUT);

			// get inet addr of server
			InetAddress serverAddr = InetAddress.getByName(args[0]);

			// set counter to zero
			System.out.println("Z�hler wird auf 0 gesetzt.");
			udpSocket.send("reset", serverAddr, 1250);

			String reply = null;
			// receive reply
			try {
				reply = udpSocket.receive(20);
				System.out.println("Z�hler: " + reply);
			} catch (Exception e) {
				System.out.println(e);
			}

			// get count, initialize start time
			System.out.println("Nun wird der Z�hler erh�ht.");
			int count = new Integer(args[1]).intValue();
			long startTime = System.currentTimeMillis();

			// perform increment "count" number of times
			for (int i = 0; i < count; i++) {
				udpSocket.send("increment", serverAddr, 1250);
				try {
					reply = udpSocket.receive(20);
				} catch (Exception e) {
					System.out.println(e);
				}
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
		System.out.println("DatagramSocket wurde geschlossen");
	}
}
