public class MyThread extends Thread
{
    Range range;
    Integer[] array;

    public MyThread(Integer[] array, Range range)
    {
        this.array = array;
        this.range = range;
    }

    @Override
    public void run()
    {
        for (int i = range.getStart(); i <= range.getEnd(); i++)
        {
            if (isPrimeNumber(array[i]))
            {
                printNumber(array[i]);
            }
        }
    }

    private void printNumber(Integer primeNumber)
    {
        System.out.println(getName() + " " + range.getRange() + ": " + primeNumber);
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