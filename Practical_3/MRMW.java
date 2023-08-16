public class MRMW<T> implements Register<T> {
    private int capacity;
    private StampedValue<MRSW<T>>[] valueTable;

    @SuppressWarnings("unchecked")
    public MRMW(int capacity, T initialValue) {
        this.capacity = capacity;
        valueTable = new StampedValue[capacity];

        StampedValue<T> initialValueStamped = new StampedValue<>(initialValue);

        for (int j = 0; j < valueTable.length; j++) {
            valueTable[j] = (StampedValue<MRSW<T>>) initialValueStamped;
        }
    }

    @SuppressWarnings("unchecked")
    public void write(T newValue) {
        int id = (int) (Thread.currentThread().threadId() % capacity);

        StampedValue<T> max = (StampedValue<T>) StampedValue.MIN_VALUE;

        for (int i = 0; i < valueTable.length; i++) {
            max = (StampedValue<T>) StampedValue.max(max, valueTable[i]);
        }

        valueTable[id] = (StampedValue<MRSW<T>>) new StampedValue<>(max.stamp + 1, newValue);
    }

    @SuppressWarnings("unchecked")
    public T read() {
        StampedValue<T> max = (StampedValue<T>) StampedValue.MIN_VALUE;

        for (int i = 0; i < valueTable.length; i++) {
            max = (StampedValue<T>) StampedValue.max(max, valueTable[i]);
        }

        return max.value;
    }
}