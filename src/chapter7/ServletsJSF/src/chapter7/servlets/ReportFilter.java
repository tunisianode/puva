package chapter7.ServletsJSF.src.chapter7.servlets;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@WebFilter(filterName = "ReportFilter", urlPatterns = {"/*"})
public class ReportFilter implements Filter {
	public void init(FilterConfig filterConfig)
			throws ServletException {
	}

	public void destroy() {
	}

	public void doFilter(ServletRequest request,
						 ServletResponse response,
						 FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) request;
		System.out.println(req.getRemoteHost() + ":  Zugriff auf " +
				req.getRequestURL() + ".");
		chain.doFilter(request, response);
	}
}
