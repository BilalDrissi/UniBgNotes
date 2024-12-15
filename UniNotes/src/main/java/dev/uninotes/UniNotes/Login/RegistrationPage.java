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
import com.vaadin.flow.router.Route;
import dev.uninotes.UniNotes.Database.DatabaseManager;
import dev.uninotes.UniNotes.Utils.Utils;
import org.springframework.security.crypto.bcrypt.BCrypt;

import java.util.regex.Pattern;

@Route("register")
public class RegistrationPage extends VerticalLayout {

    public RegistrationPage() {

        H1 title = new H1("Register to UniBG Notes");
        title.getStyle()
                .set("color", "#333")
                .set("font-family", "Arial, sans-serif");


        TextField emailField = new TextField("Email");
        emailField.setPlaceholder("email");
        emailField.setWidth("300px");

        PasswordField passwordField = new PasswordField("Password");
        passwordField.setPlaceholder("password");
        passwordField.setWidth("300px");

        PasswordField confirmPasswordField = new PasswordField("Confirm Password");
        confirmPasswordField.setPlaceholder("password");
        confirmPasswordField.setWidth("300px");


        Button registerButton = new Button("Register", event -> {
            String email = emailField.getValue();
            String password = passwordField.getValue();
            String confirmPassword = confirmPasswordField.getValue();

            // if the password does not get validated
            if (!validatePassword(password)) {
                Notification.show("Password must be at least 8 characters long, include a number, a special character, and an uppercase letter.", 5000, Notification.Position.MIDDLE);
                return;
            }

            // if passwords are not the same it stops the registration
            if (!password.equals(confirmPassword)) {
                Notification.show("Passwords do not match!", 3000, Notification.Position.MIDDLE);
                return;
            }

            boolean success = DatabaseManager.addUser(email, Utils.cryptPassword(password));
            if (success) {
                Notification.show("User registered successfully!", 3000, Notification.Position.MIDDLE);
                registeredAndLoggedIn(email);
            } else {
                Notification.show("Error registering user. Email might already be in use.", 5000, Notification.Position.MIDDLE);
            }
        });

        Anchor loginLink = new Anchor("/login", "Back to Login");

        add(title, emailField, passwordField, confirmPasswordField, registerButton, loginLink);

        // style
        setAlignItems(FlexComponent.Alignment.CENTER);
        setJustifyContentMode(FlexComponent.JustifyContentMode.CENTER);
        setSizeFull();
    }

    private boolean validatePassword(String password) {
        String passwordPattern = "^(?=.*[A-Z])(?=.*[0-9])(?=.*[!@#$%^&*]).{8,}$";
        return Pattern.matches(passwordPattern, password);
    }

    private void registeredAndLoggedIn(String email){
        Utils.userLoggedIn(email);
        UI.getCurrent().navigate("home");
    }
}
