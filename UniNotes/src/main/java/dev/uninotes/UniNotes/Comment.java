package dev.uninotes.UniNotes;

import java.time.LocalDateTime;

public class Comment {
    String text;
    int id_user;
    LocalDateTime dateTime;
    String image;
    String username;


    public Comment(String text, int id_user, String username, LocalDateTime dateTime, String image) {
        this.text = text;
        this.id_user = id_user;
        this.dateTime = dateTime;
        this.image = image;
        this.username = username;
    }

    public String getText() {
        return text;
    }

    public int getIdUser() {
        return id_user;
    }

    public String getImage() {
        return image;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public String getUsername() {
        return username;
    }
}
