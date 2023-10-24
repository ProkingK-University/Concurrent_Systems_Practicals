import java.util.concurrent.ThreadLocalRandom;

class Developer extends Thread {
    private final LFQueue<Job> queue;

    public Developer(LFQueue<Job> queue) {
        this.queue = queue;
    }

    @Override
    public void run() {
        for (int i = 0; i < 3; i++) {
            int hours = ThreadLocalRandom.current().nextInt(1, 25);
            Job job = new Job(i, hours);

            queue.enq(job);

            System.out.println("(IN) " + Thread.currentThread().getName() + " Job ID-" + job.id + " Job Hours-" + job.hours);
        }
    }
}