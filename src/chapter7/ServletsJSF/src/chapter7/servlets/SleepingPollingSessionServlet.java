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
@WebServlet("/SchlafenAbfragen")
public class SleepingPollingSessionServlet extends HttpServlet {
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

		HttpSession session = request.getSession(false);
		if (session != null) {
			SleepingThread t;
			t = (SleepingThread) session.getAttribute(
					"SleepingThread");
			if (t != null) {
				if (t.isAlive()) {
					out.println("Der Auftrag ist noch nicht "
							+ "beendet!");
					response.setHeader("Refresh", "5");
				} else {
					out.println("<b>Der Auftrag ist beendet!</b>");
					out.println("<p>");
					try {
						t.join();
					} catch (InterruptedException e) {
					}
					out.print("Das Ergebnis lautet: "
							+ t.getMessage());
					out.println("<p>");
					out.println("<a "
							+ "href=\"SchlafenSitzung\">"
							+ "Zur&uuml;ck!</a>");
					session.invalidate();
				}
			} else {
				out.println("Fehler: Der Auftrag ist unbekannt!");
				out.println("<p>");
				out.println("<a "
						+ "href=\"SchlafenSitzung\">"
						+ "Zur&uuml;ck!</a>");
			}
		} else {
			out.println("Fehler: Es gibt keine Sitzung!");
			out.println("<p>");
			out.println("<a href=\"SchlafenSitzung\">"
					+ "Zur&uuml;ck!</a>");
		}

		out.println("</body>");
		out.println("</html>");
	}
}
