package chapter7.Restful.src.chapter7.artist;

import com.google.gson.Gson;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.HashMap;

@Path("/artists")
public class ArtistManager {

	private static HashMap<String, Artist> allArtists = new HashMap<>();
	private static int number = 0;
	private static Gson gson = new Gson();

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public static synchronized String getAll() {
		System.out.println("getAll");
		return gson.toJson(allArtists);
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("{id}")
	public static synchronized String getArtist(@PathParam("id") String id) {
		System.out.println("getArtist(" + id + ")");
		return gson.toJson(allArtists.get(id));
	}

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public static synchronized String addArtist(String json) {
		System.out.println("addArtist(" + json + ")");
		Artist a = gson.fromJson(json, Artist.class);
		if (a != null) {
			number++;
			allArtists.put("" + number, a);
			return "" + number;
		} else {
			return null;
		}
	}

	@DELETE
	@Path("{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public static synchronized String deleteArtist(@PathParam("id") String id) {
		System.out.println("deleteArtist(" + id + ")");
		if (allArtists.remove(id) != null) {
			return id;
		} else {
			return "null";
		}
	}
}
