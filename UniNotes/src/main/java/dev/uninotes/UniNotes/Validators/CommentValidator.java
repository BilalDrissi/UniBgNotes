package dev.uninotes.UniNotes.Validators;

import dev.uninotes.UniNotes.Exceptions.MaximumLengthException;

public class CommentValidator {
    private static final int MAX_LENGTH = 256;

    public static void validateComment(String comment) {
        if (comment != null && comment.length() > MAX_LENGTH) {
            throw new MaximumLengthException("The comment exceeds the maximum length of " + MAX_LENGTH + " characters.");
        }
    }
}
