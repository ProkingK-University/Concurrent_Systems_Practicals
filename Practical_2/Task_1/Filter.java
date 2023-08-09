import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.Condition;

// Name: Ashley
// Student Number: u21525600

public class Filter implements Lock
{
	private int numberOfThreads;
	private volatile int[] level;
    private volatile int[] victim;

	public Filter(int numberOfThreads)
	{
		level = new int[numberOfThreads];
        victim = new int[numberOfThreads];
        this.numberOfThreads = numberOfThreads;

        for (int i = 0; i < numberOfThreads; i++)
		{
            level[i] = 0;
        }
    }

	@Override
    public void lock()
	{
        int i = (int) (Thread.currentThread().threadId() % numberOfThreads);

        for (int l = 1; l < numberOfThreads; l++)
		{
            level[i] = l;
            victim[l] = i;

			System.out.println(Thread.currentThread().getName() + ": " + "level[" + i + "] = " + l + " , victim[" + l + "] = " + i);

            boolean conflicting = true;

            while (conflicting)
			{
                conflicting = false;

                for (int k = 0; k < numberOfThreads; k++)
				{
                    if (k != i && level[k] >= l && victim[l] == i)
					{
                        conflicting = true;

                        break;
                    }
                }
            }
        }
    }

	@Override
	public void unlock()
	{

		int i = (int) (Thread.currentThread().threadId() % numberOfThreads);
        level[i] = 0;
		System.out.println(Thread.currentThread().getName() + ": ----------------------- DONE");
	}

	public boolean tryLock()
	{
		return false;
	}
	
	public Condition newCondition()
	{
		return null;
	}

	public void lockInterruptibly() throws InterruptedException
	{

	}

	public boolean tryLock(long time, TimeUnit unit) throws InterruptedException
	{
		return false;
	}
}