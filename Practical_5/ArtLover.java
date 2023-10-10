import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ArtLover {
    public int id;
    public boolean marked;
    public ArtLover next;
    private Lock lock = new ReentrantLock();

    public ArtLover(int id) {
        this.id = id;
        marked = false;
        this.next = null;
    }

    public void lock() {
        lock.lock();
    }

    public void unlock() {
        lock.unlock();
    }
}
