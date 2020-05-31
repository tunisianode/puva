package chapter5.tcp;

import java.net.ServerSocket;

public class TCPParallelDynamicSleepServer {
	public static void main(String[] args) {
		// create socket
		try (ServerSocket serverSocket = new ServerSocket(1250)) {
			while (true) {
				// wait for connection then create streams
				System.out.println("Warten auf Verbindungsaufbau");
				try {
					TCPSocket tcpSocket =
							new TCPSocket(serverSocket.accept());
					new Slave(tcpSocket);
				} catch (Exception e) {
					System.out.println(e);
				}
			}
		} catch (Exception e) {
			System.out.println("Fehler beim Erzeugen oder Nutzen "
					+ "des ServerSockets");
		}
	}
}

class Slave extends Thread {
	private TCPSocket socket;

	public Slave(TCPSocket socket) {
		this.socket = socket;
		this.start();
	}

	public void run() {
		try (TCPSocket s = socket) {
			while (true) {
				String request = s.receiveLine();
				// execute client requests
				if (request != null) {
					try {
						int secs = Integer.parseInt(request);
						Thread.sleep(secs * 1000);
					} catch (InterruptedException e) {
						System.out.println(e);
					}
					s.sendLine(request);
				} else {
					break;
				}
			}
		} catch (Exception e) {
			System.out.println(e);
		}
		System.out.println("Verbindung geschlossen");
	}
}
