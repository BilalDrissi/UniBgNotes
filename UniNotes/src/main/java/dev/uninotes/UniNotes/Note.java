package dev.uninotes.UniNotes;

import dev.uninotes.UniNotes.Utils.Utils;

import java.time.LocalDateTime;

public class Note {
    String username;
    String image;
    String text;
    LocalDateTime dateTime;

    public Note(String username, String image, String text, String dateTime) {
        this.username = username;
        this.image = image;
        this.text = text;
        this.dateTime = Utils.dateTimeFormatter(dateTime);
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public String getUsername() {
        return username;
    }

    public String getImage() {
        return image;
    }

    public String getText() {
        return text;
    }
}
