import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.atomic.AtomicReference;

public class MCSQueue implements Lock {
    private ThreadLocal<Node> node;
    private AtomicReference<Node> tail;

    public MCSQueue(Printer printer) {
        tail = new AtomicReference<Node>(null);
        node = new ThreadLocal<Node>() {
            protected Node initialValue() {
                return new Node(printer);
            }
        };
    }

    @Override
    public void lock() {
        Node curr = node.get();
        curr.requestNumber++;
        Node prev = tail.getAndSet(curr);

        if (prev != null) {
            curr.locked = true;
            prev.next = curr;

            while (curr.locked) {}
        }
    }

    @Override
    public void unlock() {
        Node curr = node.get();
        
        while (curr != null) {
            if (curr.next != null) {
                System.out.print("{" + curr.getName() + ":Request " + curr.requestNumber + "} -> ");
            } else {
                System.out.println("{" + curr.getName() + ":Request " + curr.requestNumber + "}");
            }

            curr = curr.next;
        }

        curr = node.get();

        if (curr.next == null) {
            if (tail.compareAndSet(curr, null)) {
                return;
            }

            while (curr.next == null) {}
        }

        curr.next.locked = false;
        curr.next = null;
    }

    @Override
    public boolean tryLock() {
        return false;
    }

    @Override
    public void lockInterruptibly() {}

    @Override
    public Condition newCondition() {
        return null;
    }

    @Override
    public boolean tryLock(long time, TimeUnit unit) {
        return false;
    }
}