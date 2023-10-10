import java.util.concurrent.ThreadLocalRandom;

public class Optimistic implements Gallery {
    public ArtLover head;

    public Optimistic() {
        head = new ArtLover(Integer.MIN_VALUE);
        head.next = new ArtLover(Integer.MAX_VALUE);
    }

    public boolean enter(int entrance, int id) {
        while (true) {
            ArtLover prev = head;
            ArtLover curr = prev.next;

            while (curr.id < id) {
                prev = curr;
                curr = curr.next;
            }

            prev.lock();
            try {
                curr.lock();
                try {
                    if (validate(prev, curr)) {
                        if (curr.id == id) {
                            return false;
                        } else {
                            ArtLover node = new ArtLover(id);
                            node.next = curr;
                            prev.next = node;
                            return true;
                        }
                    }
                } finally {
                    curr.unlock();
                }
            } finally {
                prev.unlock();
            }
        }
    }

    public boolean leave(int entrance, int id) {

        while (true) {
            ArtLover prev = head;
            ArtLover curr = prev.next;

            while (curr.id < id) {
                prev = curr;
                curr = curr.next;
            }

            prev.lock();
            curr.lock();

            try {
                if (validate(prev, curr)) {
                    if (curr.id == id) {
                        prev.next = curr.next;
                        return true;
                    } else {
                        return false;
                    }
                }
            } finally {
                prev.unlock();
                curr.unlock();
            }
        }
    }

    private boolean validate(ArtLover prev, ArtLover curr) {
        ArtLover node = head;

        while (node.id <= prev.id) {
            if (node.equals(prev)) {
                return prev.next == curr;
            }
            node = node.next;
        }

        return false;
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