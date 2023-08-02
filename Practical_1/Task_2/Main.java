public class Main
{
    public static void main(String[] args)
    {
        Integer array[] = new Integer[16];

        for (int i = 0; i < array.length; i++)
        {
           array[i] = i;
        }

        Thread thread1 = new MyThread(array);
        Thread thread2 = new MyThread(array);

        thread1.start();
        thread2.start();
    }
}