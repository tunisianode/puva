package chapter7.ServletsJSF.src.chapter7.servlets;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

@WebListener
public class CounterInitializer implements ServletContextListener {
	public void contextInitialized(ServletContextEvent event) {
		Counter counter = new Counter();
		ServletContext ctx = event.getServletContext();
		ctx.setAttribute("Counter", counter);
	}

	public void contextDestroyed(ServletContextEvent event) {
	}
}
