package dev.uninotes.UniNotes.Database;

import dev.uninotes.UniNotes.Note;
import dev.uninotes.UniNotes.Post;
import org.junit.jupiter.api.*;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class TestDatabaseManager {

    @BeforeAll
    static void setupDatabase() {
        DatabaseManager.initialize();
    }

    @AfterAll
    static void tearDown() {
        System.out.println("All tests completed.");
    }

    @Test
    @Order(1)
    void testConnect() {
        assertNotNull(DatabaseManager.connect(), "Connection should not be null");
    }

    @Test
    @Order(2)
    void testAddUser() {
        boolean result = DatabaseManager.addUser("test@example.com", "password123");
        assertTrue(result, "User should be added successfully");
    }

    @Test
    @Order(3)
    void testInvalidUser() {
        boolean isValid = DatabaseManager.validateUser("thisMailDoesNotExists@example.com", "password123");
        assertFalse(isValid, "Credentials should be invalid");
    }

    @Test
    @Order(4)
    void testUpdateUser() {
        boolean updated = DatabaseManager.UPDATE_USER(1, "TestUpdate", "TestUpdate", "update@test.com", "teeeest", "image.jpg");
        assertTrue(updated, "User should be updated successfully");
    }

    @Test
    @Order(5)
    void testUpdateUserWithTheSameUsernameAsAnotherUser() {
        boolean updated = DatabaseManager.UPDATE_USER(2, "TestUpdate", "TestUpdate", "update2@test.com", "teeeest", "image.jpg");
        assertFalse(updated, "Update should fail due to duplicate username");
    }

    @Test
    @Order(6)
    void testUpdateUserWithTheSameEmailAsAnotherUser() {
        boolean updated = DatabaseManager.UPDATE_USER(2, "TestUpdate", "TestUpdate", "update@test.com", "teeeest2", "image.jpg");
        assertFalse(updated, "Update should fail due to duplicate email");
    }

    @Test
    @Order(7)
    void testUpdateUserWithTheSameEmailAndUsernameAsAnotherUser() {
        boolean updated = DatabaseManager.UPDATE_USER(2, "TestUpdate", "TestUpdate", "update@test.com", "teeeest", "image.jpg");
        assertFalse(updated, "Update should fail due to duplicate email and username");
    }

    @Test
    @Order(8)
    void testInsertPost() {
        boolean postInserted = DatabaseManager.INSERT_POST("This is a test post", 1);
        assertTrue(postInserted, "Post should be inserted successfully");
    }

    @Test
    @Order(9)
    void testSelectPosts() {
        ArrayList<Post> posts = DatabaseManager.SELECT_POSTS();
        assertNotNull(posts, "Posts should not be null");
        assertFalse(posts.isEmpty(), "Posts list should not be empty");
    }

    @Test
    @Order(10)
    void testInsertNote() {
        int noteId = DatabaseManager.INSERT_NOTE("Test note", 1, "Data Science", "Lecture");
        assertTrue(noteId > 0, "Note ID should be greater than 0");
    }

    @Test
    @Order(11)
    void testConsistentResultSelectNotes() {
        ArrayList<Note> notes = DatabaseManager.SELECT_NOTES("Computer Science", "Data Science", "john_doe", "Lecture");
        assertNotNull(notes, "Notes should not be null");
    }

    @Test
    @Order(12)
    void testUpdateUserPassword() {
        boolean passwordUpdated = DatabaseManager.UPDATE_USER_PASSWORD("test@example.com", "newpassword123", "password123");
        assertTrue(passwordUpdated, "Password should be updated successfully");
    }

    @Test
    @Order(13)
    void testSelectFaculties() {
        List<String> faculties = DatabaseManager.SELECT_FACULTIES_NAME();
        assertNotNull(faculties, "Faculties should not be null");
    }

    @Test
    @Order(14)
    void testSelectUsernames() {
        List<String> usernames = DatabaseManager.SELECT_USERNAMES();
        assertNotNull(usernames, "Usernames should not be null");
        assertFalse(usernames.isEmpty(), "Usernames list should not be empty");
    }
}
