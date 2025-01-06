package dev.uninotes.UniNotes.Components;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.router.AfterNavigationEvent;
import com.vaadin.flow.router.AfterNavigationObserver;

public class NavBar extends HorizontalLayout implements AfterNavigationObserver {

    private Button profileButton;
    private Button addButton;
    private Button searchButton;
    private Button postsButton;

    public NavBar() {
        setWidth("100%");
        setHeight("100px");
        setJustifyContentMode(FlexComponent.JustifyContentMode.CENTER);
        setAlignItems(FlexComponent.Alignment.CENTER);
        getStyle().set("position", "fixed").set("bottom", "0").set("left", "0").set("right", "0");

        profileButton = createButton(VaadinIcon.USER, "profile");
        addButton = createButton(VaadinIcon.PLUS, "load-documents");
        searchButton = createButton(VaadinIcon.SEARCH, "search-documents");
        postsButton = createButton(VaadinIcon.COMMENT, "posts");
        profileButton.getStyle()
                .set("color", "black")
                .set("cursor", "pointer")
                .set("font-size", "24px");
        addButton.getStyle()
                .set("color", "black")
                .set("cursor", "pointer")
                .set("font-size", "24px");
        searchButton
                .getStyle()
                .set("color", "black")
                .set("cursor", "pointer")
                .set("font-size", "24px");

        postsButton.getStyle()
                .set("color", "black")
                .set("cursor", "pointer")
                .set("font-size", "24px");
        add(profileButton, addButton, searchButton, postsButton);
    }

    private Button createButton(VaadinIcon icon, String route) {
        Button button = new Button(new Icon(icon));
        button.addClickListener(e -> {
            UI.getCurrent().navigate(route);
        });
        button.getStyle()
                .set("font-size", "24px")
                .set("width", "50px")
                .set("height", "50px");
        return button;
    }

    private void resetButtonStyles() {
        profileButton.getStyle().set("background-color", "");
        addButton.getStyle().set("background-color", "");
        searchButton.getStyle().set("background-color", "");
        postsButton.getStyle().set("background-color", "");
    }

    private void highlightButton(Button button) {
        button.getStyle().set("background-color", "#8080ff");
    }

    @Override
    public void afterNavigation(AfterNavigationEvent event) {
        String activeView = UI.getCurrent().getInternals().getActiveViewLocation().getPath();

        resetButtonStyles();

        if (activeView.equals("profile")) {
            highlightButton(profileButton);
        } else if (activeView.equals("load-documents")) {
            highlightButton(addButton);
        } else if (activeView.equals("search-documents")) {
            highlightButton(searchButton);
        } else if (activeView.equals("posts")) {
            highlightButton(postsButton);
        }
    }
}
