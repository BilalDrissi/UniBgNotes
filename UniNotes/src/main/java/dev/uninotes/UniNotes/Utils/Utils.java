package dev.uninotes.UniNotes.Utils;

import com.vaadin.flow.component.UI;
import dev.uninotes.UniNotes.Database.DatabaseManager;
import dev.uninotes.UniNotes.User.User;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Utils {


    public static boolean isValidEmail(String email) {
        // Regex to email check
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";

        Pattern pattern = Pattern.compile(emailRegex);

        Matcher matcher = pattern.matcher(email);

        return matcher.matches();
    }

    public static String cryptPassword(String password) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");

            byte[] hash = digest.digest(password.getBytes());

            StringBuilder hexString = new StringBuilder();
            for (byte b : hash) {
                hexString.append(String.format("%02x", b));
            }

            return hexString.toString().substring(0, 32);

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void loggedIn(){
        Session.getInstance().setLogged(true);
    }

    public static void loggedOut(){
        Session.getInstance().setLogged(false);
    }

    public static boolean isLoggedIn(){
        return Session.getInstance().isLogged();
    }

    public static void redirectToLoginIfNotLoggedIn(){
        if(!Session.getInstance().isLogged()){
            UI.getCurrent().navigate("/login");
            return;
        }
    }

    public static boolean userLoggedIn(String emailOrUsernameField){
        Map<String, String> result = DatabaseManager.SELECT_USER(emailOrUsernameField);

        if(result == null) return false;

        User.getInstance().init(
                Integer.parseInt(result.get("id")),
                result.get("username"),
                result.get("email"),
                result.get("name"),
                result.get("surname"),
                result.get("image"),
                Integer.parseInt(result.get("role"))
        );
        return true;
    }

    //returns the position of a char in a string
    public static int find(String s, char c){
        for(int i = 0; i < s.length(); i++){
            if(s.charAt(i) == c) return i;
        }
        return -1;
    }

    public static LocalDateTime dateTimeFormatter(String dateTime){
        return LocalDateTime.parse(dateTime, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }

    public static String getDefaultProfileImagePath(){
        return "images/default/user.jpg";
    }

    public static String getDirectoryOfDowloads() {
        return "downloads/";
    }

    public static Path getDownloadsFolder() {
        String userHome = System.getProperty("user.home");
        return Paths.get(userHome, "Downloads");
    }


}
