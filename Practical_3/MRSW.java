public class MRSW<T> implements Register<T> {
    int numOfReaders;
    ThreadLocal<Long> lastStamp;
    private StampedValue<T>[][] valueTable;

    @SuppressWarnings("unchecked")
    public MRSW(T init, int readers) {
        numOfReaders = readers;

        lastStamp = new ThreadLocal<Long>() {
            protected Long initialValue() {
                return 0L;
            };
        };

        valueTable = (StampedValue<T>[][]) new StampedValue[readers][readers];

        StampedValue<T> value = new StampedValue<T>(init);

        for (int i = 0; i < readers; i++) {
            for (int j = 0; j < readers; j++) {
                valueTable[i][j] = value;
            }
        }
    }

    @SuppressWarnings("unchecked")
    public T read() {
        int id = (int) (Thread.currentThread().threadId() % numOfReaders);

        StampedValue<T> value = valueTable[id][id];

        for (int i = 0; i < valueTable.length; i++) {
            value = StampedValue.max(value, valueTable[i][id]);
        }

        for (int i = 0; i < valueTable.length; i++) {
            if (i == id) {
                continue;
            }

            valueTable[id][i] = value;
        }

        return (T) value;
    }

    public void write(T v) {
        long stamp = lastStamp.get() + 1;

        lastStamp.set(stamp);

        StampedValue<T> value = new StampedValue<T>(stamp, v);

        for (int i = 0; i < valueTable.length; i++) {
            valueTable[i][i] = value;
        }
    }
}