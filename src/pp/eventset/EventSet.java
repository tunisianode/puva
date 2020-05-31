package pp.eventset;

public class EventSet
{
    private final boolean[] feld;

    private final int length;

    public EventSet(int length)
    {
        if (length < 0)
        {
            throw new IllegalArgumentException();
        }
        this.length = length;
        this.feld = new boolean[length];

    }

    public synchronized void set(int index, boolean value)
    {
        if (index < 0 || index >= this.length)
        {
            throw new IllegalArgumentException();
        }
        System.out.println("set " + value + " at " + index);
        feld[index] = value;
        notifyAll();
    }

    public synchronized void waitAND()
    {
        for (int i = 0; i < feld.length - 1; i++)
        {
            while (feld[i] != feld[i + 1])
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
        }
        System.out.println("waitAND");
        notifyAll();
    }

    public synchronized void waitOR()
    {
        for (int i = 0; i < feld.length; i++)
        {
            while (!feld[i])
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
            System.out.println("waitOR");
            notifyAll();
        }
    }

    public static void main(String[] args)
    {
        EventSet s = new EventSet(3);
        new Thread(new Runnable()
        {
            @Override public void run()
            {
                s.set(2, true);
            }
        }

        ).start();

        new Thread(new Runnable()
        {
            @Override public void run()
            {
                s.waitAND();
            }
        }

        ).start();

        new Thread(new Runnable()
        {
            @Override public void run()
            {
                s.waitOR();
            }
        }

        ).start();

    }

}
