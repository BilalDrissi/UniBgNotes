package dev.uninotes.UniNotes.Pages;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.component.upload.MultiFileReceiver;
import com.vaadin.flow.component.upload.Upload;
import com.vaadin.flow.router.Route;
import dev.uninotes.UniNotes.Components.NavBar;
import dev.uninotes.UniNotes.Database.DatabaseManager;
import dev.uninotes.UniNotes.User.User;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

@Route("profile")
public class ProfilePage extends VerticalLayout {

    private String imagePath = "";

    public ProfilePage() {

        add(new NavBar());

        Span title = new Span("Hello " + ((User.getInstance().getName() != null) ? User.getInstance().getName() : "user"));
        title.getStyle().set("font-size", "24px").set("font-weight", "bold");

        String img = (User.getInstance().getImage() != null) ? User.getInstance().getImage() : "";
        Image profileImage = new Image( img , "Profile Picture");
        profileImage.setWidth("100px");
        profileImage.setHeight("100px");
        profileImage.getStyle()
                .set("border-radius", "50%")
                .set("object-fit", "cover")
                .set("overflow", "hidden")
                .set("display", "block")
                .set("margin", "0 auto");
        HorizontalLayout titleLayout = new HorizontalLayout(title, profileImage);
        titleLayout.setAlignItems(Alignment.CENTER);

        // User information fields
        TextField nameField = new TextField("Name");
        nameField.setValue((User.getInstance().getName() != null) ? User.getInstance().getName() : "");
        nameField.setWidth("300px");

        TextField usernameField = new TextField("Username");
        usernameField.setValue((User.getInstance().getUsername() != null) ? User.getInstance().getUsername() : "");
        usernameField.setWidth("300px");

        TextField surnameField = new TextField("Surname");
        surnameField.setValue((User.getInstance().getSurname() != null) ? User.getInstance().getSurname() : "");
        surnameField.setWidth("300px");

        TextField emailField = new TextField("Email");
        emailField.setValue((User.getInstance().getEmail() != null) ? User.getInstance().getEmail() : "");
        emailField.setWidth("300px");

        // File upload component
        Span uploadLabel = new Span("Upload profile picture:");
        Upload image = new Upload((fileName, mimeType) -> {
            try {
                String uploadPath = "src/main/resources/static/images/profile/" + User.getInstance().getId() + "/profileImage/" + fileName;
                imagePath = "/images/profile/" + User.getInstance().getId() + "/profileImage/" + fileName;
                File tempFile = new File(uploadPath);
                tempFile.getParentFile().mkdirs();
                return new FileOutputStream(tempFile);
            } catch (IOException e) {
                Notification.show("Error uploading file: " + e.getMessage(), 5000, Notification.Position.MIDDLE);
                return null;
            }
        });


        // can upload only 1 photo
        image.setMaxFiles(1);

        image.addFileRejectedListener(event -> {
            Notification.show("Invalid file type: " + event.getErrorMessage(), 5000, Notification.Position.MIDDLE);
        });

        image.addSucceededListener(event -> {
            if (event.getMIMEType().startsWith("image/")) {
                profileImage.setSrc(imagePath + "?t=" + System.currentTimeMillis());
                Notification.show("File uploaded: " + event.getFileName(), 3000, Notification.Position.BOTTOM_CENTER);
            } else {
                Notification.show("The uploaded file is not a valid image.", 5000, Notification.Position.MIDDLE);
            }
        });


        image.setDropAllowed(true);
        image.setWidth("300px");

        // Save button
        Button saveButton = new Button("Save", event -> {
            String name = nameField.getValue();
            String surname = surnameField.getValue();
            String username = usernameField.getValue();
            String email = emailField.getValue();

            if(!DatabaseManager
                    .UPDATE_USER( User.getInstance().getId(), name, surname, email, username, imagePath)){
                Notification.show("Error occured, retry later", 5000, Notification.Position.MIDDLE);
                return;
            }
            // Placeholder for save logic
            Notification.show("Profile updated successfully!", 3000, Notification.Position.MIDDLE);

            //updates the username in the title
            title.setText("Hello " + name);
        });

        add(titleLayout, nameField, surnameField, usernameField, emailField, uploadLabel, image, saveButton);
        setAlignItems(Alignment.CENTER);
        setJustifyContentMode(JustifyContentMode.CENTER);
        setHeightFull();
    }
}
