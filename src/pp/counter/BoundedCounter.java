package pp.counter;

public class BoundedCounter extends Thread
{

    private int count;

    private final int min;

    private final int max;

    public BoundedCounter(int min, int max)
    {
        if (min >= max)
        {
            throw new IllegalArgumentException();
        }
        this.max = max;
        this.min = min;
        this.count = min;
    }

    public synchronized int get()
    {
        return this.count;
    }

    public void run()
    {

    }

    public synchronized void up()
    {
        while (count == max)
        {
            try
            {
                wait();
            }
            catch (InterruptedException ex)
            {
                ex.printStackTrace();

            }
        }
        count++;
        notifyAll();
    }

    public synchronized void down()
    {
        while (count == min)
        {
            try
            {
                wait();
            }
            catch (InterruptedException ex)
            {
                ex.printStackTrace();
            }
        }
        count--;
        notifyAll();
    }

    public static void main(String[] args)
    {

    }
}
