import java.util.concurrent.locks.Lock;

public class SharedResources
{
    Lock l;
	private static int counter = 0;

	public void access()
	{
		counter = counter + 1;
	}
}