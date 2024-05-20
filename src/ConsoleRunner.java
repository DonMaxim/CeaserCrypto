public class ConsoleRunner {
    public static void main(String[] args) {
        Application application = new Application();
        Result result = application.run();
        System.out.println(result);
    }
}
