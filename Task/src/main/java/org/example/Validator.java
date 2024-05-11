package org.example;

import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Validator {
    public String validateCommit(String commit) {
        String[] parts = commit.split(" ");
        if (parts.length != 3) {
            return "Некорректный формат строки - " + commit;
        }

        String username = parts[0];
        if (!isValidUsername(username)) {
            return "Некорректное имя пользователя - " + username;
        }

        String commitHash = parts[1];
        if (!isValidCommitHash(commitHash)) {
            return "Некорректный хэш коммита - " + commitHash;
        }

        String commitDateString = parts[2];
        if (!isValidCommitDate(commitDateString)) {
            return "Некорректная дата коммита - " + commitDateString;
        }

        return null; // Все проверки пройдены успешно
    }

    private boolean isValidCommitDate(String commitDateString) {
        try {
            LocalDateTime commitDateTime = LocalDateTime.parse(commitDateString);
            LocalDateTime currentDateTime = LocalDateTime.now().withHour(0).withMinute(0).withSecond(0);
            LocalDateTime fourWeeksAgo = currentDateTime.minusWeeks(4);
            return commitDateTime.isAfter(fourWeeksAgo) && commitDateTime.isBefore(LocalDateTime.now().withHour(0).withMinute(0).withSecond(0));
        } catch (DateTimeParseException e) {
            return false;
        }
    }

    private boolean isValidCommitHash(String commitHash) {
        Pattern pattern = Pattern.compile("[a-z0-9]{7}");
        Matcher matcher = pattern.matcher(commitHash);
        return matcher.matches();
    }

    private boolean isValidUsername(String username) {
        Pattern pattern = Pattern.compile("[a-zA-Z_][a-zA-Z0-9_]*");
        Matcher matcher = pattern.matcher(username);
        return matcher.matches();
    }
}

