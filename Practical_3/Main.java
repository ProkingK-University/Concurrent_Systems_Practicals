public class Main {
    public static void main(String[] args) {
        Register<Boolean> register = new MRMW<Boolean>(1,true);

        Reader<Boolean> reader = new Reader<>(register);
        Reader<Boolean> reader1 = new Reader<>(register);
        Writer<Boolean> writer = new Writer<Boolean>(register, false);

        reader.start();
        writer.start();
        reader1.start();
    }
}