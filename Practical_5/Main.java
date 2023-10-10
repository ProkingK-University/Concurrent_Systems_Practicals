public class Main {
    public static void main(String[] args) {
        Gallery gallery = new Optimistic();

        for (int i = 0; i < 5; i++) {
            new Security(gallery, i).start();
        }
    }
}