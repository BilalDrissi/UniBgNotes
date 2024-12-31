package dev.uninotes.UniNotes.Pages;

import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import dev.uninotes.UniNotes.Components.NotesComponent;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Route("notes")
public class NotesPage extends VerticalLayout {

    public NotesPage() {
        setPadding(true);
        setSpacing(true);
        setAlignItems(Alignment.CENTER);
        setWidth("100%");

        // Generate example notes
        List<NotesData> notesDataList = generateMockNotes();

        // Add notes components
        for (NotesData note : notesDataList) {
            add(new NotesComponent(
                    note.profileImageUrl,
                    note.nickname,
                    note.notesImageUrl,
                    note.course,
                    note.description,
                    note.publishedDate
            ));
        }
    }

    private List<NotesData> generateMockNotes() {
        List<NotesData> notes = new ArrayList<>();
        notes.add(new NotesData("https://via.placeholder.com/50", "User1", "https://via.placeholder.com/100x150", "Mathematics", "These are my notes for Algebra", LocalDateTime.now()));
        notes.add(new NotesData("https://via.placeholder.com/50", "User2", "https://via.placeholder.com/100x150", "Physics", "Notes about Mechanics", LocalDateTime.now().minusDays(1)));
        notes.add(new NotesData("https://via.placeholder.com/50", "User3", "https://via.placeholder.com/100x150", "Programming", "Java Basics notes", LocalDateTime.now().minusHours(5)));
        return notes;
    }

    private static class NotesData {
        String profileImageUrl;
        String nickname;
        String notesImageUrl;
        String course;
        String description;
        LocalDateTime publishedDate;

        public NotesData(String profileImageUrl, String nickname, String notesImageUrl, String course, String description, LocalDateTime publishedDate) {
            this.profileImageUrl = profileImageUrl;
            this.nickname = nickname;
            this.notesImageUrl = notesImageUrl;
            this.course = course;
            this.description = description;
            this.publishedDate = publishedDate;
        }
    }
}
