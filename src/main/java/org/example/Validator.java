package src.main.java.org.example;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public abstract class Validator {
    public static boolean isValidAuthor(String username) {
        /*
        имя пользователя может содержать латинские символы
        в любом регистре, цифры (но не начинаться с них), а также символ "_";
         */
        return username.matches("[a-zA-Z_][a-zA-Z0-9_]*");
    }

    public static boolean isValidHash(String commitHash) {
        /*
        сокращенный хэш коммита представляет из себя строку в нижнем регистре,
        состояющую из 7 символов: букв латинского алфавита, а также цифр;
         */
        return commitHash.matches("[a-z0-9]{7}");
    }

    public static boolean isValidDate(String dateTime) {
        // дата и время коммита в формате YYYY-MM-ddTHH:mm:ss.
        String regex = "\\d{4}-\\d{2}-\\d{2}T\\d{2}:\\d{2}:\\d{2}\\.\\d+";
        return dateTime.matches(regex);
    }

    public static boolean isValidDateBounds(String dateTime) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS");
        LocalDateTime commitDateTime = LocalDateTime.parse(dateTime, formatter);
        LocalDateTime sprintEnd = LocalDateTime.now().minusDays(1); // Учитываем, что проверка происходит на следующий день после спринта
        LocalDateTime sprintStart = sprintEnd.minusDays(28); // Длительность спринта - 28 дней
        return commitDateTime.isAfter(sprintStart) && commitDateTime.isBefore(sprintEnd);
    }
}