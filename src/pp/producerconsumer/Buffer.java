package pp.producerconsumer;

public class Buffer
{
    private final int[] data;

    private int zeiger;

    public Buffer(int x)
    {
        if (x <= 0)
        {
            throw new IllegalArgumentException("Wert ist ung�ltig");
        }
        data = new int[x];
        zeiger = -1;
    }

    public synchronized void put(int x)
    {
        while (zeiger >= (data.length - 1))
        {
            try
            {
                wait();
            }
            catch (InterruptedException e)
            {
                e.printStackTrace();
            }
        }
        data[++zeiger] = x;
        notifyAll();
    }

    public synchronized int get()
    {
        int x;
        while (zeiger < 0)
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

        x = data[0];
        for (int i = 1; i <= zeiger; i++)
        {
            data[i - 1] = data[i];
        }

        zeiger--;
        notifyAll();
        return x;

    }

}

