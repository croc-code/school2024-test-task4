package org.example.task.configuration;

import java.time.format.DateTimeFormatter;
import lombok.Getter;

@Getter
public class ApplicationConfig {

    /**
     * Источник данных
     */
    private final String inputFile = "src/main/resources/commits.txt";
    /**
     * Хранение результата
     */
    private final String outputFile = "src/main/resources/result.txt";

    /**
     * Количество призовых мест
     */
    private final int numOfPositionInRating = 3;

    /**
     * Регулярное выражение для валидации имени
     */
    private final String usernameRegex = "^[a-zA-Z_][a-zA-Z0-9_]*$";
    /**
     * Регулярное выражение для валидации хеша
     */
    private final String hashRegex = "^[a-z0-9]{7}$";
    /**
     * Формат даты времени коммита
     */
    private final DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS");


    /**
     * Период спринта
     */
    private final int periodDays = 28;


    /**
     * Количество слов во входной строке
     * "AIvanov 25ec001 2024-04-24T13:56:39.492"
     */
    private final int numberOfWordsPerLine = 3;
    /**
     * Индекс имени
     */
    private final int usernameIdx = 0;
    /**
     * Индекс хеша
     */
    private final int hashIdx = 1;
    /**
     * Индекс даты
     */
    private final int dateIdx = 2;

}
