public class Main
{
    public static void main(String[] args)
    {
        Thread[] threads = new TDemo[5];
        Thread[] runs = new Thread[5];

        for (int i = 0; i < 5; i++)
        {
            threads[i] = new TDemo();
            runs[i] = new Thread(new RDemo());
        }

        System.out.println("Threads created by extending the Thread class:");

        // Start running threads
        for (Thread t : threads)
        {
            t.start();
        }
        
        // Wait for all running threads to finish
        for (Thread t : threads)
        {
            try
            {
                t.join();
            }
            catch (InterruptedException e)
            {

            }
        }

        System.out.println("\nThreads created by implementing the Runnable interface:");

        for (Thread r : runs)
        {
            r.start();
        }
    }
}