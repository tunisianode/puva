package chapter4.basics;

import javafx.beans.property.SimpleIntegerProperty;

public class UnidirectionalBinding {
	public static void main(String[] args) {
		SimpleIntegerProperty prop1 = new SimpleIntegerProperty();
		SimpleIntegerProperty prop2 = new SimpleIntegerProperty();
		prop2.bind(prop1); //prop2 ist an prop1 gekoppelt (nicht umgekehrt)
		System.out.println(prop1.get() + " / " + prop2.get()); // => 0 / 0
		prop1.set(101);
		System.out.println(prop1.get() + " / " + prop2.get()); // => 101 / 101

		try {
			prop2.set(49);
		} catch (Exception e) {
			System.out.println("das funktioniert also nicht");
		}
		prop2.unbind();
		prop2.set(73);
		System.out.println(prop1.get() + " / " + prop2.get()); // => 101 / 73
	}
}
