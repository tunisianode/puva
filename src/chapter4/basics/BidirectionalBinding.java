package chapter4.basics;

import javafx.beans.property.SimpleIntegerProperty;

public class BidirectionalBinding {
	public static void main(String[] args) {
		SimpleIntegerProperty prop1 = new SimpleIntegerProperty();
		SimpleIntegerProperty prop2 = new SimpleIntegerProperty();
		prop1.bindBidirectional(prop2); //oder prop2.bindBidirectional(prop1);
		System.out.println(prop1.get() + " / " + prop2.get()); // => 0 / 0
		prop1.set(101);
		System.out.println(prop1.get() + " / " + prop2.get()); // => 101 / 101
		prop2.set(49);
		System.out.println(prop1.get() + " / " + prop2.get()); // => 49 / 49
	}
}
