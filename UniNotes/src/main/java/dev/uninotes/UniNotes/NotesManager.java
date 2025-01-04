package dev.uninotes.UniNotes;

import dev.uninotes.UniNotes.Database.DatabaseManager;

import java.util.ArrayList;

public class NotesManager {

    public ArrayList<Note> loadNotes(String course, String field, String year, String username ){
        return DatabaseManager.SELECT_NOTES(course, field, year, username);
    }

}
