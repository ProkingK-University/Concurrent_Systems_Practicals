import java.util.concurrent.TimeUnit;
import java.util.concurrent.ThreadLocalRandom;

public class FineGrained implements Gallery {
    public ArtLover head;

    public FineGrained() {
        head = new ArtLover(Integer.MIN_VALUE);
        head.next = new ArtLover(Integer.MAX_VALUE);
    }

    public boolean enter(int entrance, int id) {
        head.lock();

        ArtLover prev = head;

        try {
            ArtLover curr = prev.next;

            curr.lock();

            try {
                while (curr.id < id) {
                    prev.unlock();
                    prev = curr;
                    curr = curr.next;
                    curr.lock();
                }

                if (curr.id == id) {
                    return false;
                }

                ArtLover newNode = new ArtLover(id);

                newNode.next = curr;
                prev.next = newNode;

                TimeUnit.MILLISECONDS.sleep(0);

                return true;
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                curr.unlock();
            }
        } finally {
            prev.unlock();
        }

        return false;
    }

    public boolean leave(int entrance, int id) {
        ArtLover prev = head;

        prev.lock();

        try {
            ArtLover curr = prev.next;
            curr.lock();

            try {
                while (curr.id < id) {
                    prev.unlock();
                    prev = curr;
                    curr = curr.next;
                    curr.lock();
                }

                if (curr.id == id) {
                    prev.next = curr.next;

                    return true;
                }

                return false;
            } finally {
                curr.unlock();
            }
        } finally {
            prev.unlock();
        }
    }

    public void print() {
        ArtLover curr = head.next;

        while (curr.next != null) {
            int timeLeft = ThreadLocalRandom.current().nextInt(100, 400);
            System.out.print("ArtLover-" + curr.id + " (" + timeLeft + "ms), ");
            curr = curr.next;
        }

        System.out.println();
    }
}
