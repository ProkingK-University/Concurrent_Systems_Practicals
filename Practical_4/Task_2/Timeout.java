import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.atomic.AtomicReference;

public class Timeout implements Lock {
    Printer printer;
    ThreadLocal<Node> node;
    AtomicReference<Node> tail;
    static Node AVAILABLE = new Node(new Printer());

    public Timeout(Printer printer) {
        this.printer = printer;
        tail = new AtomicReference<Node>(null);
        node = new ThreadLocal<Node>() {
            protected Node initialValue() {
                return new Node(printer);
            }
        };
    }

    @Override
    public boolean tryLock(long time, TimeUnit unit) throws InterruptedException {
        Node curr = new Node(printer);
        //curr.requestNumber++;
        node.set(curr);
        //node.get().requestNumber++;
        Node prev = tail.getAndSet(curr);
        //prev.requestNumber++;

        long startTime = System.currentTimeMillis();
        long patience = TimeUnit.MILLISECONDS.convert(time, unit);
        
        if (prev == null || prev.prev == AVAILABLE) {
            return true;
        }

        while (System.currentTimeMillis() - startTime < patience) {
            Node prevPrev = prev.prev;

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
        Node curr = node.get();
        
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