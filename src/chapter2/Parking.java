package chapter2;

class ParkingGarage {
	private int places;

	public ParkingGarage(int places) {
		if (places < 0) {
			throw new IllegalArgumentException("Parameter < 0");
		}
		this.places = places;
	}

	public synchronized void enter() {
		while (places == 0) {
			try {
				wait();
			} catch (InterruptedException e) {
			}
		}
		places--;
	}

	public synchronized void leave() {
		places++;
		notify();
	}
}

class Car extends Thread {
	private ParkingGarage garage;

	public Car(String name, ParkingGarage garage) {
		super(name);
		this.garage = garage;
		start();
	}

	public void run() {
		while (true) {
			try {
				sleep((int) (Math.random() * 10000));
			} catch (InterruptedException e) {
			}
			synchronized (garage) {
				garage.enter();
				System.out.println(getName() + ": eingefahren");
			}
			try {
				sleep((int) (Math.random() * 20000));
			} catch (InterruptedException e) {
			}
			synchronized (garage) {
				garage.leave();
				System.out.println(getName() + ": ausgefahren");
			}
		}
	}
}

public class Parking {
	public static void main(String[] args) {
		ParkingGarage garage = new ParkingGarage(10);
		for (int i = 1; i <= 40; i++) {
			new Car("Auto " + i, garage);
		}
	}
}
