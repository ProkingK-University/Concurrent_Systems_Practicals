import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

// Name: Ashley
// Student Number: u21525600

public class Filter implements Lock
{
	@Override
	public void lock()
	{

	}

	@Override
	public void unlock()
	{

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