package chapter3;

import java.util.concurrent.atomic.AtomicReference;

class Element<T> {
	private volatile T value;
	private volatile Element<T> next;

	public Element(T value, Element<T> next) {
		this.value = value;
		this.next = next;
	}

	public T getValue() {
		return value;
	}

	public Element<T> getNext() {
		return next;
	}
}

public class SyncStack<T> {
	private volatile AtomicReference<Element<T>> ar;

	public SyncStack() {
		ar = new AtomicReference<>();
	}

	public void push(T t) {
		Element<T> oldHead, newHead;
		do {
			oldHead = ar.get();
			newHead = new Element<>(t, oldHead);
		} while (!ar.compareAndSet(oldHead, newHead));
	}

	public T pop() {
		Element<T> oldHead, newHead;
		do {
			oldHead = ar.get();
			if (oldHead == null) {
				return null;
			}
			newHead = oldHead.getNext();
		} while (!ar.compareAndSet(oldHead, newHead));
		return oldHead.getValue();
	}
}
