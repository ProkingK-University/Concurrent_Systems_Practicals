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

        /*Developer developer1 = new Developer(database);
        Developer developer2 = new Developer(database);
        Developer developer3 = new Developer(database);
        Developer developer4 = new Developer(database);

        Administrator administrator1 = new Administrator(database);
        Administrator administrator2 = new Administrator(database);

        developer1.start();
        developer2.start();
        developer3.start();
        developer4.start();

        administrator1.start();
        administrator2.start();*/
    }
}