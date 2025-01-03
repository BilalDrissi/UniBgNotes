package dev.uninotes.UniNotes.Components;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextArea;

import com.vaadin.flow.component.orderedlayout.HorizontalLayout;

import java.time.format.DateTimeFormatter;
import java.time.LocalDateTime;

public class PostComponent extends VerticalLayout {
    private boolean commentSectionVisible = false;
    private VerticalLayout commentSection;

    public PostComponent(String username, String userImage, String text, LocalDateTime dateTime) {
        setPadding(true);
        setSpacing(true);

        getStyle()
                .set("border-bottom", "1px solid #ccc")
                .set("padding", "20px 0");


        Image profileImage = new Image((userImage != null ) ? userImage : "" , "Profile picture");
        profileImage.setWidth("50px");
        profileImage.setHeight("50px");
        profileImage.getStyle()
                .set("border-radius", "50%")
                .set("object-fit", "cover");

        Span userNameSpan = new Span(username);
        userNameSpan.getStyle().set("font-weight", "bold").set("margin-left", "10px");

        Span timeSpan = new Span(dateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")));
        timeSpan.getStyle().set("color", "gray")
                .set("font-size", "12px")
                .set("margin-left", "auto");

        HorizontalLayout headerLayout = new HorizontalLayout(profileImage, userNameSpan, timeSpan);
        headerLayout.setAlignItems(Alignment.CENTER);
        headerLayout.setWidthFull();
        headerLayout.getStyle().set("gap", "10px");


        Span commentSpan = new Span(text);
        commentSpan.getStyle().set("font-size", "14px");

        // Link "comment"
        Span commentLink = new Span("comment");
        commentLink.getStyle()
                .set("color", "#007BFF")
                .set("cursor", "pointer")
                .set("font-size", "14px");

        commentLink.addClickListener(e -> toggleCommentSection());


        commentSection = new VerticalLayout();
        commentSection.setVisible(false);
        commentSection.setSpacing(true);
        commentSection.setPadding(false);
        commentSection.setWidthFull();

        TextArea commentField = new TextArea();
        commentField.setPlaceholder("Write a comment...");
        commentField.setWidth("100%");

        Button publishCommentButton = new Button("Publish", ev -> {
            String commentValue = commentField.getValue();
            if (!commentValue.trim().isEmpty()) {
                // TODO: publish comment

                System.out.println("New comment: " + commentValue);
                commentField.clear();
            }
        });

        commentSection.add(commentField, publishCommentButton);

        add(headerLayout, commentSpan, commentLink, commentSection);
        setWidth("100%");
    }

    private void toggleCommentSection() {
        commentSectionVisible = !commentSectionVisible;
        commentSection.setVisible(commentSectionVisible);
    }
}
