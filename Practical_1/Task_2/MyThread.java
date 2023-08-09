public class MyThread extends Thread
{
    private Integer[] array;
    private static Counter counter = new Counter(1);

    public MyThread(Integer[] array)
    {
        this.array = array;
    }

    @Override
    public void run()
    {
        for (int i = 0; i < array.length; i++)
        {
            i = counter.getAndIncrement();

            if (i < array.length && isPrimeNumber(array[i]))
            {
                printNumber(array[i]);
            }
        }
    }

    private void printNumber(Integer primeNumber)
    {
        System.out.println(getName() + ": " + primeNumber);
    }

    private boolean isPrimeNumber(Integer number)
    {
        if (number <= 1)
        {
            return false;
        }
        else
        {
            for (int i = 2; i * i <= number; i++)
            {
                if (number % i == 0)
                {
                    return false;
                }
            }

            return true;
        }
    }
}