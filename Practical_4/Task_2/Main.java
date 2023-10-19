public class Main {
    public static void main(String args[]) {
        Printer printer = new Printer();

        ArtLover node1 = new ArtLover(printer);
        ArtLover node2 = new ArtLover(printer);
        ArtLover node3 = new ArtLover(printer);
        ArtLover node4 = new ArtLover(printer);
        ArtLover node5 = new ArtLover(printer);

        node1.start();
        node2.start();
        node3.start();
        node4.start();
        node5.start();
    }
}