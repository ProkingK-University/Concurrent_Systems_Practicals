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

                return true;
            } finally {
                curr.unlock();
            }
        } finally {
            prev.unlock();
        }
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
}
