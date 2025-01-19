package dev.uninotes.UniNotes.Utils;

import org.junit.jupiter.api.*;

import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class TestUtils {

    @Test
    @Order(1)
    void testIsValidEmail() {
        assertTrue(Utils.isValidEmail("test@example.com"), "Valid email should return true");
        assertFalse(Utils.isValidEmail("thisEmailIsInvalid"), "Invalid email should return false");
    }

    @Test
    @Order(2)
    void testCryptPassword() {
        String password = "TestPassword123";
        String encrypted = Utils.cryptPassword(password);
        assertNotNull(encrypted, "Encrypted password should not be null");
        assertEquals(32, encrypted.length(), "Encrypted password should be 32 characters long");
    }

    @Test
    @Order(3)
    void testCryptGivesAlwaysTheSame() {
        String password = "TestPassword123";
        String encrypted = Utils.cryptPassword(password);
        String password2 = "TestPassword123";
        String encrypted2 = Utils.cryptPassword(password2);
        assertEquals(encrypted, encrypted2, "Encrypted password should be the same");
    }

    @Test
    @Order(4)
    void testFind() {
        assertEquals(2, Utils.find("hello", 'l'), "Position of 'l' should be 2");
        assertEquals(-1, Utils.find("hello", 'z'), "Character not in string should return -1");
    }

    @Test
    @Order(5)
    void testDateTimeFormatter() {
        String dateTime = "2025-01-01 10:15:30";
        LocalDateTime formattedDate = Utils.dateTimeFormatter(dateTime);
        assertNotNull(formattedDate, "Formatted date should not be null");
        assertEquals(2025, formattedDate.getYear(), "Year should be 2025");
        assertEquals(1, formattedDate.getMonthValue(), "Month should be January");
        assertEquals(1, formattedDate.getDayOfMonth(), "Day should be 1");
    }

    @Test
    @Order(6)
    void testGetDefaultProfileImagePath() {
        String path = Utils.getDefaultProfileImagePath();
        assertEquals("images/default/user.jpg", path, "Default profile image path should match");
    }

    @Test
    @Order(7)
    void testGetDirectoryOfDownloads() {
        String directory = Utils.getDirectoryOfDowloads();
        assertEquals("downloads/", directory, "Directory of downloads should match");
    }

    @Test
    @Order(8)
    void testGetDownloadsFolder() {
        Path downloadsFolder = Utils.getDownloadsFolder();
        assertNotNull(downloadsFolder, "Downloads folder path should not be null");
        assertTrue(downloadsFolder.toString().contains("Downloads"), "Path should contain 'Downloads'");
    }

    @Test
    @Order(9)
    void testValidatePassword() {
        assertTrue(Utils.validatePassword("Test@123"), "Valid password should return true");
        assertFalse(Utils.validatePassword("test1234"), "Password without uppercase, special characters should return false");
        assertFalse(Utils.validatePassword("test123!"), "Password without uppercase should return false");
        assertFalse(Utils.validatePassword("Test123"), "Password without special character should return false");
        assertFalse(Utils.validatePassword("short1!"), "Password which is not 8 characters should return false");
        assertFalse(Utils.validatePassword(""), "Empty password should return false");
        assertTrue(Utils.validatePassword("TestVeryVeryLongPasswordWithNumbersAndSpecial@123"), "Valid password should return true");
    }

    @Test
    @Order(10)
    void testLoadNoteTypes() {
        List<String> noteTypes = Utils.loadNoteTypes();
        assertNotNull(noteTypes, "Note types should not be null");
        assertFalse(noteTypes.isEmpty(), "Note types should not be empty");
    }

    @AfterAll
    static void tearDown() {
        System.out.println("All tests completed.");
    }
}
