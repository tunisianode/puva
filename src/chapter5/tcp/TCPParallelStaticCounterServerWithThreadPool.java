package chapter5.tcp;

import java.net.ServerSocket;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

class Task implements Runnable {
	private TCPSocket socket;
	private Counter counter;

	public Task(TCPSocket socket, Counter counter) {
		this.socket = socket;
		this.counter = counter;
	}

	public void run() {
		try (TCPSocket s = socket) {
			while (true) {
				String request = socket.receiveLine();
				int result;
				if (request != null) {
					if (request.equals("increment")) {
						// perform increment operation
						result = counter.increment();
					} else if (request.equals("reset")) {
						// perform reset operation
						result = counter.reset();
					} else {
						result = counter.getCounter();
					}
					socket.sendLine("" + result);
				} else {
					break;
				}
			}
		} catch (Exception e) {
			System.out.println(e);
		}
	}
}

public class TCPParallelStaticCounterServerWithThreadPool {
	public static void main(String[] args) {
		Counter counter = new Counter();
		ThreadPoolExecutor pool =
				new ThreadPoolExecutor(3, 3,
						0L, TimeUnit.SECONDS,
						new LinkedBlockingQueue<Runnable>());

		try (ServerSocket serverSocket = new ServerSocket(1250)) {
			while (true) {
				try {
					TCPSocket tcpSocket =
							new TCPSocket(serverSocket.accept());
					Task task = new Task(tcpSocket, counter);
					pool.execute(task);
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
