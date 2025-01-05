package dev.uninotes.UniNotes;

import dev.uninotes.UniNotes.Utils.Utils;

import java.time.LocalDateTime;

public class Note {
    int id,idUser,idPost;
    String path, description;
    LocalDateTime dateTime;

    public Note(int id, int idUser, int idPost, String path, String description, String dateTime) {
        this.id = id;
        this.idUser = idUser;
        this.idPost = idPost;
        this.path = path;
        this.description = description;
        this.dateTime = Utils.dateTimeFormatter(dateTime);
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
