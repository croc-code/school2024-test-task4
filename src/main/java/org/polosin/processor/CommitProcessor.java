package org.polosin.processor;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class CommitProcessor {
    private static final String TARGET_FILENAME;

    static {
        ResourceBundle resourceBundle = ResourceBundle.getBundle("config");
        TARGET_FILENAME = resourceBundle.getString("resultFilename");
    }

    private static boolean validateUsername(String username) {
        return username.matches("[a-zA-Z_][a-zA-Z0-9_]*");
    }

    private static boolean validateHash(String hash) {
        return hash.matches("[a-z0-9]{7}");
    }

    private static boolean validateDate(String date) {
        return date.matches("\\d{4}-\\d{2}-\\d{2}T\\d{2}:\\d{2}:\\d{2}.*\\d*");
    }

    /**
     * Формирует итоговый список, включающий не более трех лучших контрибьютеров.
     * Если какая-то строчка не удволетвеоряет требованиям(неправилный хэш, имя пользователя или дата), она игнорируется.
     *
     * @apiNote Пусть количество коммитов в исходном файле n. Тогда асимтотика есть O(n)
     * @param lines  считанные данные из файла
     * @return  итоговый список
     */
    public static List<String> formRating(Stream<String> lines) {
        var contributesStatistics = lines
                .map(x -> x.split(" "))
                .filter(splitLine -> {
                    if (splitLine.length != 3) return false;
                    return validateUsername(splitLine[0])
                            && validateHash(splitLine[1])
                            && validateDate(splitLine[2]);
                })
                .collect(Collectors.groupingBy(x -> x[0], Collectors.counting())).entrySet();
        ArrayList<Map.Entry<String, Long>> ratingList = new ArrayList<>(List.of(
                Map.entry("undefined", -1L),
                Map.entry("undefined", -1L),
                Map.entry("undefined", -1L)));
        contributesStatistics.forEach(item -> {
            var currentValue = item.getValue();
            if (currentValue >= ratingList.get(0).getValue()) {
                var prevFirstValue = ratingList.get(0);
                var prevSecondValue = ratingList.get(1);
                ratingList.set(0, item);
                ratingList.set(1, prevFirstValue);
                ratingList.set(2, prevSecondValue);
            } else if (currentValue >= ratingList.get(1).getValue()) {
                var prevSecondValue = ratingList.get(1);
                ratingList.set(1, item);
                ratingList.set(2, prevSecondValue);
            } else if (currentValue >= ratingList.get(2).getValue()) {
                ratingList.set(2, item);
            }
        });

        return ratingList.stream().filter(x -> x.getValue() != -1).map(Map.Entry::getKey).toList();
    }

    /**
     * Записывает в файл, находящийся по пути TARGET_FILENAME, топ-3 контрибьютеров.
     * Если контрибьютеров менее трех, записывает имеющихся.
     *
     *
     * @param sourceFileName путь до файла с данными
     * @throws  IOException  в случае I/O ошибок при открытии файла/записи или создании файла.
     */
    public static void getTopThreeContributes(String sourceFileName) throws IOException {
        Stream<String> lines = Files.lines(Path.of(sourceFileName), StandardCharsets.UTF_8);
        var outputRating = formRating(lines);
        Files.write(Path.of(TARGET_FILENAME), outputRating, StandardCharsets.UTF_8);
    }

    public static String getTargetFilename() {
        return TARGET_FILENAME;
    }
}
