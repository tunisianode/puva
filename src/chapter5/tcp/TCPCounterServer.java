package chapter5.tcp;

import java.net.ServerSocket;

public class TCPCounterServer {
	public static void main(String[] args) {
		int counter = 0;

		// create socket
		try (ServerSocket serverSocket = new ServerSocket(1250)) {
			while (true) {
				// wait for connection then create streams
				System.out.println("Warten auf Verbindungsaufbau");
				try (TCPSocket tcpSocket =
							 new TCPSocket(serverSocket.accept())) {
					// execute client requests
					while (true) {
						String request = tcpSocket.receiveLine();
						if (request != null) {
							if (request.equals("increment")) {
								// perform increment operation
								counter++;
							} else if (request.equals("reset")) {
								// perform reset operation
								counter = 0;
								System.out.println("Der Z�hler "
										+ "wurde "
										+ "zur�ckgesetzt");
							}
							String result = String.valueOf(counter);
							tcpSocket.sendLine(result);
						} else {
							System.out.println("Schlie�en der "
									+ "Verbindung");
							break;
						}
					}
				} catch (Exception e) {
					System.out.println(e);
					System.out.println("=> Verbindung geschlossen");
				}
			}
		} catch (Exception e) {
			System.out.println("Fehler beim Erzeugen oder Nutzen "
					+ "des ServerSockets");
			return;
		}
	}
}
