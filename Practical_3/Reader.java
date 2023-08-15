package Practical_3;

public class Reader<T> extends Thread {
	Register<T> register;

	public Reader(Register<T> register) {
		this.register = register;
	}

	@Override
	public void run() {
		T value = register.read();
		System.out.println(this.getName() + " Read: " + value);
	}
}