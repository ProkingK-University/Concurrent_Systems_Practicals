public class Main {
    public static void main(String[] args) {
        Register<Boolean> register = new MRSW<Boolean>(true,1);
        Reader<Boolean> reader = new Reader<>(register);

        reader.start();
    }
}