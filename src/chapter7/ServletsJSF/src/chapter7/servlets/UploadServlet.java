package chapter7.ServletsJSF.src.chapter7.servlets;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Enumeration;

@WebServlet("/Hochladen")
public class UploadServlet extends HttpServlet {
	private static final long serialVersionUID = -827092251504276507L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		out.println("<html>");
		out.println("<head>");
		out.println("<title>Antwort auf Hochladen" + "</title>");
		out.println("</head>");
		out.println("<body>");
		out.println("<h1>Antwort auf Hochladen</h1>");
		out.println("<h3>Folgende Kopffelder sind " + "angekommen:</h3>");
		out.println("<xmp>");

		Enumeration<?> headers = request.getHeaderNames();
		while (headers.hasMoreElements()) {
			String header = (String) headers.nextElement();
			String hvalue = request.getHeader(header);
			out.println(header + ": " + hvalue);
		}
		out.println("</xmp>");
/*
        out.println("<h3><Zugriff auf Eingabefeld:</h3>");
        String name = request.getParameter("nachname");
        if(name == null)
        {
            out.println("Eingabefeld �ber request.getParameter " +
                        "nicht erreichbar!!!!");
        }
        else
        {
            out.println("Eingabefeld �ber request.getParameter " +
                        "erreichbar: " + name);
        }
*/
		out.println("<h3>Folgende Daten sind " + "angekommen:</h3>");
		out.println("<xmp>");
		BufferedReader br = request.getReader();
		String line;
		while ((line = br.readLine()) != null) {
			out.println(line); // --> Ausgabe der Datei
		}
		out.println("</xmp>");
		out.println("</body></html>");
	}
}
