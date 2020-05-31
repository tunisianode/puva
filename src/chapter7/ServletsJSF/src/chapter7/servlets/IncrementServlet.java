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
@WebServlet("/Erhoehen")
public class IncrementServlet extends HttpServlet {
	protected void doPost(HttpServletRequest request,
						  HttpServletResponse response)
			throws IOException, ServletException {
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		out.println("<html><head>");
		out.println("<title>Z&auml;hler</title>");
		out.println("</head>");
		out.println("<body>");
		out.println("<h1>Erh&ouml;ung des Z&auml;hlers</h1>");

		ServletContext ctx = getServletContext();
		Counter counter = (Counter) ctx.getAttribute("Counter");
		int value = counter.increment();
		out.println("Der Z&auml;hler wurde auf " + value
				+ " erh&ouml;ht.<p>");

		out.println("</body>");
		out.println("</html>");
	}
}
