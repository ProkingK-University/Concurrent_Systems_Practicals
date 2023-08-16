public class Main {
    public static void main(String[] args) {
        Register<Boolean> register = new MRMW<Boolean>(1,true);
        Reader<Boolean> reader = new Reader<>(register);

        reader.start();
    }
}