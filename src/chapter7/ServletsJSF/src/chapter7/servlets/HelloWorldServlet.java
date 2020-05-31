package chapter7.ServletsJSF.src.chapter7.servlets;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/HalloWelt")
public class HelloWorldServlet extends HttpServlet {
	private static final long serialVersionUID = -897846265692477184L;

	public void init() {
		System.out.println("method init of HelloWorld servlet called");
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		System.out.println("method doGet of HelloWorld servlet called");

		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		out.println("<html>");
		out.println("<head>");
		out.println("<title>Hallo Welt!</title>");
		out.println("</head>");
		out.println("<body>");
		out.println("<h1>Hallo Welt!</h1>");
		out.println("</body>");
		out.println("</html>");
	}
}
