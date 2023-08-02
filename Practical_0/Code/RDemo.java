public class RDemo implements Runnable
{
	public void run(){
		System.out.println(String.format("%s is running...", Thread.currentThread().getName()));
	}
}
