package dev.uninotes.UniNotes.Login;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Anchor;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import dev.uninotes.UniNotes.Database.DatabaseManager;
import dev.uninotes.UniNotes.User.User;
import dev.uninotes.UniNotes.Utils.Session;
import dev.uninotes.UniNotes.Utils.Utils;

import javax.xml.transform.Result;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

@Route("login")
public class LoginPage extends VerticalLayout {

    public LoginPage() {

        // page title
        H1 title = new H1("Login to UniBG Notes");
        title.getStyle()
                .set("color", "#333")
                .set("font-family", "Arial, sans-serif");

        // email
        TextField emailOrUsernameField = new TextField("Email or Username");
        emailOrUsernameField.setPlaceholder("email or username");
        emailOrUsernameField.setWidth("300px");

        // password
        PasswordField passwordField = new PasswordField("Password");
        passwordField.setPlaceholder("password");
        passwordField.setWidth("300px");

        // login button
        Button loginButton = new Button("Login", e -> {
            System.out.println("clicked -> login");


            //if it is a mail and is valid
            if(Utils.find(emailOrUsernameField.getValue(), '@') >= 0 && !Utils.isValidEmail(emailOrUsernameField.getValue())){
                Notification.show("You should enter a valid email", 5000, Notification.Position.MIDDLE);
                return;
            }


            if(DatabaseManager.validateUser(emailOrUsernameField.getValue(), passwordField.getValue())){
                //there was an error getting the user infos
                if(!Utils.userLoggedIn(emailOrUsernameField.getValue())) return;

                Notification.show("Login Successful", 5000, Notification.Position.MIDDLE);

                //logged in the session
                Utils.loggedIn();

                //user logged
                UI.getCurrent().navigate("home");

            } else {
                Notification.show("Invalid credentials", 5000, Notification.Position.MIDDLE);
            }

        });

        //can press send with enter
        loginButton.addClickShortcut(com.vaadin.flow.component.Key.ENTER);

        loginButton.getStyle()
                .set("background-color", "#007BFF")
                .set("color", "white")
                .set("border", "none")
                .set("padding", "10px 20px")
                .set("border-radius", "5px")
                .set("cursor", "pointer");

        // Register link
        Anchor registerLink = new Anchor("/register", "You do not have an account");
        registerLink.getStyle()
                .set("font-size", "14px")
                .set("color", "#007BFF")
                .set("text-decoration", "none");



        // Forgot Password link
        Anchor forgotPasswordLink = new Anchor("/forgot-password", "Did you forget the password?");
        forgotPasswordLink.getStyle()
                .set("font-size", "14px")
                .set("color", "#007BFF")
                .set("text-decoration", "none");

        // style
        setAlignItems(FlexComponent.Alignment.CENTER);
        setJustifyContentMode(FlexComponent.JustifyContentMode.CENTER);
        setSizeFull();

        // adding component
        add(title, emailOrUsernameField, passwordField, loginButton, new Span(registerLink), new Span(forgotPasswordLink));
    }

}
