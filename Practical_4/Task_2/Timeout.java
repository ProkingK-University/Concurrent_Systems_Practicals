import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.atomic.AtomicReference;

public class Timeout implements Lock {
    Printer printer;
    ThreadLocal<ArtLover> node;
    AtomicReference<ArtLover> tail;
    static ArtLover AVAILABLE = new ArtLover(new Printer());

    public Timeout(Printer printer) {
        this.printer = printer;
        tail = new AtomicReference<ArtLover>(null);
        node = new ThreadLocal<ArtLover>() {
            protected ArtLover initialValue() {
                return new ArtLover(printer);
            }
        };
    }

    @Override
    public boolean tryLock(long time, TimeUnit unit) throws InterruptedException {
        ArtLover curr = (ArtLover) Thread.currentThread();
        node.set(curr);
        ArtLover prev = tail.getAndSet(curr);

        long startTime = System.currentTimeMillis();
        long patience = TimeUnit.MILLISECONDS.convert(time, unit);
        
        if (prev == null || prev.prev == AVAILABLE) {
            return true;
        }

        while (System.currentTimeMillis() - startTime < patience) {
            ArtLover prevPrev = prev.prev;

            if (prevPrev == AVAILABLE) {
                return true;
            } else if (prevPrev != null) {
                prev = prevPrev;
            }
        }
        
        if (!tail.compareAndSet(curr, prev)) {
            curr.prev = prev;
        }

        return false;
    }

    public void unlock() {
        ArtLover curr = node.get();
        
        while (curr != null) {
            
            if (curr.prev != null) {
                System.out.print("{" + Thread.currentThread().getName() + ":Request " + curr.requestNumber + "} -> ");
            } else {
                System.out.println("{" + Thread.currentThread().getName() + ":Request " + curr.requestNumber + "}");
            }

            curr = curr.prev;
        }
        
        curr = node.get();

        if (!tail.compareAndSet(curr, null)) {
            curr.prev = AVAILABLE;
        }
    }
    
    @Override
    public void lock() {}

    @Override
    public Condition newCondition() {
        return null;
    }
    
    @Override
    public void lockInterruptibly() {}
    
    @Override
    public boolean tryLock() {
        return false;
    }
}