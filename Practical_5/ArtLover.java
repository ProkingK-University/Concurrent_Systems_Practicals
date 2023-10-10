import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ArtLover {
    public int id;
    public ArtLover next;
    private Lock lock = new ReentrantLock();

    public ArtLover(int id) {
        this.id = id;
        this.next = null;
    }

    public void lock() {
        lock.lock();
    }

    public void unlock() {
        lock.unlock();
    }
}
