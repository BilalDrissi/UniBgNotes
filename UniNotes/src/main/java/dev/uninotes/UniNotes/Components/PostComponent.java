package dev.uninotes.UniNotes.Components;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextArea;
import dev.uninotes.UniNotes.Comment;
import dev.uninotes.UniNotes.Database.DatabaseManager;
import dev.uninotes.UniNotes.User.User;
import dev.uninotes.UniNotes.Utils.Utils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class PostComponent extends VerticalLayout {
    private boolean commentSectionVisible = false;
    private VerticalLayout commentSection;
    private VerticalLayout existingCommentsLayout;

    public PostComponent(int idPost, String username, String userImage, String text, LocalDateTime dateTime) {
        setPadding(true);
        setSpacing(true);

        getStyle()
                .set("border-bottom", "1px solid #ccc")
                .set("padding", "20px 0");

        Image profileImage = new Image((userImage != null && !userImage.isEmpty()) ? userImage : Utils.getDefaultProfileImagePath(), "Profile picture");
        profileImage.setWidth("50px");
        profileImage.setHeight("50px");
        profileImage.getStyle()
                .set("border-radius", "50%")
                .set("object-fit", "cover");

        Span userNameSpan = new Span(username);
        userNameSpan.getStyle().set("font-weight", "bold").set("margin-left", "10px");

        Span timeSpan = new Span(dateTime.format(DateTimeFormatter.ofPattern("HH:mm dd/MM/yy")));
        timeSpan.getStyle().set("color", "gray")
                .set("font-size", "12px")
                .set("margin-left", "auto");

        HorizontalLayout headerLayout = new HorizontalLayout(profileImage, userNameSpan, timeSpan);
        headerLayout.setAlignItems(Alignment.CENTER);
        headerLayout.setWidthFull();
        headerLayout.getStyle().set("gap", "10px");

        Span postText = new Span(text);
        postText.getStyle().set("font-size", "14px");

        Icon commentIcon = new Icon(VaadinIcon.COMMENT);
        commentIcon.getStyle()
                .set("color", "#808080")
                .set("cursor", "pointer")
                .set("font-size", "16px");


        HorizontalLayout textAndIconLayout = new HorizontalLayout(postText, commentIcon);
        textAndIconLayout.setWidthFull();
        textAndIconLayout.setJustifyContentMode(JustifyContentMode.BETWEEN);
        textAndIconLayout.setAlignItems(Alignment.CENTER);
        textAndIconLayout.getStyle().set("margin-top", "5px");

        commentIcon.addClickListener(e -> toggleCommentSection());

        existingCommentsLayout = new VerticalLayout();
        existingCommentsLayout.setPadding(false);
        existingCommentsLayout.setSpacing(true);
        existingCommentsLayout.setWidthFull();
        existingCommentsLayout.getStyle().set("margin-left", "20px");

        loadComments(idPost);

        //new comments
        commentSection = new VerticalLayout();
        commentSection.setVisible(false);
        commentSection.setSpacing(true);
        commentSection.setPadding(false);
        commentSection.setWidthFull();

        TextArea commentField = new TextArea();
        commentField.setPlaceholder("Write a comment...");
        commentField.setWidth("100%");

        Button publishCommentButton = new Button("Publish", ev -> {
            String commentText = commentField.getValue();
            if (!commentText.trim().isEmpty()) {
                LocalDateTime now = LocalDateTime.now();
                Comment newComment = new Comment(commentText, User.getInstance().getId(), User.getInstance().getUsername(), now, User.getInstance().getImage());
                existingCommentsLayout.add(createCommentComponent(newComment));

                // insert the comment
                DatabaseManager.INSERT_COMMENT(User.getInstance(), commentText, idPost);

                System.out.println("New comment: " + commentText);
                commentField.clear();
            }
        });

        commentSection.add(commentField, publishCommentButton);

        add(headerLayout, textAndIconLayout, existingCommentsLayout, commentSection);
        setWidth("100%");
    }

    // loads the comments of a post
    private void loadComments(int idPost) {
        ArrayList<Comment> comments = DatabaseManager.SELECT_COMMENTS(idPost);
        if (comments != null && !comments.isEmpty()) {
            for (Comment comment : comments) {
                existingCommentsLayout.add(createCommentComponent(comment));
            }
        }
    }

    private void toggleCommentSection() {
        commentSectionVisible = !commentSectionVisible;
        commentSection.setVisible(commentSectionVisible);
    }

    private VerticalLayout createCommentComponent(Comment comment) {
        Image profileImage = new Image((comment.getImage() != null && !comment.getImage().isEmpty()) ? comment.getImage() : Utils.getDefaultProfileImagePath(), "Comment profile picture");
        profileImage.setWidth("30px");
        profileImage.setHeight("30px");
        profileImage.getStyle()
                .set("border-radius", "50%")
                .set("object-fit", "cover");

        Span userNameSpan = new Span(comment.getUsername());
        userNameSpan.getStyle().set("font-weight", "bold").set("margin-left", "10px");

        Span timeSpan = new Span(comment.getDateTime().format(DateTimeFormatter.ofPattern("HH:mm dd/MM/yy")));
        timeSpan.getStyle().set("color", "gray")
                .set("font-size", "12px")
                .set("margin-left", "auto");

        Span commentText = new Span(comment.getText());
        commentText.getStyle().set("font-size", "14px");

        HorizontalLayout headerLayout = new HorizontalLayout(profileImage, userNameSpan, timeSpan);
        headerLayout.setAlignItems(Alignment.CENTER);
        headerLayout.setWidthFull();
        headerLayout.getStyle().set("gap", "10px");

        VerticalLayout commentLayout = new VerticalLayout(headerLayout, commentText);
        commentLayout.setSpacing(false);
        commentLayout.getStyle()
                .set("border-left", "2px solid #ccc")
                .set("padding-left", "10px")
                .set("margin-bottom", "10px");

        return commentLayout;
    }
}
