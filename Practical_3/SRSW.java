package Practical_3;

public class SRSW<T> implements Register<T> {
    StampedValue<T> r_value;
    ThreadLocal<Long> lastStamp;
    ThreadLocal<StampedValue<T>> lastRead;

    public SRSW(T value) {
        r_value = new StampedValue<T>(value);

        lastStamp = new ThreadLocal<Long>() {
            protected Long initialValue() {
                return 0L;
            };
        };

        lastRead = new ThreadLocal<StampedValue<T>>() {
            protected StampedValue<T> initialValue() {
                return r_value;
            };
        };
    }

    @SuppressWarnings("unchecked")
    public T read() {
        StampedValue<T> value = r_value;
        StampedValue<T> last = lastRead.get();
        StampedValue<T> result = StampedValue.max(value, last);

        lastRead.set(result);

        return result.value;
    }

    @SuppressWarnings({"rawtypes", "unchecked"})
    public void write(T value) {
        long stamp = lastStamp.get() + 1;

        r_value = new StampedValue(stamp, value);
        lastStamp.set(stamp);
    }
}