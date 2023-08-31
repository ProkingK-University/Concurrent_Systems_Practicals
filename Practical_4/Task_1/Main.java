public class Main {
    public static void main(String args[]) {
        Printer printer = new Printer();

        Node node1 = new Node(printer);
        Node node2 = new Node(printer);
        Node node3 = new Node(printer);
        Node node4 = new Node(printer);
        Node node5 = new Node(printer);

        node1.start();
        node2.start();
        node3.start();
        node4.start();
        node5.start();
    }
}