package chapter7.Restful.src.tutorial.rest;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
// JAX-RS supports an automatic mapping from JAXB annotated class to XML and
// JSON
// Isn't that cool?
class Todo {
	private String id;
	private String summary;
	private String description;

	public Todo() {
	}

	public Todo(String id, String summary) {
		this.id = id;
		this.summary = summary;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getSummary() {
		return summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
}

@Path("/todo")
public class ToDoResource {
	// This method is called if XMLis request
	@GET
	@Produces(
			{MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	public Todo getJSON() {
		Todo todo = new Todo("JSON", "This is my JSON todo");
		todo.setDescription("This is my JSON todo");
		return todo;
	}

	// This can be used to test the integration with the browser
	@GET
	@Produces(MediaType.TEXT_XML)
	public Todo getXML() {
		Todo todo = new Todo("XML", "This is my XML todo");
		todo.setDescription("This is my XML todo");
		return todo;
	}
}
