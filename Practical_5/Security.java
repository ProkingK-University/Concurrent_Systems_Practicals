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
                TimeUnit.MILLISECONDS.sleep(200);
                gallery.enter(entrance, i);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
