package dev.uninotes.UniNotes.Components;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.icon.VaadinIcon;

public class NavBar extends HorizontalLayout {

    public NavBar() {
        //layout
        setWidth("100%");
        setHeight("100px");
        setJustifyContentMode(FlexComponent.JustifyContentMode.CENTER);
        setAlignItems(FlexComponent.Alignment.CENTER);
        getStyle().set("position", "fixed").set("bottom", "0").set("left", "0").set("right", "0");

        // User Button
        Button profileButton = new Button(new Icon(VaadinIcon.USER));
        profileButton.addClickListener(e -> {
            UI.getCurrent().navigate("profile");
            System.out.println("clicked -> profile");
        });

        // + button
        Button addButton = new Button(new Icon(VaadinIcon.PLUS));
        addButton.addClickListener(e -> {
            UI.getCurrent().navigate("load-documents");
            System.out.println("Clicked -> +");
        });

        // search button
        Button searchButton = new Button(new Icon(VaadinIcon.SEARCH));
        searchButton.addClickListener(e -> {
            UI.getCurrent().navigate("search-documents");
            System.out.println("Clicked -> Search");
        });

        // post button
        Button postsButton = new Button(new Icon(VaadinIcon.COMMENT));
        postsButton.addClickListener(e -> {
            UI.getCurrent().navigate("posts");
            System.out.println("Clicked -> Posts");
        });


        profileButton.getStyle().set("font-size", "24px");
        addButton.getStyle().set("font-size", "24px");
        searchButton.getStyle().set("font-size", "24px");
        postsButton.getStyle().set("font-size", "24px");

        profileButton.getStyle().set("width", "50px");
        profileButton.getStyle().set("height", "50px");
        postsButton.getStyle().set("width", "50px");
        postsButton.getStyle().set("height", "50px");
        addButton.getStyle().set("width", "50px");
        addButton.getStyle().set("height", "50px");
        searchButton.getStyle().set("width", "50px");
        searchButton.getStyle().set("height", "50px");

        add(profileButton, addButton, searchButton,postsButton);
    }
}
