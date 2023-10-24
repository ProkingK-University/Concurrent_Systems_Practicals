import java.util.Random;
import java.util.EmptyStackException;
import java.util.concurrent.atomic.AtomicReference;

public class LFStack<T> implements Database<T>{
    private AtomicReference<Node<T>> top = new AtomicReference<>(null);

    private static final int MIN_DELAY = 100;
    private static final int MAX_DELAY = 1000;
    private Backoff backoff = new Backoff(MIN_DELAY, MAX_DELAY);

    private boolean tryInsert(Node<T> node) {
        Node<T> oldTop = top.get();
        node.next = oldTop;
        return top.compareAndSet(oldTop, node);
    }

    @Override
    public void insert(T value) throws InterruptedException {
        Node<T> node = new Node<>(value);
        while (true) {
            if (tryInsert(node)) {
                return;
            } else {
                backoff.backoff();
            }
        }
    }

    private Node<T> tryRemove() throws EmptyStackException {
        Node<T> oldTop = top.get();

        if (oldTop == null) {
            throw new EmptyStackException();
        }

        Node<T> newTop = oldTop.next;

        if (top.compareAndSet(oldTop, newTop)) {
            return oldTop;
        } else {
            return null;
        }
    }

    @Override
    public T remove() throws EmptyStackException, InterruptedException {
        while (true) {
            Node<T> returnNode = tryRemove();

            if (returnNode != null) {
                return returnNode.value;
            } else {
                backoff.backoff();
            }
        }
    }

    @Override
    public boolean isEmpty() {
        return top.get() == null;
    }
}

class Backoff {
    private int limit;
    private final int minDelay;
    private final int maxDelay;
    private final Random random;

    public Backoff(int min, int max) {
        minDelay = min;
        maxDelay = max;
        limit = minDelay;
        random = new Random();
    }

    public void backoff() throws InterruptedException {
        int delay = random.nextInt(limit);
        limit = Math.min(maxDelay, 2 * limit);
        Thread.sleep(delay);
    }
}

class Node<T> {
    public T value;
    public Node<T> next;

    public Node(T value) {
        this.value = value;
        next = null;
    }
}