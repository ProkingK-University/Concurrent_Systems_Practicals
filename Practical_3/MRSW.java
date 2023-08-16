public class MRSW<T> implements Register<T> {
    private int numReaders;
    private ThreadLocal<Long> lastStamp;
    private StampedValue<SRSW<T>>[][] valueTable;

    @SuppressWarnings("unchecked")
    public MRSW(T initialValue, int numReaders) {
        this.numReaders = numReaders;

        lastStamp = ThreadLocal.withInitial(() -> 0L);

        valueTable = new StampedValue[numReaders][numReaders];

        StampedValue<T> initialValueStamped = new StampedValue<>(initialValue);

        for (int i = 0; i < numReaders; i++) {
            for (int j = 0; j < numReaders; j++) {
                valueTable[i][j] = (StampedValue<SRSW<T>>) initialValueStamped;
            }
        }
    }

    @SuppressWarnings("unchecked")
    public T read() {
        int id = (int) (Thread.currentThread().threadId() % numReaders);

        StampedValue<SRSW<T>> value = valueTable[id][id];

        for (int i = 0; i < valueTable.length; i++) {
            value = (StampedValue<SRSW<T>>) StampedValue.max(value, valueTable[i][id]);
        }

        for (int i = 0; i < valueTable.length; i++) {
            if (i == id) {
                continue;
            }

            valueTable[id][i] = value;
        }

        return value.value.read();
    }

    @SuppressWarnings("unchecked")
    public void write(T newValue) {
        long stamp = lastStamp.get() + 1;

        lastStamp.set(stamp);

        StampedValue<T> stampedValue = new StampedValue<>(stamp, newValue);

        for (int i = 0; i < valueTable.length; i++) {
            valueTable[i][i] = (StampedValue<SRSW<T>>) stampedValue;
        }
    }
}