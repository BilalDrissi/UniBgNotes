package dev.uninotes.UniNotes.Tests;
import dev.uninotes.UniNotes.Utils.Utils;
import org.testng.annotations.Test;
import static org.junit.jupiter.api.Assertions.*;

public class TestCryptPassword {

    @Test
    public void testCryptPasswordNotNull() {
        String password = "mypassword123";
        String hashedPassword = Utils.cryptPassword(password);

        //check hash is not null
        assertNotNull(hashedPassword, "The hashed password should not be null.");
    }

    @Test
    public void testCryptPasswordLength() {
        String password = "mypassword123";
        String hashedPassword = Utils.cryptPassword(password);

        // checks if the hash is of 32 chars
        assertEquals(32, hashedPassword.length(), "The hashed password should be 32 characters long.");
    }

    @Test
    public void testCryptPasswordConsistency() {
        String password = "mypassword123";
        String hashedPassword1 = Utils.cryptPassword(password);
        String hashedPassword2 = Utils.cryptPassword(password);

        // checks if the hash is the same for the same word
        assertEquals(hashedPassword1, hashedPassword2, "The hashed password should be consistent for the same input.");
    }

    @Test
    public void testCryptPasswordDifferentInputs() {
        String password1 = "password123";
        String password2 = "password456";
        String hashedPassword1 = Utils.cryptPassword(password1);
        String hashedPassword2 = Utils.cryptPassword(password2);

        // checks if the hash is different for different words
        assertNotEquals(hashedPassword1, hashedPassword2, "Different inputs should produce different hashed passwords.");
    }

    @Test
    public void testCryptPasswordEmptyInput() {
        String password = "";
        String hashedPassword = Utils.cryptPassword(password);

        // checks if an empty string creates a 32 chars hash (in the actual utilisation it should never happen)
        assertNotNull(hashedPassword, "The hashed password should not be null for an empty input.");
        assertEquals(32, hashedPassword.length(), "The hashed password for an empty input should be 32 characters long.");
    }

    @Test
    public void testCryptPasswordNullInput() {
        String hashedPassword = Utils.cryptPassword(null);

        // checks if a null string creates a null hash (in the actual utilisation it should never happen)
        assertNull(hashedPassword, "The hashed password should be null for a null input.");
    }
}
