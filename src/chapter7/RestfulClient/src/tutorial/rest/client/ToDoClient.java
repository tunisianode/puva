package chapter7.RestfulClient.src.tutorial.rest.client;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriBuilder;
import java.net.URI;

public class ToDoClient {
	public static void main(String[] args) {
		Client client = ClientBuilder.newClient();

		WebTarget target = client.target(getBaseURI());
		// Get XML
		String xmlResponse = target.path("rest").path("todo").request().accept(MediaType.TEXT_XML).get(String.class);
		// Get XML for application
		String xmlAppResponse = target.path("rest").path("todo").request().accept(MediaType.APPLICATION_XML).get(String.class);

		// Get JSON for application
		//System.out.println(target.path("rest").path("todo").request().accept(MediaType.APPLICATION_JSON).get(String.class));

		System.out.println(xmlResponse);
		System.out.println(xmlAppResponse);
	}

	private static URI getBaseURI() {
		return UriBuilder.fromUri("http://localhost:8080/Restful").build();
	}
}
