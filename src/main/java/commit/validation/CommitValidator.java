package commit.validation;

import commit.Commit;

import java.time.DateTimeException;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.regex.Pattern;

// Проверка переданных данных о коммите на корректность в соответствии с предъявленными требованиями
public class CommitValidator {
    // Регулярные выражения для проверки соответствия данных требованиям
    private static final Pattern NAME_PATTERN = Pattern.compile("^[a-zA-Z_][a-zA-Z0-9_]*$");
    private static final Pattern HASH_PATTERN = Pattern.compile("^[a-z0-9]{7}$");
    private static final Pattern DATE_PATTERN = Pattern.compile("\\d{4}-\\d{2}-\\d{2}T\\d{2}:\\d{2}:\\d{2}\\.\\d{0,9}");

    private CommitValidator() {}

    // Проверка корректности переданных данных о коммите
    public static boolean isValid(Commit commit) {
        return validateUsername(commit.username()) && validateHash(commit.hash()) && validateDate(commit.date());
    }

    // Проверка корректности переданного имени пользователя
    private static boolean validateUsername(String username) {
        if (username == null || username.isEmpty()) {
            return false;
        }

        return NAME_PATTERN.matcher(username).matches();
    }

    // Проверка корректности переданного хэш-кода коммита
    private static boolean validateHash(String hash) {
        if (hash == null || hash.isEmpty()) {
            return false;
        }

        return HASH_PATTERN.matcher(hash).matches();
    }

    // Проверка корректности переданных даты и времени
    private static boolean validateDate(String date) {
        // Проверка на соответствие паттерну и отсутствие данных
        if (date == null || date.isEmpty()) {
            return false;
        } else if (!DATE_PATTERN.matcher(date).matches()) {
            return false;
        }

        // Приведение даты и времени к массиву целочисленных значений
        String[] parts = date.split("[.\\-T:]");
        int[] intParts = Arrays.stream(parts)
                .mapToInt(Integer::parseInt)
                .toArray();

        // Проверка на соответствие допустимым значениям (не более 24 часов, не более 60 минут и т.д.)
        try {
            LocalDateTime.of(
                    intParts[0],
                    intParts[1],
                    intParts[2],
                    intParts[3],
                    intParts[4],
                    intParts[5],
                    intParts[6]);

            return true;
        } catch (DateTimeException e) {
            return false;
        }
    }
}
