package org.example.task.validation;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.regex.Pattern;
import lombok.experimental.UtilityClass;
import org.example.task.configuration.ApplicationConfig;

@UtilityClass
public class Validator {

    private static final ApplicationConfig APPLICATION_CONFIG = new ApplicationConfig();

    /**
     * Этот метод проверяет, является ли количество слов в строке валидным
     *
     * @param line массив строк, представляющий строку данных
     * @return {@code true}, если строка данных валидна, иначе {@code false}
     */
    public boolean isLineValid(String[] line) {
        return line.length == APPLICATION_CONFIG.getNumberOfWordsPerLine();
    }

    /**
     * Этот метод проверяет, является ли имя контрибьютера валидным
     *
     * @param username имя пользователя
     * @return {@code true}, если строка данных валидна, иначе {@code false}
     */
    public boolean isUsernameValid(String username) {
        return Pattern.matches(APPLICATION_CONFIG.getUsernameRegex(), username);
    }

    /**
     * Этот метод проверяет, является ли имя коммита валидным
     *
     * @param hash хеш коммита
     * @return {@code true}, если строка данных валидна, иначе {@code false}
     */
    public boolean isHashValid(String hash) {
        return Pattern.matches(APPLICATION_CONFIG.getHashRegex(), hash);
    }

    /**
     * Этот метод проверяет, является ли дата коммита валидной.
     * Валидная дата: Текущая дата - Период спринта - 1 < Дата коммита < Текущая дата
     * Так как программа запускается на следующий день
     *
     * @param date дата коммита
     * @return {@code true}, если строка данных валидна, иначе {@code false}
     */
    public boolean isDateValid(String date) {
        try {
            LocalDateTime currDateTime = LocalDateTime.parse(date, APPLICATION_CONFIG.getDateFormat());
            LocalDate currDate = currDateTime.toLocalDate();
            LocalDate dateOfStartSprint = LocalDate.now().minusDays(APPLICATION_CONFIG.getPeriodDays() + 1);
            LocalDate dateOfEndSprint = LocalDate.now();
            return currDate.isBefore(dateOfEndSprint) && currDate.isAfter(dateOfStartSprint);
        } catch (DateTimeParseException e) {
            return false;
        }
    }
}
