package chapter6.list;

import java.io.Serializable;

class ListItem implements Serializable {
	private int value;
	private ListItem next;

	public ListItem(int v) {
		value = v;
		next = null;
	}

	public int getValue() {
		return value;
	}

	public ListItem getNext() {
		return next;
	}

	public void setNext(ListItem n) {
		next = n;
	}
}

public class List implements Serializable {
	private ListItem first, last;

	public void append(int i) {
		if (first == null) {
			first = new ListItem(i);
			last = first;
		} else {
			last.setNext(new ListItem(i));
			last = last.getNext();
		}
	}

	public void print() {
		ListItem item = first;
		while (item != null) {
			System.out.print(item.getValue() + " ");
			item = item.getNext();
		}
		System.out.println();
	}
}
