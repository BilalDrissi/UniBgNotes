package dev.uninotes.UniNotes.Components;

import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class NotesComponent extends HorizontalLayout {

    public NotesComponent(String profileImageUrl, String nickname, String notesImageUrl, String course, String description, LocalDateTime publishedDate) {
        setWidth("80%");
        setPadding(true);
        setSpacing(true);
        getStyle()
                .set("border-bottom", "1px solid #ddd")
                .set("margin", "20px auto");

        // Left Section: Profile Image + Notes Image
        VerticalLayout leftSection = new VerticalLayout();
        leftSection.setPadding(false);
        leftSection.setSpacing(false);
        leftSection.setAlignItems(FlexComponent.Alignment.CENTER);

        // Profile Image (circular and small)
        Image profileImage = new Image(profileImageUrl, "Profile Picture");
        profileImage.setWidth("50px");
        profileImage.setHeight("50px");
        profileImage.getStyle().set("border-radius", "50%").set("object-fit", "cover");

        // Nickname
        Span nicknameSpan = new Span(nickname);
        nicknameSpan.getStyle()
                .set("font-weight", "bold")
                .set("font-size", "14px")
                .set("margin-bottom", "10px");

        // Notes Image
        Image notesImage = new Image(notesImageUrl, "Notes Image");
        notesImage.setWidth("100px");
        notesImage.setHeight("150px");
        notesImage.getStyle().set("object-fit", "cover");

        leftSection.add(profileImage, nicknameSpan, notesImage);

        // Right Section: Course Title, Description, and Date
        VerticalLayout rightSection = new VerticalLayout();
        rightSection.setPadding(false);
        rightSection.setSpacing(false);
        rightSection.setWidthFull();

        // Course Title
        Span courseSpan = new Span(course);
        courseSpan.getStyle()
                .set("font-weight", "bold")
                .set("font-size", "18px")
                .set("margin-bottom", "5px");

        // Description
        Span descriptionSpan = new Span(description);
        descriptionSpan.getStyle()
                .set("font-size", "14px")
                .set("margin-bottom", "10px");

        // Published Date
        String formattedDate = publishedDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
        Span dateSpan = new Span("Published on: " + formattedDate);
        dateSpan.getStyle()
                .set("color", "gray")
                .set("font-size", "12px")
                .set("align-self", "flex-end");

        // Add course, description, and date
        rightSection.add(courseSpan, descriptionSpan, dateSpan);

        // Combine Left and Right Sections
        add(leftSection, rightSection);
        setAlignItems(Alignment.START);
    }
}
