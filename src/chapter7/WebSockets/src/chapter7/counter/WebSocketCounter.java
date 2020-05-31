package chapter7.WebSockets.src.chapter7.counter;

import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

class Counter {
	private int counter;

	public synchronized int increment() {
		counter++;
		return counter;
	}

	public synchronized int reset() {
		counter = 0;
		return counter;
	}

	public synchronized int get() {
		return counter;
	}
}

@ServerEndpoint("/counter")
public class WebSocketCounter {
	private static Counter c = new Counter();
	private static List<Session> connections =
			Collections.synchronizedList(new LinkedList<Session>());
	private Session session;

	private static void broadcast(String msg) {
		for (Session s : connections) {
			try {
				s.getBasicRemote().sendText(msg);
			} catch (IOException e) {
				connections.remove(s);
				try {
					s.close();
				} catch (IOException e1) {
				}
				broadcast("someone has been disconnected");
			}
		}
	}

	@OnOpen
	public void start(Session s) {
		System.out.println("start");
		session = s;
		connections.add(session);
		broadcast("someone joined");
	}

	@OnClose
	public void end() {
		System.out.println("end");
		connections.remove(session);
		session = null;
		broadcast("someone left");
	}

	@OnMessage
	public void incoming(String message) {
		System.out.println("incoming");
		String filteredMessage = message.toString();
		int value;
		if (filteredMessage.equals("increment")) {
			value = c.increment();
		} else if (filteredMessage.equals("reset")) {
			value = c.reset();
		} else {
			value = c.get();
		}
		broadcast("counter = " + value);
	}
}
