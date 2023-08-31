import java.util.Random;
import java.security.SecureRandom;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;

public class Printer {
	private int requestNumber = 0;
	private Lock lock = new Timeout(this);
    private final long DELAY = (long) (Math.random() * 800 + 200);
	private final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";

	public void Print() {
		boolean locked = false;

		try {
			locked = lock.tryLock(10, TimeUnit.SECONDS);
			String message = generateRandomString();
			System.out.println(Thread.currentThread().getName() + ": " + requestNumber++ + " " + message);
			Thread.sleep(DELAY);
		} catch (InterruptedException e) {
			e.printStackTrace();
		} finally {
			lock.unlock();
		}
	}

	private String generateRandomString() {
        Random random = new SecureRandom();
        StringBuilder stringBuilder = new StringBuilder(10);

        for (int i = 0; i < 10; i++) {
            int randomIndex = random.nextInt(CHARACTERS.length());
            char randomChar = CHARACTERS.charAt(randomIndex);
            stringBuilder.append(randomChar);
        }

        return stringBuilder.toString();
    }
}