package dev.uninotes.UniNotes.Database;

import dev.uninotes.UniNotes.User.User;
import dev.uninotes.UniNotes.Utils.Utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

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
            return true;
        } catch (SQLException e) {
            System.out.println("Error updating user: " + e.getMessage());
        }

        return false;
    }


    public static boolean INSERT_POST(String text, int userId) {
        String insertPostQuery = "INSERT INTO posts (text) VALUES (?)";
        String insertRelationQuery = "INSERT INTO user_post (idUser, idPost) VALUES (?, ?)";

        try (Connection connection = connect();
             PreparedStatement postStatement = connection.prepareStatement(insertPostQuery, Statement.RETURN_GENERATED_KEYS)) {

            // Insert the post
            postStatement.setString(1, text);


            if (postStatement.executeUpdate() == 0) {
                System.out.println("Inserting post failed, no rows affected.");
                return false;
            }

            // Retrieve the generated post ID
            try (ResultSet generatedKeys = postStatement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    int postId = generatedKeys.getInt(1);

                    // Insert into user_post relation
                    try (PreparedStatement relationStatement = connection.prepareStatement(insertRelationQuery)) {
                        relationStatement.setInt(1, userId);
                        relationStatement.setInt(2, postId);
                        relationStatement.executeUpdate();
                    }

                    System.out.println("Post inserted successfully with ID: " + postId);
                    return true;
                } else {
                    System.out.println("Inserting post failed, no ID obtained.");
                    return false;
                }
            }
        } catch (SQLException e) {
            System.out.println("Error publishing post: " + e.getMessage());
        }

        return false;
    }




}
