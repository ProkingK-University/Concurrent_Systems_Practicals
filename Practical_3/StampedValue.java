public class StampedValue<T> {
    public T value;
    public long stamp;

    @SuppressWarnings("rawtypes")
    public static StampedValue MIN_VALUE = new StampedValue<>(null);

    public StampedValue(T value) {
        stamp = 0;
        this.value = value;
    }

    public StampedValue(long stamp, T value) {
        this.stamp = stamp;
        this.value = value;
    }

    @SuppressWarnings("rawtypes")
    public static StampedValue max(StampedValue x, StampedValue y) {
        if (x.stamp > y.stamp) {
            return x;
        } else {
            return y;
        }
    }
}