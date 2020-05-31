package chapter7.RestfulClient.src.tutorial.rest.client;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import java.net.URI;

public class HelloClient {
	public static void main(String[] args) {
		Client client = ClientBuilder.newClient();
		WebTarget target = client.target(getBaseURI());

		String response = target.path("rest").path("hello").request().accept(MediaType.TEXT_PLAIN).get(Response.class).toString();
		String plainAnswer = target.path("rest").path("hello").request().accept(MediaType.TEXT_PLAIN).get(String.class);
		String xmlAnswer = target.path("rest").path("hello").request().accept(MediaType.TEXT_XML).get(String.class);
		String htmlAnswer = target.path("rest").path("hello").request().accept(MediaType.TEXT_HTML).get(String.class);

		System.out.println(response);
		System.out.println();
		System.out.println(plainAnswer);
		System.out.println();
		System.out.println(xmlAnswer);
		System.out.println();
		System.out.println(htmlAnswer);
		System.out.println();
	}

	private static URI getBaseURI() {
		return UriBuilder.fromUri("http://localhost:8080/Restful").build();
	}
}
