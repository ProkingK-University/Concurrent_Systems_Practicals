import java.util.concurrent.ConcurrentLinkedQueue;

public class Main {

    public static void main(String[] args) {
        ConcurrentLinkedQueue<Job> queue = new ConcurrentLinkedQueue<Job>();

        for (int i = 0; i < 4; i++) {
            Developer developer = new Developer(queue);
            developer.start();
        }

        for (int i = 0; i < 2; i++) {
            Administrator administrator = new Administrator(queue);
            administrator.start();
        }
    }
}