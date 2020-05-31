package chapter7.ServletsJSF.src.chapter7.servlets;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;

@SuppressWarnings("serial")
@WebServlet("/SchlafenSitzung")
public class SleepingSessionServlet extends HttpServlet {
	protected void doGet(HttpServletRequest request,
						 HttpServletResponse response)
			throws IOException, ServletException {
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		out.println("<html>");
		out.println("<head>");
		out.println("<title>Schlafen mit Abfragen</title>");
		out.println("</head>");
		out.println("<body>");
		out.println("<h1>Schlafen mit Abfragen</h1>");

		String secsString = request.getParameter("Sekunden");
		if (secsString != null) {
			try {
				int secs = Integer.parseInt(secsString);
				if (secs < 0) {
					throw new NumberFormatException();
				}
				SleepingThread t = new SleepingThread(secs);
				t.start();
				HttpSession session = request.getSession(true);
				session.setMaxInactiveInterval(10);
				session.setAttribute("SleepingThread", t);
				response.setHeader("Refresh",
						"2; URL=SchlafenAbfragen");
				out.println("<b>Der Auftrag wurde gestartet!</b>");
				out.println("<p>");
				out.println("Sie werden automatisch "
						+ "weitergeleitet ...");
				out.println("</body>");
				out.println("</html>");
				return;
			} catch (NumberFormatException e) {
				out.println("Es muss eine nicht negative Zahl "
						+ "eingegeben werden.");
			}
		}

		out.println("<h2>GET-Formular");
		out.println("<form method=\"get\">");
		out.println("<input name=\"Sekunden\">");
		out.println("<input type=\"submit\" value=\"Los!\">");
		out.println("</form>");

		out.println("<h2>POST-Formular");
		out.println("<form method=\"post\">");
		out.println("<input name=\"Sekunden\">");
		out.println("<input type=\"submit\" value=\"Los!\">");
		out.println("</form>");

		out.println("</body>");
		out.println("</html>");
	}

	protected void doPost(HttpServletRequest request,
						  HttpServletResponse response)
			throws IOException, ServletException {
		doGet(request, response);
	}
}

class SleepingThread extends Thread {
	private int secs;
	private String message;

	public SleepingThread(int secs) {
		this.secs = secs;
	}

	public void run() {
		try {
			Thread.sleep(secs * 1000);
			message = "Es wurde " + secs
					+ " Sekunden geschlafen.";
		} catch (InterruptedException e) {
			message = "Es gab Probleme beim Schlafen.";
		}
	}

	public String getMessage() {
		return message;
	}
}
