public class Writer<T> extends Thread {
    T value;
    Register<T> register;

    public Writer(Register<T> register, T value) {
        this.value = value;
        this.register = register;
    }

    public void setValue(T value) {
        this.value = value;
    }

    @Override
    public void run() {
        register.write(value);
        System.out.println(this.getName() + " Wrote: " + value);
    }
}