package sample;

public class Username {
    private static Username instance;
    private static String name;

    private Username() {

    }

    public static void eraseName() {
        name = null;
    }

    public static void setName(String username) {
        if (name == null)
            name = username;
    }

    public static String getName() {
        if (name == null)
            throw new IllegalArgumentException();
        else
            return name;
    }

    public static Username getInstance() {
        if (instance == null) {
            instance = new Username();
        }
        return instance;
    }
}
