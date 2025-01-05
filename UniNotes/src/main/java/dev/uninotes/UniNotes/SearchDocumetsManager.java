package dev.uninotes.UniNotes;

import dev.uninotes.UniNotes.Database.DatabaseManager;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class SearchDocumetsManager {

    public static List<String> loadUsernames(){
        return DatabaseManager.SELECT_USERNAMES();
    }

    public static List<String> loadFaculties(){
        return DatabaseManager.SELECT_FACULTIES_NAME();
    }


    public static Map<String, List<String>> loadCourses(String faculty){
        return DatabaseManager.SELECT_COURSES_BY_FACULTY(faculty);

    }




}
