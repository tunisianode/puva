package chapter7.ServletsJSF.src.chapter7.servlets;

import javax.servlet.AsyncContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.concurrent.DelayQueue;
import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

@SuppressWarnings("serial")
@WebServlet(value = "/SchlafenAsync", asyncSupported = true,
		loadOnStartup = 1)
public class SleepingServletAsync extends HttpServlet {
	private DelayQueue<Job> queue;
	private SingleServletWorker worker;

	public void init() {
		queue = new DelayQueue<>();
		worker = new SingleServletWorker(queue);
		worker.start();
	}

	public void destroy() {
		worker.interrupt();
	}

	public void doGet(HttpServletRequest request,
					  HttpServletResponse response)
			throws IOException, ServletException {
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		out.println("<html>");
		out.println("<head>");
		out.println("<title>Asynchrones Schlafen</title>");
		out.println("</head>");
		out.println("<body>");
		out.println("<h1>Asynchrones Schlafen</h1>");
		out.println("<h3>Das Servlet wird ausgef�hrt von " +
				"Thread \"" + Thread.currentThread().getName() +
				"\".</h3>");

		String secsString = request.getParameter("Sekunden");
		if (secsString != null) {
			try {
				long msecs = Integer.parseInt(secsString) * 1000L;
				if (!request.isAsyncSupported()) {
					request.setAttribute(
							"org.apache.catalina.ASYNC_SUPPORTED",
							true);
				}
				AsyncContext asyncContext = request.startAsync();
				asyncContext.setTimeout(msecs + 1000L);
				Job job = new Job(msecs, asyncContext);
				queue.put(job);
			} catch (NumberFormatException e) {
				out.println("<p>Keine/falsche Sekundenangabe!</p>");
				out.println("</body>");
				out.println("</html>");
			}
		} else {
			out.println("<h2>GET-Formular</h2>");
			out.println("<p><form method=\"get\">");
			out.println("<input name=\"Sekunden\">");
			out.println("<input type=\"submit\" value=\"Los!\">");
			out.println("</form></p>");
		}
	}
}

class Job implements Delayed {
	private static long jobCounter;

	private long startTime;
	private long delay;
	private AsyncContext asyncContext;
	private long jobNumber;

	public Job(long delay, AsyncContext asyncContext) {
		this.startTime = System.currentTimeMillis();
		this.delay = delay;
		this.asyncContext = asyncContext;
		this.jobNumber = getNextJobNumber();
	}

	private static synchronized long getNextJobNumber() {
		return ++jobCounter;
	}

	public long getDelay() {
		return delay;
	}

	public AsyncContext getAsyncContext() {
		return asyncContext;
	}

	public long getJobNumber() {
		return jobNumber;
	}

	public long getDelay(TimeUnit unit) {
		long expireTime = startTime + delay;
		return unit.convert(expireTime - System.currentTimeMillis(),
				TimeUnit.MILLISECONDS);
	}

	public int compareTo(Delayed o) {
		long d = getDelay(TimeUnit.MILLISECONDS) -
				o.getDelay(TimeUnit.MILLISECONDS);
		return d == 0 ? 0 : (d < 0 ? -1 : 1);
	}
}

class SingleServletWorker extends Thread {
	private DelayQueue<Job> queue;

	public SingleServletWorker(DelayQueue<Job> queue) {
		this.queue = queue;
	}

	public void run() {
		while (!isInterrupted()) {
			Job job;
			try {
				job = queue.take();
			} catch (InterruptedException e1) {
				System.out.println("Worker-Thread beendet sich.");
				return;
			}

			AsyncContext asyncContext = job.getAsyncContext();
			PrintWriter out;
			try {
				out = asyncContext.getResponse().getWriter();
			} catch (Exception e) {
				continue;
			}

			long delay = job.getDelay();
			out.println("<p>Der Auftrag mit der Bearbeitungsdauer" +
					" von " + delay + " Millisekunden wurde" +
					" bearbeitet.</p>");
			out.println("<p>Die Antwort wurde von Thread \"" +
					Thread.currentThread().getName() +
					"\" erzeugt.</p>");
			out.println("<p>Damit ist Job Nr. " +
					job.getJobNumber() + " abgearbeitet.</p>");
			out.println("</body>");
			out.println("</html>");
			asyncContext.complete();
		}
		System.out.println("Worker-Thread hat Schleife verlassen.");
	}
}
