package chapter5.tcp;

import java.net.ServerSocket;

public class TCPSequentialSleepServer {
	public static void main(String[] args) {
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
							// perform sleep
							try {
								int secs =
										Integer.parseInt(request);
								Thread.sleep(secs * 1000);
							} catch (Exception e) {
								System.out.println(e);
							}
							tcpSocket.sendLine(request);
						} else {
							System.out.println("Schlie�en der "
									+ "Verbindung");
							break;
						}
					}
				} catch (Exception e) {
					System.out.println(e);
					System.out.println("=> Schlie�en der "
							+ "Verbindung");
				}
			}
		} catch (Exception e) {
			System.out.println("Fehler beim Erzeugen oder Nutzen "
					+ "des ServerSockets");
		}
	}
}
