package chapter5.udp;

public class UDPMulticastServer {
	public static void main(String[] args) {
		if (args.length != 1) {
			System.out.println("Notwendiges Kommandozeilenargument:"
					+ " <Multicast-IP-Adresse>");
			return;
		}

		try (UDPMulticastSocket multiSocket =
					 new UDPMulticastSocket(1250)) {
			System.out.println("MulticastSocket erzeugt");
			multiSocket.join(args[0]);
			System.out.println("Multicast-Gruppe beigetreten");

			while (true) {
				String request = multiSocket.receive(200);
				System.out.println("Nachricht erhalten: "
						+ multiSocket.getSenderAddress()
						+ ":"
						+ multiSocket.getSenderPort()
						+ ": "
						+ request);
				multiSocket.reply(request);
				if (request.equals("exit")) {
					break;
				}
			}
			multiSocket.leave(args[0]);
			System.out.println("Multicast-Gruppe verlassen");
		} catch (Exception e) {
			System.out.println("Ausnahme '" + e + "'");
		}
		System.out.println("MulticastSocket wurde geschlossen");
	}
}
