import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.Condition;

public class PetersonLock implements Lock
{
    private volatile int victim;
    private volatile boolean[] flag = new boolean[2];
    
    @Override
    public void lock()
    {
        int i = (int) (MyThread.currentThread().threadId() % 2);
        int j = 1 - i;

        flag[i] = true;
        victim = i;
        
        while (flag[j] && victim == i) {}
    }

    @Override
    public void unlock()
    {
        int i = (int) (MyThread.currentThread().threadId() % 2);
        flag[i] = false;
    }

    @Override
    public void lockInterruptibly() throws InterruptedException {}

    @Override
    public boolean tryLock()
    {
        return false;
    }

    @Override
    public boolean tryLock(long time, TimeUnit unit) throws InterruptedException
    {
        return false;
    }

    @Override
    public Condition newCondition()
    {
        return null;
    }
}