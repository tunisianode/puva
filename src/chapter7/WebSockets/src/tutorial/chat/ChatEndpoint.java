package chapter7.WebSockets.src.tutorial.chat;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

@ServerEndpoint(value = "/chat/{room}", encoders = ChatMessageEncoder.class, decoders = ChatMessageDecoder.class)
public class ChatEndpoint {
	private static final Set<Session> peers = new HashSet<>();
	private static int counter;

	private int instanceNumber;

	public ChatEndpoint() {
		synchronized (getClass()) {
			counter++;
			instanceNumber = counter;
			System.out.println("ChatEndpoint instance no. " + instanceNumber + " created.");
		}
	}

	private String getInfo() {
		return "instance " + instanceNumber + ", thread " + Thread.currentThread().getName();
	}

	@OnOpen
	public void onOpen(Session session, @PathParam("room") String room) {
		synchronized (getClass()) {
			System.out.println("Session openend and bound to room: " + room + " (" + getInfo() + ")");
			session.getUserProperties().put("room", room);
			peers.add(session);
		}
	}

	@OnMessage
	public void onMessage(Session session, ChatMessage chatMessage) {
		synchronized (getClass()) {
			System.out.println("onMessage begin (" + getInfo() + ")");
			try {
				Thread.sleep(5000);
			} catch (InterruptedException e) {
			}

			String room = (String) session.getUserProperties().get("room");
			try {
				for (Session s : peers) {
					if (s.isOpen() && room.equals(s.getUserProperties().get("room"))) {
						s.getBasicRemote().sendObject(chatMessage);
					}
				}
			} catch (IOException | EncodeException e) {
				System.out.println("onMessage failed: " + e);
			}
			System.out.println("onMessage end (" + getInfo() + ")");
		}
	}

	@OnClose
	public void onClose(Session session) {
		synchronized (getClass()) {
			System.out.println("Someone left the chat room (" + getInfo() + ")");
			peers.remove(session);
		}
	}
}
