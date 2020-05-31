package chapter7.ServletsJSF.src.chapter7.servlets;

import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

@WebListener
public class CounterSessionInitializer
		implements HttpSessionListener {
	public void sessionCreated(HttpSessionEvent event) {
		Counter counter = new Counter();
		HttpSession session = event.getSession();
		session.setAttribute("Counter", counter);
	}

	public void sessionDestroyed(HttpSessionEvent event) {
	}
}
