public class Counter
{
    private int value;
    private PetersonLock lock = new PetersonLock();

    public Counter(int number)
    {
        value = number;
    }

    public int getAndIncrement()
    {
        int temp = 0;

        lock.lock();

        try
        {
            temp = value;
            value = value + 1;
        }
        finally
        {
            lock.unlock();
        }

        return temp;
    }
}