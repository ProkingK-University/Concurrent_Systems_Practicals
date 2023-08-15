package Practical_3;

public interface Register<T> {
    T read();

    void write(T value);
}