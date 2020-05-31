package chapter7.RestfulClient.src.tutorial.rest.client;

import com.google.gson.Gson;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import java.net.URI;

class Person {
	private String name;
	private int age;
	private Person bestFriend;

	public Person(String name, int age, Person bestFriend) {
		super();
		this.name = name;
		this.age = age;
		this.bestFriend = bestFriend;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public Person getBestFriend() {
		return bestFriend;
	}

	public void setBestFriend(Person bestFriend) {
		this.bestFriend = bestFriend;
	}
}

public class PersonClient {
	public static void main(String[] args) {
		Client client = ClientBuilder.newClient();
		WebTarget target = client.target(getBaseURI());

		String response = target.path("rest").path("person").path("olli").request().accept(MediaType.APPLICATION_JSON).get(Response.class).toString();
		String jsonAnswer1 = target.path("rest").path("person").path("olli").request().accept(MediaType.APPLICATION_JSON).get(String.class);
		String jsonAnswer2 = target.path("rest").path("person").path("stan").request().accept(MediaType.APPLICATION_JSON).get(String.class);

		System.out.println(response);
		System.out.println();
		System.out.println(jsonAnswer1);
		System.out.println();
		System.out.println(jsonAnswer2);
		System.out.println();

		Gson gson = new Gson();
		Person newPerson = gson.fromJson(jsonAnswer2, Person.class);
		System.out.println(newPerson.getName() + " / " +
				newPerson.getAge() + " / " +
				newPerson.getBestFriend().getName());

		String input = "{\"name\": \"Charly Chaplin\", \"age\": 42}";
		response = target.path("rest").path("person").request().post(Entity.entity(input, MediaType.TEXT_PLAIN)).toString();
		System.out.println(response);
		System.out.println();

		String result = target.path("rest").path("person").path("stan").request().delete(String.class);
		System.out.println(result);
		System.out.println();
	}

	private static URI getBaseURI() {
		return UriBuilder.fromUri("http://localhost:8080/Restful").build();
	}
}
