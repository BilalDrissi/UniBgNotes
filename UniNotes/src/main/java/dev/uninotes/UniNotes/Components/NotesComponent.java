package dev.uninotes.UniNotes.Components;

import com.vaadin.flow.component.html.IFrame;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import dev.uninotes.UniNotes.Database.DatabaseManager;
import dev.uninotes.UniNotes.Note;

import java.io.File;

public class NotesComponent extends HorizontalLayout {

    public NotesComponent(Note note) {

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

        // placeholder for file preview
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

        Span usernameSpan = new Span(DatabaseManager.SELECT_USERNAME_OF(note.getIdUser()));
        usernameSpan.getStyle().set("font-weight", "bold");

        Span dataSpan = new Span(String.valueOf(note.getDateTime()));
        dataSpan.getStyle().set("font-size", "12px");

        topRow.add(usernameSpan, dataSpan);

        Span descriptionSpan = new Span(note.getDescription());

        textLayout.add(topRow, descriptionSpan);

        add(filePreviewLayout, textLayout);

        setSpacing(true);
        setWidth("50%");
        setAlignItems(Alignment.START);
    }
}
