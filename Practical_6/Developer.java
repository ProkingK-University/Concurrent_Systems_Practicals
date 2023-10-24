import java.util.concurrent.ThreadLocalRandom;

class Developer extends Thread {
    private final Database<Job> queue;

    public Developer(Database<Job> queue) {
        this.queue = queue;
    }

    @Override
    public void run() {
        for (int i = 0; i < 3; i++) {
            int hours = ThreadLocalRandom.current().nextInt(1, 25);
            Job job = new Job(i, hours);

            try {
                queue.insert(job);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            System.out.println("(IN) " + Thread.currentThread().getName() + " Job ID-" + job.id + " Job Hours-" + job.hours);
        }
    }
}