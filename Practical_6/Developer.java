import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.ConcurrentLinkedQueue;

class Developer extends Thread {
    private final ConcurrentLinkedQueue<Job> queue;

    public Developer(ConcurrentLinkedQueue<Job> queue) {
        this.queue = queue;
    }

    @Override
    public void run() {
        for (int i = 0; i < 3; i++) {
            int hours = ThreadLocalRandom.current().nextInt(1, 25);
            Job job = new Job(i, hours);

            queue.add(job);

            System.out.println("(IN) " + Thread.currentThread().getName() + " Job ID-" + job.id + " Job Hours-" + job.hours);
        }
    }
}