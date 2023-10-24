import java.util.EmptyStackException;
import java.util.concurrent.ThreadLocalRandom;

class Administrator extends Thread {
    private final Database<Job> database;

    public Administrator(Database<Job> database) {
        this.database = database;
    }

    @Override
    public void run() {
        while (!database.isEmpty()) {
            Job job = null;

            try {
                job = database.remove();
            } catch (EmptyStackException | InterruptedException e) {
                e.printStackTrace();
            }

            if (job != null) {
                int randomHours = ThreadLocalRandom.current().nextInt(1, 25);
                String status = (job.hours < randomHours) ? "Approved" : "Disapproved";

                System.out.println("(OUT) " + Thread.currentThread().getName() + " Job ID-" + job.id + " Job Hours-" + job.hours + " Status-" + status);
            }
        }
    }
}