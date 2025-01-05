package dev.uninotes.UniNotes;

import dev.uninotes.UniNotes.Database.DatabaseManager;

import java.util.ArrayList;

public class NotesManager {

    public ArrayList<Note> loadNotesOfCourse(String course, String username ){
        return DatabaseManager.SELECT_NOTES(course, username);
    }

}
