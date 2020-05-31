package chapter7.ServletsJSF.src.chapter7.servlets;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@SuppressWarnings("serial")
@WebServlet("/Schlafen")
public class SleepingServlet extends HttpServlet {
	protected void doGet(HttpServletRequest request,
						 HttpServletResponse response)
			throws IOException, ServletException {
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		out.println("<html><head><title>Schlafen</title>");
		out.println("</head><body><h1>Schlafen</h1>");
		String secsString = request.getParameter("Sekunden");
		if (secsString != null) {
			try {
				int secs = Integer.parseInt(secsString);
				Thread.sleep(secs * 1000);
				out.println("<p>Es wurde " + secs
						+ " Sekunden geschlafen.</p>");
			} catch (Exception e) {
				out.println("<p>Es gab Probleme beim Schlafen.</p>");
			}
		} else {
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
		}
		out.println("</body></html>");
	}

	protected synchronized void doPost(HttpServletRequest request,
									   HttpServletResponse response)
			throws IOException, ServletException {
		doGet(request, response);
	}
}
