package chapter7.Restful.src.tutorial.rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Hashtable;


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

@Path("person")
public class PersonResource {
	private static final String SUCCESS_RESULT = "<result>success</result>";
	private static final String FAILURE_RESULT = "<result>failure</result>";

	private static Hashtable<String, Person> persons; //synchronized!!!
	private static int counter;

	static {
		persons = new Hashtable<>();
		Person olli = new Person("Oliver Hardy", 28, null);
		Person stan = new Person("Stan Laurel", 29, olli);
		persons.put("olli", olli);
		persons.put("stan", stan);
	}

	private int instanceNumber;

	public PersonResource() {
		synchronized (PersonResource.class) {
			counter++;
			instanceNumber = counter;
		}
		System.out.println("new object no. " + instanceNumber);
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("{id}")
	public static String getPerson(@PathParam("id") String id) {
		Person person = persons.get(id);
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		return gson.toJson(person);
	}

	@DELETE
	@Path("{id}")
	@Produces(MediaType.APPLICATION_XML)
	public static String deletePerson(@PathParam("id") String id) {
		if (persons.remove(id) != null) {
			return SUCCESS_RESULT;
		} else {
			return FAILURE_RESULT;
		}
	}

	@POST
	@Consumes(MediaType.TEXT_PLAIN)
	public Response createPerson(String json) {
		Person p = new Gson().fromJson(json, Person.class);
		if (p != null) {
			persons.put(p.getName(), p);
			return Response.status(201).entity("Okay").build();
		} else {
			return Response.status(201).entity("Failed").build();
		}
	}

	@GET
	@Produces(MediaType.TEXT_PLAIN)
	public String getNumberOfPersons() {
		return "" + persons.size();
	}
}
