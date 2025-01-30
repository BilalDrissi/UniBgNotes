package dev.uninotes.UniNotes.Components;


import com.vaadin.flow.component.html.IFrame;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import dev.uninotes.UniNotes.Database.DatabaseManager;
import dev.uninotes.UniNotes.Manager.FileManager;
import dev.uninotes.UniNotes.Note;

import java.io.File;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class NotesComponent extends HorizontalLayout {

    public NotesComponent(Note note) {

        getStyle()
                .set("border-bottom", "1px solid #ccc")
                .set("padding", "20px 0");


        String directoryPath = "src/main/resources/static" + note.getPath();
        File directory = new File(directoryPath);

        String filePath = null;
        if (directory.exists() && directory.isDirectory()) {
            File[] files = directory.listFiles();
            if (files != null && files.length > 0) {
                filePath = note.getPath() + files[0].getName();
            } else {
                System.out.println("Empty directory: " + directoryPath);
            }
        } else {
            System.out.println("Directory not found: " + directoryPath);
        }

        if (filePath == null) {
            filePath = "images/default.png";
        }

        boolean isImage = filePath.endsWith(".png") || filePath.endsWith(".jpg") || filePath.endsWith(".jpeg");
        boolean isPDF = filePath.endsWith(".pdf");

        HorizontalLayout filePreviewLayout = new HorizontalLayout();
        filePreviewLayout.setWidth("54px");
        filePreviewLayout.setHeight("96px");
        filePreviewLayout.setAlignItems(Alignment.CENTER);

        if (isImage) {
            Image coverImage = new Image("/" + filePath, "Notes File");
            coverImage.setWidth("54px");
            coverImage.setHeight("96px");
            filePreviewLayout.add(coverImage);
        } else if (isPDF) {
            IFrame pdfPreview = new IFrame("/" + filePath);
            pdfPreview.setWidth("54px");
            pdfPreview.setHeight("96px");
            pdfPreview.getStyle().set("border", "none");
            filePreviewLayout.add(pdfPreview);
        } else {
            Image fallbackImage = new Image("/images/file-preview.png", "File Preview");
            fallbackImage.setWidth("54px");
            fallbackImage.setHeight("96px");
            filePreviewLayout.add(fallbackImage);
        }

        VerticalLayout textLayout = new VerticalLayout();
        textLayout.setPadding(false);
        textLayout.setSpacing(false);

        HorizontalLayout topRow = new HorizontalLayout();
        topRow.setWidthFull();
        topRow.setJustifyContentMode(JustifyContentMode.BETWEEN);

        List<String> userInfos = DatabaseManager.SELECT_USERNAME_IMAGE_OF(note.getIdUser());

        Image profileImage = new Image(userInfos.get(1), "User Profile Picture");
        profileImage.setWidth("24px");
        profileImage.setHeight("24px");
        profileImage.getStyle()
                .set("border-radius", "50%")
                .set("object-fit", "cover");

        Span usernameSpan = new Span(userInfos.get(0));
        usernameSpan.getStyle().set("font-weight", "bold");

        HorizontalLayout userProfileLayout = new HorizontalLayout(profileImage, usernameSpan);
        userProfileLayout.setAlignItems(Alignment.CENTER);
        userProfileLayout.setSpacing(true);
        userProfileLayout.getStyle().set("gap", "8px");

        Span courseSpan = new Span(note.getCourse() + ", " + note.getType());
        courseSpan.getStyle().set("font-size", "12px").set("color", "gray");

        VerticalLayout userInfoLayout = new VerticalLayout(userProfileLayout, courseSpan);
        userInfoLayout.setPadding(false);
        userInfoLayout.setSpacing(false);

        Span dataSpan = new Span(note.getDateTime().format(DateTimeFormatter.ofPattern("dd/MM/yy")));
        dataSpan.getStyle().set("font-size", "12px");

        topRow.add(userInfoLayout, dataSpan);

        HorizontalLayout descriptionAndDownloadLayout = new HorizontalLayout();
        descriptionAndDownloadLayout.setWidthFull();
        descriptionAndDownloadLayout.setJustifyContentMode(JustifyContentMode.BETWEEN);
        descriptionAndDownloadLayout.setAlignItems(Alignment.CENTER);

        Span descriptionSpan = new Span(note.getDescription());

        Icon downloadIcon = new Icon(VaadinIcon.DOWNLOAD_ALT);
        downloadIcon.getStyle()
                .set("color", "#808080")
                .set("cursor", "pointer")
                .set("font-size", "16px");
        downloadIcon.addClickListener(e -> {
            String msg = FileManager.downloadNote(note.getId(), note.getPath());
            Notification.show(msg, 3000, Notification.Position.MIDDLE);

        });


        descriptionAndDownloadLayout.add(descriptionSpan, downloadIcon);

        textLayout.add(topRow, descriptionAndDownloadLayout);

        add(filePreviewLayout, textLayout);

        setSpacing(true);
        setWidth("50%");
        setAlignItems(Alignment.START);
    }
}
