public class Main
{
    public static void main(String[] args)
    {
        Integer[] array = new Integer[16];

        for (int i = 0; i < array.length; i++)
        {
            array[i] = i;
        }

        Thread thread1 = new MyThread(array, new Range(0, 3));
        Thread thread2 = new MyThread(array, new Range(4, 7));
        Thread thread3 = new MyThread(array, new Range(8, 11));
        Thread thread4 = new MyThread(array, new Range(12, 15));

        thread1.start();
        thread2.start();
        thread3.start();
        thread4.start();
    }
}