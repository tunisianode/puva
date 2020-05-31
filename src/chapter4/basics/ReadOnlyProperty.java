package chapter4.basics;

import javafx.beans.property.ReadOnlyIntegerProperty;
import javafx.beans.property.ReadOnlyIntegerWrapper;

public class ReadOnlyProperty {
	public static void main(String[] args) {
		ReadOnlyIntegerWrapper wrapper = new ReadOnlyIntegerWrapper();
		ReadOnlyIntegerProperty readonly = wrapper.getReadOnlyProperty();
		readonly.addListener((o, oldValue, newValue)
				-> System.out.println("***changed from " +
				oldValue + " to " +
				newValue));

		for (int i = 1; i <= 20; i++) {
			int newValue = (int) (Math.random() * 10) - 5;
			System.out.println("changing to " + newValue);
			wrapper.set(newValue);
			//readonly.set(newValue);
		}
	}
}
