package chapter7.ServletsJSF.src.chapter7.servlets;

import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

@WebListener
public class SessionListenerExample implements HttpSessionListener {
	private int numberOfSessions;

	public void sessionCreated(HttpSessionEvent event) {
		numberOfSessions++;
		System.out.println("Anzahl der Sessions: "
				+ numberOfSessions);
	}

	public void sessionDestroyed(HttpSessionEvent event) {
		numberOfSessions--;
		System.out.println("Anzahl der Sessions: "
				+ numberOfSessions);
	}
}
