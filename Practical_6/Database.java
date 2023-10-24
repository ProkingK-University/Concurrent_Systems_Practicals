import java.util.EmptyStackException;

public interface Database<T> {
    boolean isEmpty();

    void insert(T value) throws InterruptedException;

    T remove() throws EmptyStackException, InterruptedException;
}