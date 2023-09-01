public class Node extends Thread {
	private Printer printer;
	public volatile Node prev;
	public volatile int requestNumber;

	Node(Printer printer) {
		prev = null;
		requestNumber = 0;
		this.printer = printer;
	}

	@Override
	public void run() {
		for (int i = 0; i < 5; i++) {
			printer.Print(requestNumber++);
		}
	}
}