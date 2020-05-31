package chapter7.ServletsJSF.src.chapter7.servlets;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@SuppressWarnings("serial")
@WebServlet("/Tee")
public class TeaServlet extends HttpServlet {
	protected void doPost(HttpServletRequest request,
						  HttpServletResponse response)
			throws IOException, ServletException {
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		out.println("<html>");
		out.println("<head>");
		out.println("<title>Servlet-Bearbeitung der Teebestellung");
		out.println("</title>");
		out.println("</head>");
		out.println("<body>");
		out.println("<h1>Auftragsbest&auml;tigung</h1>");
		out.println("<p>Hiermit best&auml;tigen wir Ihren Auftrag:</p>");
		out.println("<ul>");
		out.println("<li>Teesorte: " +
				request.getParameter("Teesorte") + "</li>");
		out.println("<li>bestellt von: " +
				request.getParameter("Besteller") + "</li>");
		out.println("<li>Kartennummer (nur zur Demo angezeigt): " +
				request.getParameter("Kreditkartennummer") + "</li>");
		if (request.getParameter("Eile") != null) {
			out.println("<li><b>Eilbestellung</b></li>");
		}
		out.println("</ul>");
		out.println("<p>Vielen Dank f&uuml;r Ihren Auftrag. "
				+ "Bestellen Sie bald wieder bei uns!</p>");
		out.println("</body>");
		out.println("</html>");
	}

	protected void doGet(HttpServletRequest request,
						 HttpServletResponse response)
			throws IOException, ServletException {
		doPost(request, response);
	}
}
