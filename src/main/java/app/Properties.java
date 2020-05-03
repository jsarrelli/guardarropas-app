package app;

public class Properties {
    public static String getProperty(String key) {
        System.out.println(System.getenv().get(key));
        return System.getenv().get(key);
    }
}
