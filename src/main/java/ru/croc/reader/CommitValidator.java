package ru.croc.reader;

import java.time.LocalDateTime;
import java.util.regex.Pattern;

class CommitValidator {

    private static final Pattern USERNAME_PATTERN = Pattern.compile("^[a-zA-Z_][a-zA-Z0-9_]*$");
    private static final Pattern COMMIT_HASH_PATTERN = Pattern.compile("^[a-z0-9]{7}$");
    private static final Pattern DATETIME_PATTERN = Pattern.compile("^\\d{4}-\\d{2}-\\d{2}T\\d{2}:\\d{2}:\\d{2}$");

    public static boolean validateLine(String line) {
        String[] parts = line.split(" ");
        if (parts.length != 3) {
            return false;
        }

        if (parts[1].length() != 7) {
            return false;
        }

        try {
            LocalDateTime commitDate = LocalDateTime.parse(parts[2]);
            if (commitDate.isAfter(LocalDateTime.now())) {
                return false;
            }
            if (commitDate.isBefore(LocalDateTime.now().minusWeeks(4))) {
                return false;
            }
        } catch (Exception e) {
            return false;
        }

        return isValidUsername(parts[0]) && isValidCommitHash(parts[1]) && isValidDatetime(parts[2]);
    }

    private static boolean isValidUsername(String username) {
        return USERNAME_PATTERN.matcher(username).matches();
    }

    private static boolean isValidCommitHash(String commitHash) {
        return COMMIT_HASH_PATTERN.matcher(commitHash).matches();
    }

    private static boolean isValidDatetime(String datetime) {
        return DATETIME_PATTERN.matcher(datetime).matches();
    }
}
