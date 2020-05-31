package pp.filelocking;

public class LockInterval
{
    private int begin;

    private int end;

    private boolean status;

    public LockInterval(int begin, int end)
    {
        this.begin = begin;
        this.end = end;
        this.status = true;
    }

    public int getBegin()
    {
        return begin;
    }

    public void setBegin(int begin)
    {
        this.begin = begin;
    }

    public int getEnd()
    {
        return end;
    }

    public void setEnd(int end)
    {
        this.end = end;
    }

    public boolean isStatus()
    {
        return status;
    }

    public void setStatus(boolean status)
    {
        this.status = status;
    }

    public boolean isCollapst(LockInterval cpxsperrintervall)
    {
        return (this.begin <= cpxsperrintervall.begin && this.end >= cpxsperrintervall.begin) || (this.begin <= cpxsperrintervall.end && this.end > cpxsperrintervall.end);
    }

}
