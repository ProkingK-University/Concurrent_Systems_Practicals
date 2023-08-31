public class Node extends Thread {
	private Printer printer;
	public volatile Node prev;

	Node(Printer printer) {
		prev = null;
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