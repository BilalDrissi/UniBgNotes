package dev.uninotes.UniNotes.Manager;


import dev.uninotes.UniNotes.Utils.Utils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

public class FileManager {

    public static String downloadNote(int idNote, String notePath) {

        //the path is set by default because vaadin does not permit to interact with os
        File target = new File(Utils.getDownloadsFolder().toFile(), "Note_" + idNote);
        File source = Paths.get("src/main/resources/static/notes", String.valueOf(idNote)).toFile();

        try {
            copyFolder(source, target);
            return "Downloaded Note in: " + target;
        } catch (IOException ex) {
            return "Error downloading note: " + ex.getMessage();
        }

    }

    private static void copyFolder(File source, File target) throws IOException {
        if (!target.exists()) {
            target.mkdirs();
        }

        for (File file : source.listFiles()) {
            File targetFile = new File(target, file.getName());
            if (file.isDirectory()) {
                copyFolder(file, targetFile);
            } else {
                Files.copy(file.toPath(), targetFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
            }
        }
    }


}
