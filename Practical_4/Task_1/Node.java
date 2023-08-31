public class Node extends Thread {

	public Node next;
	public boolean locked;
	private Printer printer;

	Node(Printer printer) {
		next = null;
		locked = false;
		this.printer = printer;
	}

	@Override
	public void run() {

	}
}