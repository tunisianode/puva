package chapter5.udp;

public class UDPCounterServer {
	public static void main(String[] args) {
		int counter = 0;

		// create socket
		try (UDPSocket udpSocket = new UDPSocket(1250)) {
			// wait for request packets
			System.out.println("Server wartet auf Kunden");

			// execute client requests
			while (true) {
				// receive request
				String request = udpSocket.receive(20);

				// perform increment operation
				if (request.equals("increment")) {
					// perform increment
					counter++;
				} else if (request.equals("reset")) {
					// perform reset
					counter = 0;
					System.out.println("Z�hler auf 0 gesetzt durch "
							+ udpSocket.getSenderAddress()
							+ ":"
							+ udpSocket.getSenderPort());
				}

				// generate answer
				String answer = String.valueOf(counter);

				// send answer
				udpSocket.reply(answer);
			}
		} catch (Exception e) {
			System.out.println(e);
		}
		System.out.println("DatagramSocket wurde geschlossen");
	}
}
