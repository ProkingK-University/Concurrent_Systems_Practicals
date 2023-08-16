public class StampedValue<T> {
    public T value;
    public long stamp;
    public static final StampedValue<?> MIN_VALUE = new StampedValue<>(null);

    public StampedValue(T value) {
        this.stamp = 0;
        this.value = value;
    }

    public StampedValue(long stamp, T value) {
        this.stamp = stamp;
        this.value = value;
    }

    public static StampedValue<?> max(StampedValue<?> x, StampedValue<?> y) {
        return x.stamp > y.stamp ? x : y;
    }
}