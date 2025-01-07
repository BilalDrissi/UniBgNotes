package dev.uninotes.UniNotes.Login;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Anchor;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.QueryParameters;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.VaadinRequest;
import dev.uninotes.UniNotes.Components.NavBar;
import dev.uninotes.UniNotes.Database.DatabaseManager;
import dev.uninotes.UniNotes.Post;
import dev.uninotes.UniNotes.User.User;
import dev.uninotes.UniNotes.Utils.Utils;

import java.util.List;

@Route("password-manager")
public class PasswordManagePage extends VerticalLayout {

    public PasswordManagePage() {
        boolean isLost = !Utils.isLoggedIn();//if the user is logged he knows the password

        H1 title = isLost ? new H1("Lost Password") : new H1("Change Password");
        title.getStyle()
                .set("color", "#333")
                .set("font-family", "Arial, sans-serif");

        if (isLost) {
            //LOST PASSWORD
            TextField emailField = new TextField("Email");
            emailField.setPlaceholder("Enter your email");
            emailField.setWidth("300px");

            PasswordField newPasswordField = new PasswordField("New Password");
            newPasswordField.setPlaceholder("Enter new password");
            newPasswordField.setWidth("300px");

            PasswordField confirmPasswordField = new PasswordField("Confirm Password");
            confirmPasswordField.setPlaceholder("Confirm new password");
            confirmPasswordField.setWidth("300px");

            Button submitButton = new Button("Submit");
            submitButton.addClickListener(e -> {
                String email = emailField.getValue();
                String newPassword = newPasswordField.getValue();
                if(!newPassword.equals(confirmPasswordField.getValue())) {
                    Notification.show("Passwords do not match");
                    return;
                }
                if(!Utils.validatePassword(newPassword)) {
                    Notification.show("Password does not meet the requirements");
                    return;
                }
                newPassword = Utils.cryptPassword(newPassword);
                boolean res = DatabaseManager.UPDATE_USER_PASSWORD(email, newPassword);
                if(res){
                    Notification.show("Password changed successfully");
                    UI.getCurrent().getPage().setLocation("login");
                    return;
                }

                else
                    Notification.show("Error, try again later");
            });
            submitButton.getStyle()
                    .set("background-color", "#007BFF")
                    .set("color", "white")
                    .set("border", "none")
                    .set("padding", "10px 20px")
                    .set("border-radius", "5px")
                    .set("cursor", "pointer");
            Anchor loginLink = new Anchor("/login", "Back to Login");
            add(title, emailField, newPasswordField, confirmPasswordField, submitButton, loginLink);
        } else {
            //CHANGE PASSWORD

            //gives the opportunity to change page
            add(new NavBar());

            PasswordField oldPasswordField = new PasswordField("Old Password");
            oldPasswordField.setPlaceholder("Enter your old password");
            oldPasswordField.setWidth("300px");

            PasswordField newPasswordField = new PasswordField("New Password");
            newPasswordField.setPlaceholder("Enter new password");
            newPasswordField.setWidth("300px");

            PasswordField confirmPasswordField = new PasswordField("Confirm Password");
            confirmPasswordField.setPlaceholder("Confirm new password");
            confirmPasswordField.setWidth("300px");

            Button submitButton = new Button("Submit");
            submitButton.addClickListener(e -> {
                String oldPassword = oldPasswordField.getValue();
                String newPassword = newPasswordField.getValue();
                if(!newPassword.equals(confirmPasswordField.getValue())) {
                    Notification.show("Passwords do not match");
                    return;
                }
                if(!Utils.validatePassword(newPassword)) {
                    Notification.show("Password does not meet the requirements");
                    return;
                }
                oldPassword = Utils.cryptPassword(oldPassword);
                newPassword = Utils.cryptPassword(newPassword);
                boolean res = DatabaseManager.UPDATE_USER_PASSWORD(User.getInstance().getEmail(), newPassword, oldPassword);
                if(res){
                    Notification.show("Password changed successfully");
                    UI.getCurrent().getPage().setLocation("login");
                    return;
                }
                else
                    Notification.show("Error, the old password is incorrect");
            });


            submitButton.getStyle()
                    .set("background-color", "#007BFF")
                    .set("color", "white")
                    .set("border", "none")
                    .set("padding", "10px 20px")
                    .set("border-radius", "5px")
                    .set("cursor", "pointer");

            Anchor loginLink = new Anchor("/login", "Back to Login");

            add(title, oldPasswordField, newPasswordField, confirmPasswordField, submitButton, loginLink);
        }

        setAlignItems(FlexComponent.Alignment.CENTER);
        setJustifyContentMode(FlexComponent.JustifyContentMode.CENTER);
        setSizeFull();
    }
}
