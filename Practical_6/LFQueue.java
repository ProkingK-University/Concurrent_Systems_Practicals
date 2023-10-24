import java.util.EmptyStackException;
import java.util.concurrent.atomic.AtomicReference;

class Node<T> {
    public T value;
    public AtomicReference<Node<T>> next;

    public Node(T value) {
        this.value = value;
        next = new AtomicReference<Node<T>>(null);
    }
}

public class LFQueue<T> implements Database<T> {
    private AtomicReference<Node<T>> head;
    private AtomicReference<Node<T>> tail;

    public LFQueue() {
        Node<T> sentinel = new Node<>(null);

        head = new AtomicReference<>(sentinel);
        tail = new AtomicReference<>(sentinel);
    }

    public void insert(T value) {
        Node<T> node = new Node<>(value);

        while (true) {
            Node<T> last = tail.get();
            Node<T> next = last.next.get();

            if (last == tail.get()) {
                if (next == null) {
                    if (last.next.compareAndSet(next, node)) {
                        tail.compareAndSet(last, node);

                        return;
                    }
                } else {
                    tail.compareAndSet(last, next);
                }
            }
        }
    }

    public T remove() throws EmptyStackException {
        while (true) {
            Node<T> first = head.get();
            Node<T> last = tail.get();
            Node<T> next = first.next.get();

            if (first == head.get()) {
                if (first == last) {
                    if (next == null) {
                        throw new EmptyStackException();
                    }

                    tail.compareAndSet(last, next);
                } else {
                    T value = next.value;

                    if (head.compareAndSet(first, next)) {
                        return value;
                    }
                }
            }
        }
    }

    public boolean isEmpty() {
        Node<T> first = head.get();
        Node<T> last = tail.get();
        Node<T> next = first.next.get();
        return (first == head.get()) && (first == last) && (next == null);
    }
}