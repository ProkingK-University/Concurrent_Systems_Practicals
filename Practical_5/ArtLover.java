import java.util.concurrent.locks.Lock;
import java.util.concurrent.atomic.AtomicMarkableReference;

public class ArtLover {
    public int id;
    public ArtLover next;
    public boolean marked;
    public AtomicMarkableReference<ArtLover> succ;
    private Lock lock = new BakeryLock(5);

    public ArtLover(int id) {
        this.id = id;
        marked = false;
        this.next = null;
        this.succ = new AtomicMarkableReference<>(null, false);
    }

    public void lock() {
        lock.lock();
    }

    public void unlock() {
        lock.unlock();
    }
}
