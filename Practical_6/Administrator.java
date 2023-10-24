import java.util.concurrent.ThreadLocalRandom;

class Administrator extends Thread {
    private final LFQueue<Job> queue;

    public Administrator(LFQueue<Job> queue) {
        this.queue = queue;
    }

    @Override
    public void run() {
        while (!queue.isEmpty()) {
            Job job = queue.deq();

            if (job != null) {
                int randomHours = ThreadLocalRandom.current().nextInt(1, 25);
                String status = (job.hours < randomHours) ? "Approved" : "Disapproved";

                System.out.println("(OUT) " + Thread.currentThread().getName() + " Job ID-" + job.id + " Job Hours-" + job.hours + " Status-" + status);
            }
        }
    }
}