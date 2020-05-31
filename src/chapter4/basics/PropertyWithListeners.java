package chapter4.basics;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;

class MySimpleChangeListener implements ChangeListener<Number> {
	public void changed(ObservableValue<? extends Number> observable,
						Number oldValue, Number newValue) {
		System.out.println(">>>ge�ndert von " + oldValue + " zu " + newValue);
	}
}

public class PropertyWithListeners {
	public static void main(String[] args) {
		SimpleIntegerProperty prop = new SimpleIntegerProperty();
		MySimpleChangeListener listener = new MySimpleChangeListener();
		prop.addListener(listener);
		prop.addListener((o, oldValue, newValue)
				-> System.out.println("***ge�ndert von " +
				oldValue + " zu " +
				newValue));

		for (int i = 1; i <= 20; i++) {
			int newValue = (int) (Math.random() * 10) - 5;
			System.out.println("�nderung: " + newValue);
			prop.set(newValue);
		}
	}
}
