public class TDemo extends Thread
{
	public void run()
	{
		System.out.println(String.format("%s is running...", this.getName()));
	}
}