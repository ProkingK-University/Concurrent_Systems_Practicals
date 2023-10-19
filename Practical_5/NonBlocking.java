import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.atomic.AtomicMarkableReference;

public class NonBlocking implements Gallery {
    public ArtLover head;

    public NonBlocking() {
        head = new ArtLover(Integer.MIN_VALUE);
        head.next = new ArtLover(Integer.MAX_VALUE);
    }

    public boolean enter(int entrance, int id) {
        while (true) {
            Window window = find(head, id);
            ArtLover prev = window.prev;
            ArtLover curr = window.curr;

            if (curr.id == id) {
                return false;
            } else {
                ArtLover node = new ArtLover(id);
                node.succ = new AtomicMarkableReference<>(curr, false);

                if (prev.succ.compareAndSet(curr, node, false, false)) {
                    return true;
                }
            }
        }
    }

    public boolean leave(int entrance, int id) {
        boolean snip;

        while (true) {
            Window window = find(head, id);
            ArtLover prev = window.prev;
            ArtLover curr = window.curr;

            if (curr.id != id) {
                return false;
            } else {
                ArtLover succ = curr.succ.getReference();
                snip = curr.succ.compareAndSet(succ, succ, false, true);

                if (!snip) {
                    continue;
                }

                prev.succ.compareAndSet(curr, succ, false, false);
                return true;
            }
        }
    }

    public Window find(ArtLover head, int id) {
        ArtLover prev = null, curr = null, succ = null;
        boolean[] marked = { false };
        boolean snip;

        retry: while (true) {
            prev = head;
            curr = prev.succ.getReference();

            while (true) {
                succ = curr.succ.get(marked);

                while (marked[0]) {
                    snip = prev.succ.compareAndSet(curr, succ, false, false);

                    if (!snip) {
                        continue retry;
                    }

                    curr = succ;
                    succ = curr.succ.get(marked);
                }

                if (curr.id >= id) {
                    return new Window(prev, curr);
                }

                prev = curr;
                curr = succ;
            }
        }
    }

    public void print() {
        ArtLover curr = head.succ.getReference();

        while (curr.succ.getReference() != null) {
            int timeLeft = ThreadLocalRandom.current().nextInt(100, 400);
            System.out.print("ArtLover-" + curr.id + " (" + timeLeft + "ms), ");
            curr = curr.succ.getReference();
        }

        System.out.println();
    }
}
