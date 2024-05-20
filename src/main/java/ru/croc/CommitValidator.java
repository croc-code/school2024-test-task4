package ru.croc;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Класс для валидации данных о коммитах.
 */
public class CommitValidator {
    // Регулярные выражения для проверки формата имени, хэша и даты
    private static final String USERNAME_REGEX = "^[a-zA-Z_][a-zA-Z0-9_]*$";
    private static final String COMMIT_HASH_REGEX = "^[a-zA-Z0-9]{7}$";
    private static final String DATE_REGEX = "^\\d{4}-\\d{2}-\\d{2}T\\d{2}:\\d{2}:\\d{2}(\\.\\d+)?$";

    // Шаблоны для компиляции регулярных выражений
    private static final Pattern USERNAME_PATTERN = Pattern.compile(USERNAME_REGEX);
    private static final Pattern COMMIT_HASH_PATTERN = Pattern.compile(COMMIT_HASH_REGEX);
    private static final Pattern DATE_PATTERN = Pattern.compile(DATE_REGEX);

    /**
     * Проверяет валидность имени пользователя.
     *
     * @param username Имя пользователя для проверки.
     * @return true, если имя пользователя валидно, иначе false.
     */
    private static boolean isValidUsername(String username) {
        Matcher matcher = USERNAME_PATTERN.matcher(username);
        return matcher.matches();
    }

    /**
     * Проверяет валидность хэша коммита.
     *
     * @param commitHash Хэш коммита для проверки.
     * @return true, если хэш коммита валиден, иначе false.
     */
    private static boolean isValidCommitHash(String commitHash) {
        Matcher matcher = COMMIT_HASH_PATTERN.matcher(commitHash);
        return matcher.matches();
    }

    /**
     * Проверяет валидность формата даты.
     *
     * @param date Дата для проверки.
     * @return true, если формат даты валиден, иначе false.
     */
    private static boolean isValidDate(String date) {
        Matcher matcher = DATE_PATTERN.matcher(date);
        return matcher.matches();
    }

    /**
     * Проверяет валидность строки данных о коммите.
     *
     * @param username Имя пользователя.
     * @param commitHash Хэш коммита.
     * @param date Дата коммита.
     * @return true, если данные о коммите валидны, иначе false.
     */
    public static boolean isValidCommitInfo(String username, String commitHash, String date) {
        return isValidUsername(username) && isValidCommitHash(commitHash)
                && isValidDate(date);
    }
}
