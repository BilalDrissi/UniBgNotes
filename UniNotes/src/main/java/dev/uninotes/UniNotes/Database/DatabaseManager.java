package dev.uninotes.UniNotes.Database;

import dev.uninotes.UniNotes.Comment;
import dev.uninotes.UniNotes.Note;
import dev.uninotes.UniNotes.Post;
import dev.uninotes.UniNotes.User.User;
import dev.uninotes.UniNotes.Utils.Utils;

import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class DatabaseManager {

    private static final String DB_URL = "jdbc:sqlite:./localdb/unibgnotes.db";

    /**
     * Establish a connection to the SQLite database.
     * @return a Connection object
     */
    public static Connection connect() {


        Connection connection = null;
        try {
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection(DB_URL);
            System.out.println("Connection to SQLite has been established.");
        } catch (SQLException | ClassNotFoundException e) {
            System.out.println("Error connecting to the database: " + e.getMessage());
        }
        return connection;
    }

    /**
     * Initialize the database by creating the necessary tables.
     */
    public static void initialize() {
        String createUsersTable = """
            CREATE TABLE IF NOT EXISTS users (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                email TEXT NOT NULL UNIQUE,
                username TEXT NOT NULL UNIQUE,
                password TEXT NOT NULL
            );
        """;

        try (Connection connection = connect();
             Statement statement = connection.createStatement()) {
            statement.execute(createUsersTable);
            System.out.println("Database initialized and users table created.");
        } catch (SQLException e) {
            System.out.println("Error initializing database: " + e.getMessage());
        }
    }

    /**
     * Add a new user to the database.
     * @param email the email of the user
     * @param password the password of the user
     * @return true if the user was added successfully, false otherwise
     */
    public static boolean addUser(String email, String password) {
        String insertUserSQL = "INSERT INTO users (email, password) VALUES (?, ?)";
        try (Connection connection = connect();
             PreparedStatement preparedStatement = connection.prepareStatement(insertUserSQL)) {
            preparedStatement.setString(1, email);
            preparedStatement.setString(2, password);
            preparedStatement.executeUpdate();
            System.out.println("User added successfully: " + email);
            return true;
        } catch (SQLException e) {
            System.out.println("Error adding user: " + e.getMessage());
            return false;
        }
    }

    /**
     * Validate user credentials.
     * @param email the email of the user
     * @param password the password of the user
     * @return true if the credentials are valid, false otherwise
     */
    public static boolean validateUser(String emailOrUsername, String password) {
        String query = "SELECT * FROM users WHERE (email = ? OR username = ?) AND password = ?";
        try (Connection connection = connect();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, emailOrUsername);
            preparedStatement.setString(2, emailOrUsername);
            preparedStatement.setString(3, Utils.cryptPassword(password));
            ResultSet resultSet = preparedStatement.executeQuery();

            // if the user exists
            if(resultSet.next()){
                return true;
            }
            return false;
        } catch (SQLException e) {
            System.out.println("Error validating user: " + e.getMessage());
            return false;
        }
    }


    public static ResultSet SELECT_USER(int id) {
        String query = "SELECT * FROM users WHERE id = ?";
        try (Connection connection = connect();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();

            //if the user exists
            if(resultSet.next()){
                return resultSet;
            }

        } catch (SQLException e) {
            System.out.println("Error retrieving users: " + e.getMessage());
        }
        return null;
    }

    public static String SELECT_USERNAME_OF(int id) {
        String query = "SELECT username FROM users WHERE id = ?";
        try (Connection connection = connect();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();

            //if the user exists
            if(resultSet.next()){
                return resultSet.getString("username");
            }

        } catch (SQLException e) {
            System.out.println("Error retrieving users: " + e.getMessage());
        }
        return null;
    }

    public static List<String> SELECT_USERNAME_IMAGE_OF(int id) {
        String query = "SELECT username, image FROM users WHERE id = ?";
        try (Connection connection = connect();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();

            //if the user exists
            if(resultSet.next()){
                return Arrays.asList(resultSet.getString("username"), resultSet.getString("image"));
            }

        } catch (SQLException e) {
            System.out.println("Error retrieving users: " + e.getMessage());
        }
        return null;
    }




    //used to get the username of the user which posted a post
    public static Post.UserForPost SELECT_USER_FOR_POST(int id) {
        String query = "SELECT id, username, image FROM users WHERE id = ?";
        try (Connection connection = connect();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();

            //if the user exists
            if(resultSet.next()){
                return new Post.UserForPost(
                        resultSet.getInt("id"),
                        resultSet.getString("username"),
                        resultSet.getString("image")
                );
            }

        } catch (SQLException e) {
            System.out.println("Error retrieving users: " + e.getMessage());
        }
        return null;
    }

    public static Map<String, String> SELECT_USER(String emailOrUsername) {
        String query = "SELECT * FROM users WHERE (email = ? OR username = ?)";
        try (Connection connection = connect();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, emailOrUsername);
            preparedStatement.setString(2, emailOrUsername);

            ResultSet resultSet = preparedStatement.executeQuery();

            // If the user exists
            if (resultSet.next()) {
                // Create a map to store the user data
                Map<String, String> userData = new HashMap<>();
                userData.put("id", resultSet.getString("id"));
                userData.put("email", resultSet.getString("email"));
                userData.put("username", resultSet.getString("username"));
                userData.put("name", resultSet.getString("name"));
                userData.put("surname", resultSet.getString("surname"));
                userData.put("image", resultSet.getString("image"));
                userData.put("role", resultSet.getString("role"));
                return userData;
            }

        } catch (SQLException e) {
            System.out.println("Error retrieving users: " + e.getMessage());
        }
        return null;
    }

    /**
     * Updates user.
     * @param id the id to look up the user
     * @param email the new email of the user
     * @param surname the new surname
     * @param name the new name
     * @param username the new username
     * @return true if the user has been updated
     */
    public static boolean UPDATE_USER(int id, String name, String surname, String email, String username, String image) {

        String query = "UPDATE users SET name = ? , surname = ? , email = ? , username = ? , image = ? WHERE id = ?";

        try (Connection connection = connect();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(6, id);

            preparedStatement.setString(1, (name != null) ? name : "");
            preparedStatement.setString(2, (surname != null) ? surname : "");
            preparedStatement.setString(3, (email != null && Utils.isValidEmail(email)) ? email : "");
            preparedStatement.setString(4, (username != null) ? username : "");
            preparedStatement.setString(5, (image != null) ? image : "");

            preparedStatement.executeUpdate();
            System.out.println("User updated successfully");

            User.getInstance().update(id,username,email, name, surname, image);

            return true;
        } catch (SQLException e) {
            System.out.println("Error updating user: " + e.getMessage());
        }

        return false;
    }


    public static boolean INSERT_POST(String text, int userId) {
        String insertPostQuery = "INSERT INTO posts (id_user, text, date) VALUES (?, ?, DATETIME('now'))";

        try (Connection connection = connect();
             PreparedStatement postStatement = connection.prepareStatement(insertPostQuery, Statement.RETURN_GENERATED_KEYS)) {

            // Insert the post
            postStatement.setInt(1, userId);
            postStatement.setString(2, text);

            if (postStatement.executeUpdate() == 0) {
                System.out.println("Inserting post failed, no rows affected.");
                return false;
            }

        } catch (SQLException e) {
            System.out.println("Error publishing post: " + e.getMessage());
        }

        return false;
    }

    public static boolean INSERT_COMMENT(User user, String text, int idPost) {
        String insertPostQuery = "INSERT INTO comments (id_user, text, id_post, date) VALUES (?, ?, ?, DATETIME('now'))";

        try (Connection connection = connect();
             PreparedStatement postStatement = connection.prepareStatement(insertPostQuery, Statement.RETURN_GENERATED_KEYS)) {

            // Insert the post
            postStatement.setInt(1, user.getId());
            postStatement.setString(2, text);
            postStatement.setInt(3, idPost);

            if (postStatement.executeUpdate() == 0) {
                System.out.println("Inserting post failed, no rows affected.");
                return false;
            }

        } catch (SQLException e) {
            System.out.println("Error publishing comment: " + e.getMessage());
        }

        return false;
    }

    public static int INSERT_NOTE(String description, int idUser, String course, String type) {
        String getCourseIdQuery = "SELECT id FROM courses WHERE name = ?";
        String getTypeIdQuery = "SELECT id FROM note_types WHERE type = ?";
        String insertPostQuery = "INSERT INTO notes (description, id_user, id_course, id_type, dateTime, path) VALUES (?, ?, ?, ?, DATETIME('now'), '')";

        try (Connection connection = connect();
             PreparedStatement courseStatement = connection.prepareStatement(getCourseIdQuery);
             PreparedStatement typeStatement = connection.prepareStatement(getTypeIdQuery);
             PreparedStatement postStatement = connection.prepareStatement(insertPostQuery, Statement.RETURN_GENERATED_KEYS)) {

            courseStatement.setString(1, course);
            ResultSet courseResult = courseStatement.executeQuery();

            if (!courseResult.next()) {
                System.out.println("Course not found: " + course);
                return -1;
            }

            int idCourse = courseResult.getInt("id");

            typeStatement.setString(1, type);
            ResultSet typeResult = typeStatement.executeQuery();

            if (!typeResult.next()) {
                System.out.println("Type not found: " + type);
                return -1;
            }

            int idType = typeResult.getInt("id");



            postStatement.setString(1, description);
            postStatement.setInt(2, idUser);
            postStatement.setInt(3, idCourse);
            postStatement.setInt(4, idType);

            if (postStatement.executeUpdate() == 0) {
                System.out.println("Inserting note failed, no rows affected.");
                return -1;
            }

            try (ResultSet generatedKeys = postStatement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    return generatedKeys.getInt(1);
                } else {
                    System.out.println("Failed to retrieve the generated note ID.");
                    return -1;
                }
            }

        } catch (SQLException e) {
            System.out.println("Error saving the note: " + e.getMessage());
            return -1;
        }
    }


    public static boolean UPDATE_NOTE_PATH(int idNote, String path) {
        String updatePathQuery = "UPDATE notes SET path = ? WHERE id = ?";

        try (Connection connection = connect();
             PreparedStatement updateStatement = connection.prepareStatement(updatePathQuery)) {

            updateStatement.setString(1, path);
            updateStatement.setInt(2, idNote);

            if (updateStatement.executeUpdate() == 0) {
                System.out.println("Updating path failed, no rows affected.");
                return false;
            }

            return true;

        } catch (SQLException e) {
            System.out.println("Error updating the note path: " + e.getMessage());
            return false;
        }
    }






    public static ArrayList<Post> SELECT_POSTS() {
        //the last post has the largest id (similar to order for date)
        String query = "SELECT * FROM posts ORDER BY id DESC";
        ArrayList<Post> posts = new ArrayList<>();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        try (Connection connection = connect();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            ResultSet resultSet = preparedStatement.executeQuery();

            //reads all the posts and loads them into the list
            //expecting to add LIMIT if the number of the posts exponentially increases
            while(resultSet.next()){
                posts.add(new Post(
                        LocalDateTime.parse(resultSet.getString("date"), formatter),
                        resultSet.getString("text"),
                        resultSet.getInt("id"),
                        resultSet.getInt("id_user")
                ));
            }

            return posts;

        } catch (SQLException e) {
            System.out.println("Error retrieving posts: " + e.getMessage());
        }
        return null;
    }

    public static ArrayList<Comment> SELECT_COMMENTS(int id) {
        //comments ordered by insertion
        String query = "SELECT posts.id_user, users.username, users.image, comments.text, comments.date FROM comments join posts on comments.id_post = posts.id join users on comments.id_user = users.id WHERE posts.id = ? ORDER BY comments.id DESC";
        ArrayList<Comment> comments = new ArrayList<>();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        try (Connection connection = connect();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setInt(1, id);

            ResultSet resultSet = preparedStatement.executeQuery();

            while(resultSet.next()){
                comments.add(new Comment(
                        resultSet.getString("text"),
                        resultSet.getInt("id_user"),
                        resultSet.getString("username"),
                        LocalDateTime.parse(resultSet.getString("date"), formatter),
                        resultSet.getString("image")
                ));
            }

            return comments;

        } catch (SQLException e) {
            System.out.println("Error retrieving comments: " + e.getMessage());
        }
        return null;
    }


    public static ArrayList<Note> SELECT_NOTES(String field, String course, String username, String type) {
        String query = """
            SELECT n.*,t.type, c.name
            FROM notes n
            JOIN courses c ON n.id_course = c.id
            JOIN faculties f ON c.id_faculty = f.id
            JOIN users u ON u.id = n.id_user
            JOIN note_types t ON t.id = n.id_type
            WHERE 1=1
        """ +
                (course != null && !course.isEmpty() ? " AND c.name = ?" : "") +
                (username != null && !username.isEmpty() ? " AND u.username = ?" : "") +
                (field != null && !field.isEmpty() ? " AND f.name = ? " : "") +
                (type != null && !type.isEmpty() ? " AND t.type = ? " : "") +
                """
            ORDER BY n.dateTime DESC
        """;

        ArrayList<Note> notes = new ArrayList<>();

        try (Connection connection = connect();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            //the index changes with the number of set values
            int index = 1;
            if (course != null && !course.isEmpty()) {
                preparedStatement.setString(index++, course);
            }
            if (username != null && !username.isEmpty()) {
                preparedStatement.setString(index++, username);
            }
            if (field != null && !field.isEmpty()) {
                preparedStatement.setString(index++, field);
            }
            if (type != null && !type.isEmpty()) {
                preparedStatement.setString(index++, type);
            }

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                notes.add(new Note(
                        resultSet.getInt("id"),
                        resultSet.getInt("id_user"),
                        resultSet.getInt("id_course"),
                        resultSet.getString("path"),
                        resultSet.getString("description"),
                        resultSet.getString("dateTime"),
                        resultSet.getString("name"),
                        resultSet.getString("type")
                ));
            }

        } catch (SQLException e) {
            System.out.println("Error retrieving notes: " + e.getMessage());
        }

        return notes;
    }




    public static List<String> SELECT_USERNAMES() {

        //returns all the usernames in users (each username should exist only once)
        String query = "SELECT username FROM users WHERE username is not null and username <> 'admin'";

        try (Connection connection = connect();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            ResultSet resultSet = preparedStatement.executeQuery();
            List<String> usernames = new ArrayList<String>();
            while (resultSet.next()) {
                usernames.add(resultSet.getString("username"));
            }

            return usernames;

        } catch (SQLException e) {
            System.out.println("Error retrieving comments: " + e.getMessage());
        }
        return null;
    }

    public static List<String> SELECT_FACULTIES_NAME() {

        //returns all the usernames in users (each username should exist only once)
        String query = "SELECT name FROM faculties";

        try (Connection connection = connect();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            ResultSet resultSet = preparedStatement.executeQuery();


            return new ArrayList<>() {{
                while (resultSet.next()) {
                    add(resultSet.getString("name"));
                }
            }};


        } catch (SQLException e) {
            System.out.println("Error retrieving faculties: " + e.getMessage());
        }
        return null;
    }

    public static Map<String, List<String>> SELECT_COURSES_BY_FACULTY(String faculty) {
        String query = """
        SELECT faculties.name AS faculty, courses.name AS course 
        FROM courses 
        JOIN faculties ON courses.id_faculty = faculties.id
        WHERE faculty = ?
        ORDER BY faculties.name, courses.name
    """;

        Map<String, List<String>> coursesByField = new HashMap<>();

        try (Connection connection = connect();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, faculty);

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                String facultyName = resultSet.getString("faculty");
                String courseName = resultSet.getString("course");

                coursesByField.computeIfAbsent(facultyName, k -> new ArrayList<>()).add(courseName);
            }

        } catch (SQLException e) {
            System.out.println("Error retrieving courses by faculty: " + e.getMessage());
            e.printStackTrace();
        }

        return coursesByField;
    }

    public static List<String> SELECT_NOTE_TYPES() {

        //returns all the usernames in users (each username should exist only once)
        String query = "SELECT type FROM note_types";

        try (Connection connection = connect();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            ResultSet resultSet = preparedStatement.executeQuery();


            return new ArrayList<>() {{
                while (resultSet.next()) {
                    add(resultSet.getString("type"));
                }
            }};


        } catch (SQLException e) {
            System.out.println("Error retrieving note types: " + e.getMessage());
        }
        return null;
    }





}
