package dev.uninotes.UniNotes.Tests;

import com.vaadin.flow.component.UI;
import dev.uninotes.UniNotes.Database.DatabaseManager;
import dev.uninotes.UniNotes.User.User;
import dev.uninotes.UniNotes.Utils.Session;
import dev.uninotes.UniNotes.Utils.Utils;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class TestLoginAndRedirect {

    // test loggedIn method
    @Test
    public void testLoggedIn() {
        Session session = Session.getInstance();
        session.setLogged(false); // ensure initial state is logged out

        Utils.loggedIn();

        assertTrue(session.isLogged(), "loggedIn should set the user as logged in.");
    }

    // test loggedOut method
    @Test
    public void testLoggedOut() {
        Session session = Session.getInstance();
        session.setLogged(true); // ensure initial state is logged in

        Utils.loggedOut();

        assertFalse(session.isLogged(), "loggedOut should set the user as logged out.");
    }

    // test isLoggedIn method when user is logged in
    @Test
    public void testIsLoggedInTrue() {
        Session session = Session.getInstance();
        session.setLogged(true); // set the user as logged in

        assertTrue(Utils.isLoggedIn(), "isLoggedIn should return true when user is logged in.");
    }

    // test isLoggedIn method when user is logged out
    @Test
    public void testIsLoggedInFalse() {
        Session session = Session.getInstance();
        session.setLogged(false); // set the user as logged out

        assertFalse(Utils.isLoggedIn(), "isLoggedIn should return false when user is logged out.");
    }


    // test userLoggedIn with valid email or username
    @Test
    public void testUserLoggedInValid() {
        DatabaseManager.addUser("test@example.com", "Test");

        boolean isLoggedIn = Utils.userLoggedIn("test@example.com");

        assertTrue(isLoggedIn, "userLoggedIn should return true for valid user.");

        User user = User.getInstance();
        assertEquals("test@example.com", user.getEmail(), "The email should be set correctly.");
    }

    // test userLoggedIn with invalid email or username
    @Test
    public void testUserLoggedInInvalid() {

        boolean isLoggedIn = Utils.userLoggedIn("invaliduser");

        assertFalse(isLoggedIn, "userLoggedIn should return false for invalid user.");
    }
}
