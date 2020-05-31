package pp.filelocking;

import java.util.LinkedList;

public class File extends Thread
{
    private final int length;

    private final LinkedList<LockInterval> lsInterval;

    public File(int length)
    {
        if (length < 0)
        {
            throw new IllegalArgumentException("length kleiner 0");
        }
        this.length = length;
        this.lsInterval = new LinkedList<>();
    }

    public synchronized void lock(int begin, int end)
    {
        if (begin < 0 || begin >= length || end < 0 || end >= length || end < begin)
        {
            throw new IllegalArgumentException("Ungültiger Intervall");
        }

        LockInterval interval = new LockInterval(begin, end);

        for (LockInterval cpx : this.lsInterval)
        {
            while (isInIntervallList(begin, end))
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
        this.lsInterval.add(interval);
        System.out.println("das intervall [" + begin + "," + end + "] wurde gelockt von " + this.getName());
        this.notify();

    }

    public synchronized void unlock(int begin, int end)
    {
        /*if (begin < 0 || end < 0 || begin > end || end > this.length - 1 || this.lsInterval.isEmpty())
        {
            throw new IllegalArgumentException();
        }
        else
        {
            // Sperrintervall cpx_sperintervall = new Sperrintervall(start,
            // end);
            for (int i = 0; i < this.lsInterval.size(); i++)
            {
                // if (this.lslocked.get(i).equals(cpx_sperintervall))
                if (begin == this.lsInterval.get(i).getBegin() && end == this.lsInterval.get(i).getEnd())
                {
                    if (this.lsInterval.get(i).isStatus())
                    {
                        this.lsInterval.get(i).setStatus(false);
                        this.lsInterval.remove(i);
                    }
                }
                else
                {
                    throw new IllegalArgumentException();
                }

            }
            this.notifyAll();
        }*/

        if (begin < 0 || end < 0 || begin > end || end > this.length - 1 || this.lsInterval.isEmpty())
        {
            throw new IllegalArgumentException();
        }
        else
        {

            for (LockInterval cpx : lsInterval)
            {
                if (cpx.getEnd() == end && cpx.getBegin() == begin)
                {
                    System.out.println("das intervall [" + begin + "," + end + "] wurde unlockt von " + this.getName());
                    cpx.setStatus(false);
                    lsInterval.remove(cpx);

                }
                else
                {
                    throw new IllegalArgumentException();
                }
                System.out.println("Methode unlock: notifyAll");
                this.notifyAll();

            }

        }

    }

    public static void main(String[] args)
    {

        /*final File file = new File(3);
        Thread T1 = new Thread(new Runnable()
        {
            public void run()
            {
                file.lock(0, 1);
                file.lock(1, 2);
            }
        });
        T1.start();
        Thread T2 = new Thread(new Runnable()
        {
            public void run()
            {
                file.unlock(0, 1);
            }
        });
        T2.start();
        Thread T3 = new Thread(new Runnable()
        {
            public void run()
            {
                //file.unlock(0, 1);
            }
        });
        T3.start();*/
    }

    public void run()
    {
    }

    public boolean isInIntervallList(int begin, int end)
    {
        if (!this.lsInterval.isEmpty())
        {
            for (int i = 0; i < this.lsInterval.size(); i++)
            {
                if (!((begin < this.lsInterval.get(i).getBegin() && end < this.lsInterval.get(i).getBegin()) || (begin > this.lsInterval.get(i).getEnd() && end > this.lsInterval.get(i).getEnd())))
                {
                    return true;
                }
            }
        }
        return false;
    }
}
