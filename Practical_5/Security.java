import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

class Security extends Thread {
    private final int entrance;;
    private final Gallery gallery;

    public Security(Gallery gallery, int entrance) {
        this.gallery = gallery;
        this.entrance = entrance;
    }

    @Override
    public void run() {
        for (int i = 0; i < 10; i++) {
            try {
                int id = i + 10 * entrance;
                
                TimeUnit.MILLISECONDS.sleep(200);
                gallery.enter(entrance, id);

                int timeInGallery = ThreadLocalRandom.current().nextInt(100, 1001);
                System.out.println("Thread-" + entrance + ": ADD (ArtLover-" + id + " ," + timeInGallery + "ms)");
                TimeUnit.MILLISECONDS.sleep(timeInGallery);

                gallery.leave(entrance, id);

                gallery.print();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
