package chapter2;

public class SomethingToRun2 {
	public static void main(String[] args) {
		new Thread(() -> System.out.println("Hallo Welt")).start();
	}
}
