import java.util.Random;
import java.util.concurrent.locks.Lock;

public class SharedResources
{
	private static int counter = 0;
    private Lock l = new Bakery(5);

	private Random random = new Random();
	private int delay = random.nextInt(1000 - 200 + 1) + 200;

	public void access()
	{
        l.lock();
		
        try
        {
			counter = counter + 1;
			Thread.sleep(delay);
        }
		catch (InterruptedException e)
		{
            e.printStackTrace();
        }
        finally
        {
            l.unlock();
        }
	}
}