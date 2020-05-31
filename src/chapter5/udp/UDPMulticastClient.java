package chapter5.udp;

import java.net.InetAddress;

public class UDPMulticastClient {
	private static final int TIMEOUT = 2000; // 2 seconds

	public static void main(String[] args) {
		if (args.length < 2) {
			System.out.println("Notwendige Kommandozeilenargumente:"
					+ " <Multicast-IP-Adresse>"
					+ " <Nachricht 1> ..."
					+ " < Nachricht N>");
			return;
		}

		// create datagram socket
		try (UDPSocket udpSocket = new UDPSocket()) {
			udpSocket.setTimeout(TIMEOUT);

			// get inet addr of server
			InetAddress serverAddr = InetAddress.getByName(args[0]);

			for (int i = 1; i < args.length; i++) {
				udpSocket.send(args[i], serverAddr, 1250);

				try {
					while (true) {
						String reply = udpSocket.receive(200);
						System.out.println("Nachricht erhalten: "
								+ udpSocket.getSenderAddress()
								+ ":"
								+ udpSocket.getSenderPort()
								+ ": "
								+ reply);
					}
				} catch (Exception e) {
					System.out.println("Ausnahme '" + e + "'");
				}
			} // for
		} catch (Exception e) {
			System.out.println("Ausnahme '" + e + "'");
		}
		System.out.println("DatagramSocket wurde geschlossen");
	}
}
