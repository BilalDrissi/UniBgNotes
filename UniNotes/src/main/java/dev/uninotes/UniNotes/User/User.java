package dev.uninotes.UniNotes.User;

import dev.uninotes.UniNotes.Utils.Utils;

public class User {
    
    private static User INSTANCE;
    private String username;
    private String email;
    private int id;
    private String name;
    private String surname;
    private String image;
    private int role;

    private User() {
        this.username = "";
        this.email = "";
    }
    
    public static synchronized User getInstance() { 
        if (INSTANCE == null) {
            INSTANCE = new User();
        }
        return INSTANCE;
    }

    public void init(int id, String username, String email, String name, String surname, String image, int role) {
        this.username = username;
        this.email = email;
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.image = (image != null && image != "") ?  image : Utils.getDefaultUserImagePath();
        this.role = role;
    }

    public void update(int id, String username, String email, String name, String surname, String image) {
        if(username != null && username != "")
            this.username = username;
        if(email != null && email != "")
            this.email = email;
        if(name != null && name != "")
            this.name = name;
        if(surname != null && surname != "")
            this.surname = surname;
        if(image != null && image != "")
            this.image = image;
    }

    public int getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public String getImage() {

        return image;
    }

    public void setImage(String image) { this.image = image; }
}
