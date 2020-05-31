package chapter7.ServletsJSF.src.chapter7.servlets;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@SuppressWarnings("serial")
@WebServlet(value = "/Zuruecksetzen", loadOnStartup = 1)
public class ResetServlet extends HttpServlet {
	public void init() {
		Counter counter = new Counter();
		ServletContext ctx = getServletContext();
		ctx.setAttribute("Counter", counter);
	}

	protected void doPost(HttpServletRequest request,
						  HttpServletResponse response)
			throws IOException, ServletException {
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		out.println("<html><head>");
		out.println("<title>Z&auml;hler</title>");
		out.println("</head>");
		out.println("<body>");
		out.println("<h1>Zur&uuml;cksetzen des Z&auml;hlers</h1>");

		ServletContext ctx = getServletContext();
		Counter counter = (Counter) ctx.getAttribute("Counter");
		int value = counter.reset();
		out.println("Der Z&auml;hler wurde auf " + value
				+ " zur&uuml;ckgesetzt.<p>");

		out.println("</body>");
		out.println("</html>");
	}
}
