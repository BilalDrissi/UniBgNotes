package dev.uninotes.UniNotes.Login;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;

@Route("forgot-password")
public class ForgotPasswordPage extends VerticalLayout {

    public ForgotPasswordPage() {
        // Titolo della pagina
        H1 title = new H1("Recupero Password");
        title.getStyle()
                .set("color", "#333")
                .set("font-family", "Arial, sans-serif");

        // email
        TextField emailField = new TextField("Email");
        emailField.setPlaceholder("enter the email address to reset the password");
        emailField.setWidth("300px");

        // send
        Button sendButton = new Button("Send", e -> {

            addNewPassword();

            System.out.println("clicked -> send");


        });
        sendButton.getStyle()
                .set("background-color", "#007BFF")
                .set("color", "white")
                .set("border", "none")
                .set("padding", "10px 20px")
                .set("border-radius", "5px")
                .set("cursor", "pointer");


        setAlignItems(FlexComponent.Alignment.CENTER);
        setJustifyContentMode(FlexComponent.JustifyContentMode.CENTER);
        setSizeFull();


        add(title, emailField, sendButton);
    }

    private void addNewPassword(){
        PasswordField passwordField = new PasswordField("Password");
        passwordField.setPlaceholder("password");
        passwordField.setWidth("300px");

        PasswordField confirmPasswordField = new PasswordField("Confirm Password");
        confirmPasswordField.setPlaceholder("password");
        confirmPasswordField.setWidth("300px");

        add(passwordField, confirmPasswordField);
    }
}
