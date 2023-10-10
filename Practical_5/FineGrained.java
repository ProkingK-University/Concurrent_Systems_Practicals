import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class FineGrained {
    private Node head;
    private Lock lock = new ReentrantLock();

    public FineGrained() {
        head = new Node(0);
        head.next = new Node(1);
    }
}
