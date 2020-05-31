package chapter7.Restful.src.chapter7.artist;

import com.google.gson.Gson;

class Cd {
	private String title;
	private int year;

	public Cd(String title, int year) {
		super();
		this.title = title;
		this.year = year;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}
}

class Artist {
	private String name;
	private int born;
	private Cd[] cds;

	public Artist(String name, int born, Cd[] cds) {
		super();
		this.name = name;
		this.born = born;
		this.cds = cds;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getBorn() {
		return born;
	}

	public void setBorn(int born) {
		this.born = born;
	}

	public Cd[] getCds() {
		return cds;
	}

	public void setCds(Cd[] cds) {
		this.cds = cds;
	}
}

public class JsonExample {
	public static void main(String[] args) {
		Cd cd1 = new Cd("Astral Weeks", 1968);
		Cd cd2 = new Cd("Moondance", 1970);
		Cd cd3 = new Cd("Keep Me Singing", 2016);
		Artist van = new Artist("Van Morrison", 1945, new Cd[]{cd1, cd2, cd3});
		Gson gson = new Gson();
		String jsonString = gson.toJson(van);
		System.out.println(jsonString);

		Artist van2 = gson.fromJson(jsonString, Artist.class);
		System.out.println(van2.getName() + "/" + van2.getBorn());
		for (Cd cd : van2.getCds()) {
			System.out.println("   " + cd.getTitle() + "/" + cd.getYear());
		}
	}
}
