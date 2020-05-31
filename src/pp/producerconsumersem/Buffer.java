package pp.producerconsumersem;

public class Buffer
{
    private final boolean available = false;

    private final Semaphore sem = new Semaphore(1);

    private int data;

    private int length = 0;

    public Buffer(int length)
    {
        if (length <= 0)
        {
            throw new IllegalArgumentException();
        }
        this.length = length;
    }

    public synchronized void put(int x)
    {
        sem.p();

        data = x;

    }

    public synchronized int get()
    {
        sem.v();
        return data;
    }

}