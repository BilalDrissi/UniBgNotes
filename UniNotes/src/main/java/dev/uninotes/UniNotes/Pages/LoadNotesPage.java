package dev.uninotes.UniNotes.Pages;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.upload.MultiFileReceiver;
import com.vaadin.flow.component.upload.Upload;
import com.vaadin.flow.router.Route;
import dev.uninotes.UniNotes.Components.NavBar;
import dev.uninotes.UniNotes.Database.DatabaseManager;
import dev.uninotes.UniNotes.SearchDocumetsManager;
import dev.uninotes.UniNotes.User.User;
import dev.uninotes.UniNotes.Utils.Utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Route("load-documents")
public class LoadNotesPage extends VerticalLayout {

    private List<String> uploadedFilePaths = new ArrayList<>();

    public LoadNotesPage() {

        Utils.redirectToLoginIfNotLoggedIn();

        add(new NavBar());

        // Title and user greeting
        Span title = new Span("Upload Your Notes");
        title.getStyle().set("font-size", "24px").set("font-weight", "bold");

        HorizontalLayout titleLayout = new HorizontalLayout(title);
        titleLayout.setAlignItems(FlexComponent.Alignment.CENTER);

        // Description field
        TextArea descriptionField = new TextArea("Description");
        descriptionField.setPlaceholder("Enter a description for your notes...");
        descriptionField.setWidth("300px");
        descriptionField.setMaxLength(500);

        // ComboBox for Field of Study
        ComboBox<String> fieldOfStudyComboBox = new ComboBox<>("Field of Study");
        fieldOfStudyComboBox.setPlaceholder("Select field of study...");
        fieldOfStudyComboBox.setItems(SearchDocumetsManager.loadFaculties());
        fieldOfStudyComboBox.setWidth("300px");
        fieldOfStudyComboBox.setAllowCustomValue(false);

        // ComboBox for Course
        ComboBox<String> courseComboBox = new ComboBox<>("Course");
        courseComboBox.setPlaceholder("Select course...");
        courseComboBox.setWidth("300px");
        courseComboBox.setAllowCustomValue(false);

        // ComboBox for Type
        ComboBox<String> typeComboBox = new ComboBox<>("Note Type");
        typeComboBox.setPlaceholder("Select the notes type...");
        typeComboBox.setItems(Utils.loadNoteTypes());
        typeComboBox.setWidth("300px");
        typeComboBox.setAllowCustomValue(false);

        fieldOfStudyComboBox.addValueChangeListener(event -> {
            String selectedField = event.getValue();
            if (selectedField != null) {
                Map<String, List<String>> coursesByField = SearchDocumetsManager.loadCourses(selectedField);
                List<String> courses = coursesByField.getOrDefault(selectedField, List.of());
                if (!courses.isEmpty()) {
                    courseComboBox.setItems(courses);
                }
            }
        });


        // File upload component
        Span uploadLabel = new Span("Upload your notes:");
        Upload notesUpload = new Upload((MultiFileReceiver) (fileName, mimeType) -> {
            try {
                String uploadPath = "src/main/resources/static/temp/" + User.getInstance().getId() + "/" + fileName;
                uploadedFilePaths.add(fileName);

                File tempFile = new File(uploadPath);
                tempFile.getParentFile().mkdirs();

                return new FileOutputStream(tempFile);
            } catch (Exception e) {
                Notification.show("Error uploading file: " + e.getMessage(), 5000, Notification.Position.MIDDLE);
                return null;
            }
        });

        notesUpload.setMaxFiles(5); // Allows up to 5 files
        notesUpload.setAcceptedFileTypes("application/pdf", "image/png", "image/jpeg");
        notesUpload.setDropAllowed(true);
        notesUpload.setWidth("300px");

        notesUpload.addFileRejectedListener(event -> {
            Notification.show("Invalid file type: " + event.getErrorMessage(), 5000, Notification.Position.MIDDLE);
        });

        notesUpload.addSucceededListener(event -> {
            Notification.show("File uploaded: " + event.getFileName(), 3000, Notification.Position.MIDDLE);
        });

        // Load button
        Button loadButton = new Button("Load", event -> {
            String description = descriptionField.getValue();
            String selectedCourse = courseComboBox.getValue();
            String noteType = typeComboBox.getValue();

            if (description.isEmpty()) {
                Notification.show("Description cannot be empty", 5000, Notification.Position.MIDDLE);
                return;
            }

            if (selectedCourse == null || selectedCourse.isEmpty()) {
                Notification.show("Please select a course", 5000, Notification.Position.MIDDLE);
                return;
            }

            if (noteType == null || noteType.isEmpty()) {
                Notification.show("Please select a note type", 5000, Notification.Position.MIDDLE);
                return;
            }

            if (uploadedFilePaths.isEmpty()) {
                Notification.show("Please upload at least one file", 5000, Notification.Position.MIDDLE);
                return;
            }

            int idNote = DatabaseManager.INSERT_NOTE(description, User.getInstance().getId(), selectedCourse, noteType);
            if (idNote > 0) {
                String noteFolderPath = "src/main/resources/static/notes/" + idNote;
                String pathToUpload = "/notes/" + idNote + "/";
                File noteFolder = new File(noteFolderPath);
                noteFolder.mkdirs();

                for (String filePath : uploadedFilePaths) {
                    Path oldFilePath = Paths.get("src/main/resources/static/temp/" + User.getInstance().getId() + "/" + filePath);
                    Path newFilePath = Paths.get(noteFolderPath + "/" + oldFilePath.getFileName());
                    try {
                        Files.move(oldFilePath, newFilePath, StandardCopyOption.REPLACE_EXISTING);
                    } catch (IOException e) {
                        Notification.show("Error moving file: " + oldFilePath.getFileName() + " - " + e.getMessage(), 5000, Notification.Position.MIDDLE);
                    }
                }

                DatabaseManager.UPDATE_NOTE_PATH(idNote, pathToUpload);

                Notification.show("Notes uploaded successfully!", 3000, Notification.Position.MIDDLE);
            } else {
                Notification.show("Error saving note in database.", 5000, Notification.Position.MIDDLE);
            }

            descriptionField.clear();
            courseComboBox.clear();
            fieldOfStudyComboBox.clear();
            notesUpload.clearFileList();
            uploadedFilePaths.clear();
            typeComboBox.clear();
        });


        add(titleLayout, descriptionField, fieldOfStudyComboBox, courseComboBox, typeComboBox, uploadLabel, notesUpload, loadButton);
        setAlignItems(FlexComponent.Alignment.CENTER);
        setJustifyContentMode(FlexComponent.JustifyContentMode.CENTER);
        setHeightFull();
    }
}
