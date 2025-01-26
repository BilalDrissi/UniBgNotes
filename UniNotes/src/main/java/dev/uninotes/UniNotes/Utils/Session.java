package dev.uninotes.UniNotes.Utils;

public class Session {
    public static Session INSTANCE;
    private static int id = 0;
    private static boolean logged = false;

    private Session() {
        this.logged = false;
    }

    protected static Session getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new Session();
        }
        return INSTANCE;
    }

    private int getId() {
        return id;
    }

    public void setId(int id) {
        Session.id = id;
    }

    public boolean isLogged() {
        return logged;
    }

    public void setLogged(boolean logged) {
        Session.logged = logged;
    }
}
