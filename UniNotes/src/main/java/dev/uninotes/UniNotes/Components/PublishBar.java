package dev.uninotes.UniNotes.Components;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;

public class PublishBar extends HorizontalLayout {

    private TextField textField;
    private Button publishButton;

    public PublishBar() {

        textField = new TextField();
        textField.setPlaceholder("Enter text...");
        textField.setWidth("300px");


        publishButton = new Button("Publish");
        publishButton.addClickListener(e -> {
            String text = textField.getValue();
            if (!text.trim().isEmpty()) {
                System.out.println("Published: " + text);
                textField.clear();
            } else {
                textField.setInvalid(true);
                textField.setErrorMessage("Text cannot be empty");
            }
        });

        // Layout
        add(textField, publishButton);
        setSpacing(true);
        setAlignItems(Alignment.CENTER);
        getStyle()
                .set("position", "sticky")
                .set("top", "0")
                .set("background", "#f8f9fa")
                .set("padding", "10px")
                .set("box-shadow", "0px 2px 5px rgba(0,0,0,0.1)");
        setWidthFull();
    }


    public Button getPublishButton() {
        return publishButton;
    }

    public TextField getTextField() {
        return textField;
    }
}
