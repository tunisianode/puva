package chapter3;

abstract class AccessControl {
	private int activeReaders = 0;
	private int activeWriters = 0;
	@SuppressWarnings("unused")
	private int waitingReaders = 0;
	private int waitingWriters = 0;

	protected abstract Object reallyRead();

	protected abstract void reallyWrite(Object obj);

	public Object read() {
		beforeRead();
		Object obj = reallyRead();
		afterRead();
		return obj;
	}

	public void write(Object obj) {
		beforeWrite();
		reallyWrite(obj);
		afterWrite();
	}

	private synchronized void beforeRead() {
		waitingReaders++;
		while (waitingWriters != 0 || activeWriters != 0) {
			try {
				wait();
			} catch (InterruptedException e) {
			}
		}
		waitingReaders--;
		activeReaders++;
	}

	private synchronized void afterRead() {
		activeReaders--;
		notifyAll();
	}

	private synchronized void beforeWrite() {
		waitingWriters++;
		while (activeReaders != 0 || activeWriters != 0) {
			try {
				wait();
			} catch (InterruptedException e) {
			}
		}
		waitingWriters--;
		activeWriters++;
	}

	private synchronized void afterWrite() {
		activeWriters--;
		notifyAll();
	}
}

class IntData extends AccessControl {
	private int data;

	protected Object reallyRead() {
		return new Integer(data);
		//return data;
	}

	protected void reallyWrite(Object obj) {
		data = ((Integer) obj).intValue();
		//data = (Integer) obj;
	}
}

class Reader extends Thread {
	private IntData data;

	public Reader(IntData data) {
		this.data = data;
		start();
	}

	public void run() {
		for (int i = 0; i < 10000; i++) {
			Integer integer = (Integer) data.read();
			int iValue = integer.intValue();
			// mit iValue arbeiten, z. B.
			System.out.println("iValue = " + iValue);
		}
	}
}

class Writer extends Thread {
	private IntData data;

	public Writer(IntData data) {
		this.data = data;
		start();
	}

	public void run() {
		for (int i = 0; i < 10000; i++) {
			data.write(new Integer(i));
		}
	}
}

public class ReaderWriter {
	public static void main(String[] args) {
		IntData data = new IntData();
		for (int i = 0; i < 100; i++) {
			new Writer(data);
			new Reader(data);
		}
	}
}
