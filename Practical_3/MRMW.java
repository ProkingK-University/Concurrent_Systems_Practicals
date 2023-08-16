public class MRMW<T> implements Register<T> {
    private int capacity;
    private StampedValue<T>[] valueTable;

    @SuppressWarnings("unchecked")
    public MRMW(int capacity, T init) {
        this.capacity = capacity;
        valueTable = (StampedValue<T>[]) new StampedValue[capacity];

        StampedValue<T> value = new StampedValue<T>(init);

        for (int j = 0; j < valueTable.length; j++) {
            valueTable[j] = value;
        }
    }

    @SuppressWarnings({"rawtypes", "unchecked"})
    public void write(T value) {
        int id = (int) (Thread.currentThread().threadId() % capacity);

        StampedValue<T> max = StampedValue.MIN_VALUE;

        for (int i = 0; i < valueTable.length; i++) {
            max = StampedValue.max(max, valueTable[i]);
        }

        valueTable[id] = new StampedValue(max.stamp + 1, value);
    }

    @SuppressWarnings("unchecked")
    public T read() {
        StampedValue<T> max = StampedValue.MIN_VALUE;

        for (int i = 0; i < valueTable.length; i++) {
            max = StampedValue.max(max, valueTable[i]);
        }
        
        return max.value;
    }
}