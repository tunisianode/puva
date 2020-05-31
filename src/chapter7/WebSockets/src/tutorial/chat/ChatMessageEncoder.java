package chapter7.WebSockets.src.tutorial.chat;

import javax.json.Json;
import javax.websocket.EncodeException;
import javax.websocket.Encoder;
import javax.websocket.EndpointConfig;

public class ChatMessageEncoder implements Encoder.Text<ChatMessage> {
	@Override
	public void init(EndpointConfig config) {
	}

	@Override
	public void destroy() {
	}

	@Override
	public String encode(ChatMessage chatMessage) throws EncodeException {
		String result = Json.createObjectBuilder().add("message", chatMessage.getMessage()).
				add("sender", chatMessage.getSender()).
				add("received", chatMessage.getReceived().
						toString()).build().toString();
		System.out.println("encoded: " + result);
		return result;
	}
}
