package dev.uninotes.UniNotes;

import dev.uninotes.UniNotes.Utils.Utils;

import java.time.LocalDateTime;

public class Note {
    int id, idUser, idPost;
    String path, description, course, type;
    LocalDateTime dateTime;

    public Note(int id, int idUser, int idPost, String path, String description, String dateTime, String course, String type) {
        this.id = id;
        this.idUser = idUser;
        this.idPost = idPost;
        this.path = path;
        this.description = description;
        this.dateTime = Utils.dateTimeFormatter(dateTime);
        this.course = course;
        this.type = type;
    }

    public String getCourse() {
        return course;
    }

    public String getType() {
        return type;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public int getId() {
        return id;
    }

    public int getIdUser() {
        return idUser;
    }

    public int getIdPost() {

        return idPost;
    }

    public String getPath() {
        return path;
    }

    public String getDescription() {
        return description;
    }

}
