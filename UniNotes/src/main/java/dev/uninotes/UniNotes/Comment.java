package dev.uninotes.UniNotes;

public class Comment {
    String comment;
    int id_user;

    public Comment(String comment, int id_user) {
        this.comment = comment;
        this.id_user = id_user;
    }

    public String getComment() {
        return comment;
    }

    public int getIdUser() { return id_user; }
}
