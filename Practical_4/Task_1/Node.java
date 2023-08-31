public class Node extends Thread {

	private Printer printer;
	public volatile Node next;
	public volatile boolean locked;

	Node(Printer printer) {
		next = null;
		locked = false;
		this.printer = printer;
	}

	@Override
	public void run() {
		printer.Print();
		printer.Print();
		printer.Print();
		printer.Print();
		printer.Print();
	}
}