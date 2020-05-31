package chapter4.basics;

interface Role {
	public void play();
}

class Hamlet implements Role {
	public void play() {
		System.out.println("Sein oder Nichtsein, das ist hier die Frage");
	}
}

class Faust implements Role {
	public void play() {
		System.out.println("Da steh' ich nun, ich armer Tor, und bin so klug als wie zuvor!");
	}
}

class Actor {
	private Role role;

	public void setOnAction(Role role) {
		this.role = role;
	}

	public void action() {
		if (role != null) {
			role.play();
		}
	}
}

public class Theater {
	public static void main(String[] args) {
		Actor actor = new Actor();
		actor.action();

		actor.setOnAction(new Hamlet());
		actor.action();
		actor.setOnAction(new Faust());
		actor.action();

		actor.setOnAction(() -> System.out.println("Sein oder Nichtsein ..."));
		actor.action();
		actor.setOnAction(() -> System.out.println("Da steh' ich nun ..."));
		actor.action();
	}
}
