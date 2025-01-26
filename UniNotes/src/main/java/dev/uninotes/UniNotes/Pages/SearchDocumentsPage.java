package dev.uninotes.UniNotes.Pages;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import dev.uninotes.UniNotes.Components.NavBar;
import dev.uninotes.UniNotes.Components.NotesComponent;
import dev.uninotes.UniNotes.Database.DatabaseManager;
import dev.uninotes.UniNotes.Note;
import dev.uninotes.UniNotes.SearchDocumetsManager;
import dev.uninotes.UniNotes.Utils.Utils;

import java.util.List;
import java.util.Map;

@Route("search-documents")
public class SearchDocumentsPage extends VerticalLayout {

    private VerticalLayout resultsLayout;

    public SearchDocumentsPage() {
        add(new NavBar());

        ComboBox<String> typeComboBox = new ComboBox<>("Notes Type");
        typeComboBox.setPlaceholder("Select a note type");
        typeComboBox.setItems(Utils.loadNoteTypes());
        typeComboBox.setWidth("300px");
        typeComboBox.setAllowCustomValue(false);

        ComboBox<String> fieldOfStudyComboBox = new ComboBox<>("Field of Study");
        fieldOfStudyComboBox.setPlaceholder("Select field of study...");
        fieldOfStudyComboBox.setItems(SearchDocumetsManager.loadFaculties());
        fieldOfStudyComboBox.setWidth("300px");
        fieldOfStudyComboBox.setAllowCustomValue(false);

        ComboBox<String> courseComboBox = new ComboBox<>("Course");
        courseComboBox.setPlaceholder("Select course...");
        courseComboBox.setWidth("300px");
        courseComboBox.setAllowCustomValue(false);
        courseComboBox.setVisible(false);

        ComboBox<String> userComboBox = new ComboBox<>("Uploaded By");
        userComboBox.setPlaceholder("Select user...");
        List<String> usernames = SearchDocumetsManager.loadUsernames();
        userComboBox.setItems(usernames);
        userComboBox.setWidth("300px");
        userComboBox.setAllowCustomValue(false);

        fieldOfStudyComboBox.addValueChangeListener(event -> {
            String selectedField = event.getValue();
            if (selectedField != null) {
                Map<String, List<String>> coursesByField = SearchDocumetsManager.loadCourses(selectedField);
                List<String> courses = coursesByField.getOrDefault(selectedField, List.of());
                if (!courses.isEmpty()) {
                    courseComboBox.setItems(courses);
                    courseComboBox.setVisible(true);
                } else {
                    courseComboBox.setItems();
                    courseComboBox.setVisible(false);
                }
            } else {
                courseComboBox.setVisible(false);
            }
        });

        HorizontalLayout firstRow = new HorizontalLayout(userComboBox, typeComboBox, fieldOfStudyComboBox, courseComboBox);
        firstRow.setWidthFull();
        firstRow.setJustifyContentMode(JustifyContentMode.CENTER);
        setDefaultHorizontalComponentAlignment(Alignment.CENTER);
        add(firstRow);

        resultsLayout = new VerticalLayout();
        resultsLayout.setWidth("70%");
        resultsLayout.setAlignItems(Alignment.CENTER);

        Button searchButton = new Button("Search");
        searchButton.setWidth("150px");
        searchButton.addClickListener(e -> {
            String fieldOfStudy = fieldOfStudyComboBox.getValue();
            String course = courseComboBox.isVisible() ? courseComboBox.getValue() : null;
            String user = userComboBox.getValue();
            String type = typeComboBox.getValue();
            resultsLayout.removeAll();
            loadNotes(fieldOfStudy, course, user, type);
        });

        Button clearButton = new Button("Clear");
        clearButton.setWidth("150px");
        clearButton.addClickListener(e -> {
            userComboBox.clear();
            fieldOfStudyComboBox.clear();
            courseComboBox.clear();
            courseComboBox.setVisible(false);
            typeComboBox.clear();
            resultsLayout.removeAll();
        });

        HorizontalLayout buttonRow = new HorizontalLayout(searchButton, clearButton);
        buttonRow.setJustifyContentMode(JustifyContentMode.CENTER);
        buttonRow.setWidthFull();

        add(buttonRow);
        add(resultsLayout);
    }

    private void loadNotes(String field, String course, String username, String type) {
        List<Note> notes = DatabaseManager.SELECT_NOTES(field, course, username, type);
        for (Note note : notes) {
            resultsLayout.add(new NotesComponent(note));
        }
    }
}
