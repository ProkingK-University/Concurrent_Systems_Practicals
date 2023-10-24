public class Main {
    public static void main(String[] args) {
        Database<Job> database = new LFStack<Job>();

        for (int i = 0; i < 4; i++) {
            Developer developer = new Developer(database);
            developer.start();
        }

        for (int i = 0; i < 2; i++) {
            Administrator administrator = new Administrator(database);
            administrator.start();
        }
    }
}