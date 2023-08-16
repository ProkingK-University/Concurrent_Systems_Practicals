public class SRSW<T> implements Register<T> {
    private StampedValue<T> currentValue;

    private ThreadLocal<Long> lastStamp;
    private ThreadLocal<StampedValue<T>> lastReadValue;

    public SRSW(T initialValue) {
        currentValue = new StampedValue<>(initialValue);

        lastStamp = ThreadLocal.withInitial(() -> 0L);
        lastReadValue = ThreadLocal.withInitial(() -> currentValue);
    }

    @SuppressWarnings("unchecked")
    public T read() {
        StampedValue<T> value = currentValue;
        StampedValue<T> readValue = lastReadValue.get();
        StampedValue<T> result = (StampedValue<T>) StampedValue.max(value, readValue);

        lastReadValue.set(result);

        return result.value;
    }

    public void write(T newValue) {
        long stamp = lastStamp.get() + 1;

        currentValue = new StampedValue<>(stamp, newValue);
        lastStamp.set(stamp);
    }
}