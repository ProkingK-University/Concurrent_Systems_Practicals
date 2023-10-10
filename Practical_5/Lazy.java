import java.util.concurrent.ThreadLocalRandom;

public class Lazy implements Gallery {
    public ArtLover head;

    public Lazy() {
        head = new ArtLover(Integer.MIN_VALUE);
        head.next = new ArtLover(Integer.MAX_VALUE);
    }

    public boolean enter(int entrance, int id) {
        while (true) {
            ArtLover prev = head;
            ArtLover curr = head.next;

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
            ArtLover curr = head.next;

            while (curr.id < id) {
                prev = curr;
                curr = curr.next;
            }

            prev.lock();

            try {
                curr.lock();

                try {
                    if (validate(prev, curr)) {
                        if (curr.id != id) {
                            return false;
                        } else {
                            curr.marked = true;
                            prev.next = curr.next;
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

    private boolean validate(ArtLover prev, ArtLover curr) {
        return !prev.marked && !curr.marked && prev.next == curr;
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