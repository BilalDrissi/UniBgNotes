package dev.uninotes.UniNotes.Tests;
import dev.uninotes.UniNotes.Utils.Utils;
import org.testng.annotations.Test;
import static org.junit.jupiter.api.Assertions.*;

public class TestValidEmail {

    // test valid email with standard format
    @Test
    public void testValidEmailStandardFormat() {
        String email = "example@example.com";
        assertTrue(Utils.isValidEmail(email), "Valid email should return true.");
    }

    // test valid email with subdomain
    @Test
    public void testValidEmailWithSubdomain() {
        String email = "example@mail.example.com";
        assertTrue(Utils.isValidEmail(email), "Valid email with subdomain should return true.");
    }

    // test valid email with special characters
    @Test
    public void testValidEmailWithSpecialCharacters() {
        String email = "user.name+tag+sorting@example.com";
        assertTrue(Utils.isValidEmail(email), "Valid email with special characters should return true.");
    }

    // test invalid email missing @ symbol
    @Test
    public void testInvalidEmailMissingAtSymbol() {
        String email = "example.example.com";
        assertFalse(Utils.isValidEmail(email), "Invalid email missing @ symbol should return false.");
    }

    // test invalid email with invalid domain
    @Test
    public void testInvalidEmailWithInvalidDomain() {
        String email = "example@.com";
        assertFalse(Utils.isValidEmail(email), "Invalid email with invalid domain should return false.");
    }

    // test invalid email with spaces
    @Test
    public void testInvalidEmailWithSpaces() {
        String email = "example @example.com";
        assertFalse(Utils.isValidEmail(email), "Invalid email with spaces should return false.");
    }

    // test empty email string
    @Test
    public void testEmptyEmailString() {
        String email = "";
        assertFalse(Utils.isValidEmail(email), "Empty email string should return false.");
    }

    // test null email
    @Test
    public void testNullEmail() {
        String email = null;
        assertThrows(NullPointerException.class, () -> Utils.isValidEmail(email), "Null email should throw NullPointerException.");
    }
}
