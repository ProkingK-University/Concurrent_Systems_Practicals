public class Main {
    public static void main(String[] args) {
        Register<Integer> register = new MRMW<Integer>(10,0);

        @SuppressWarnings("unchecked")
        Reader<Integer>[] readers = new Reader[10];
        
        @SuppressWarnings("unchecked")
        Writer<Integer>[] writers = new Writer[10];

        for (int i = 0; i < 10; i++) {
            readers[i] = new Reader<Integer>(register);
            writers[i] = new Writer<Integer>(register, i);
        }

        for (int i = 0; i < writers.length; i++) {
            readers[i].start();
            writers[i].start();
        }
    }
}