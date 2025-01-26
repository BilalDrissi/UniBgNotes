package dev.uninotes.UniNotes;

import dev.uninotes.UniNotes.Database.DatabaseManager;

import java.time.LocalDateTime;

public class Post {

    private LocalDateTime dateTime;
    private String text;
    private int id_post;
    private UserForPost user;

    public Post(LocalDateTime dateTime, String text, int id_post, int id_user) {
        this.dateTime = dateTime;
        this.text = text;
        this.id_post = id_post;

        user = DatabaseManager.SELECT_USER_FOR_POST(id_user);


    }

    public Post() {

    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getIdPost() {
        return id_post;
    }

    public void setIdPost(int id_post) {
        this.id_post = id_post;
    }

    public String getUsernameOfPost() {
        return user.getUsername();
    }

    public int getIdPostOwner() {
        return user.getId();
    }

    public String getOwnerProfileImage() {
        return user.getImage();
    }


    //saves mainly infos for the owner of the post
    public static class UserForPost {
        private String username;
        private int id;
        private String image;

        public UserForPost(int id, String username, String image) {
            this.username = username;
            this.id = id;
            this.image = image;
        }

        private int getId() {
            return id;
        }

        private String getUsername() {
            return username;
        }

        private String getImage() {
            return image;
        }
    }

}
