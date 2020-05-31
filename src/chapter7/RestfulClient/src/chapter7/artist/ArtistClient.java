package chapter7.RestfulClient.src.chapter7.artist;

import com.google.gson.Gson;

import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriBuilder;
import java.net.URI;
import java.util.HashMap;


public class ArtistClient {
	private static WebTarget target = ClientBuilder.newClient().target(getBaseURI());
	private static Gson gson = new Gson();

	private static URI getBaseURI() {
		return UriBuilder.fromUri("http://localhost:8081/Restful/rest/artists").build();
	}

	@SuppressWarnings("unchecked")
	private static HashMap<String, Artist> getAll() {
		String response = target.request().accept(MediaType.APPLICATION_JSON).get(String.class);
		System.out.println("getAll(): " + response);
		return gson.fromJson(response, HashMap.class);
	}

	private static Artist getArtist(String id) {
		String response = target.path(id).request().accept(MediaType.APPLICATION_JSON).get(String.class);
		System.out.println("getArtist(" + id + "): " + response);
		return gson.fromJson(response, Artist.class);
	}

	private static String addArtist(Artist a) {
		String input = gson.toJson(a);
		String response = target.request().post(Entity.entity(input, MediaType.APPLICATION_JSON)).readEntity(String.class);
		System.out.println("addArtist(...): " + response);
		return response;
	}

	private static Artist buildSample(int type) {
		switch (type) {
			case 0: {
				Cd cd1 = new Cd("Astral Weeks", 1968);
				Cd cd2 = new Cd("Moondance", 1970);
				Cd cd3 = new Cd("Keep Me Singing", 2016);
				Artist van = new Artist("Van Morrison", 1945, new Cd[]{cd1, cd2, cd3});
				return van;
			}
			case 1: {
				Cd cd1 = new Cd("Golden Heart", 1996);
				Cd cd2 = new Cd("Sailing to Philadelphia", 2000);
				Cd cd3 = new Cd("Get Lucky", 2009);
				Cd cd4 = new Cd("Privateering", 2012);
				Cd cd5 = new Cd("Tracker", 2015);
				Artist mark = new Artist("Mark Knopfler", 1949, new Cd[]{cd1, cd2, cd3, cd4, cd5});
				return mark;
			}
			default:
				return null;
		}
	}

	private static String deleteArtist(String id) {
		String result = target.path(id).request().delete(String.class);
		System.out.println("deleteArtist(" + id + "): " + result);
		return result;
	}

	public static void main(String[] args) {
		getAll();

		Artist a1 = buildSample(0);
		String id1 = addArtist(a1);

		Artist a2 = buildSample(1);
		String id2 = addArtist(a2);

		getAll();
		getArtist(id1);
		getArtist(id2);

		deleteArtist(id2);
		getAll();
		deleteArtist(id1);
		getAll();
	}
}
