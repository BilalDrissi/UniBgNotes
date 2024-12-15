package dev.uninotes.UniNotes;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import dev.uninotes.UniNotes.Components.NavBar;

@Route("search-documents")
public class SearchDocumentsPage extends VerticalLayout {

    public SearchDocumentsPage() {
        add(new NavBar());

        // Page Title
        TextField courseNameField = new TextField("Search by course name");
        courseNameField.setPlaceholder("Enter course name...");
        courseNameField.setWidth("300px");

        // ComboBox for Field of Study
        ComboBox<String> fieldOfStudyComboBox = new ComboBox<>("Field of Study");
        fieldOfStudyComboBox.setPlaceholder("Select field of study...");
        fieldOfStudyComboBox.setItems("Computer Science", "Engineering", "Mathematics", "Physics"); // Example items
        fieldOfStudyComboBox.setWidth("300px");
        fieldOfStudyComboBox.setAllowCustomValue(false);

        // ComboBox for Year
        ComboBox<String> yearComboBox = new ComboBox<>("Year");
        yearComboBox.setPlaceholder("Select year...");
        yearComboBox.setItems("2021", "2022", "2023", "2024"); // Example years
        yearComboBox.setWidth("300px");
        yearComboBox.setAllowCustomValue(false);

        // ComboBox for Course
        ComboBox<String> courseComboBox = new ComboBox<>("Course");
        courseComboBox.setPlaceholder("Select course...");
        courseComboBox.setItems("Algorithms", "Data Structures", "Networks", "Databases"); // Example courses
        courseComboBox.setWidth("300px");
        courseComboBox.setAllowCustomValue(false);

        // ComboBox for User
        ComboBox<String> userComboBox = new ComboBox<>("Uploaded By");
        userComboBox.setPlaceholder("Select user...");
        userComboBox.setItems("user1", "user2", "user3", "user4"); // Example users
        userComboBox.setWidth("300px");
        userComboBox.setAllowCustomValue(false);

        // Search Button
        Button searchButton = new Button("Search");
        searchButton.setWidth("150px");

        // Layout for inputs (arranged horizontally, two rows if necessary)
        HorizontalLayout firstRow = new HorizontalLayout(courseNameField, fieldOfStudyComboBox);
        HorizontalLayout secondRow = new HorizontalLayout(yearComboBox, courseComboBox, userComboBox);
        firstRow.setWidthFull();
        secondRow.setWidthFull();

        // Center all components
        firstRow.setJustifyContentMode(JustifyContentMode.CENTER);
        secondRow.setJustifyContentMode(JustifyContentMode.CENTER);
        setDefaultHorizontalComponentAlignment(Alignment.CENTER);

        // Add components to the page
        add(firstRow, secondRow, searchButton);

        // Add search logic (dummy example for now)
        searchButton.addClickListener(e -> {
            String courseName = courseNameField.getValue();
            String fieldOfStudy = fieldOfStudyComboBox.getValue();
            String year = yearComboBox.getValue();
            String course = courseComboBox.getValue();
            String user = userComboBox.getValue();

            // Example action
            System.out.println("Searching for documents with the following filters:");
            System.out.println("Course Name: " + courseName);
            System.out.println("Field of Study: " + fieldOfStudy);
            System.out.println("Year: " + year);
            System.out.println("Course: " + course);
            System.out.println("Uploaded By: " + user);
        });
    }
}
