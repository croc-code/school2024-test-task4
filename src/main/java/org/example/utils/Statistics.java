package org.example.utils;

import java.time.LocalDateTime;
import java.util.*;

public class Statistics {

    /**
     * Получает Map с информацией о коммитах и возвращает список из топ-3 пользователей
     * с наибольшим количеством коммитов.
     *
     * @param commits Map<String, List<Map.Entry<String, LocalDateTime>>> с информацией о коммитах
     * @return List<String> список из топ-3 пользователей
     */
    public static List<String> getTop3(Map<String, List<Map.Entry<String, LocalDateTime>>> commits) {

        // Создаем list из пар *имя пользователя* и *количество коммитов*
        List<Map.Entry<String, Integer>> pairs = commits.entrySet().stream()
                .map(element -> Map.entry(element.getKey(), element.getValue().size()))
                .toList();

        // Сортировка листа по убыванию количества коммитов
        pairs = pairs.stream()
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .toList();

        // Возвращает топ-3 имен с наибольшим количеством коммитов
        return pairs.stream()
                .map(Map.Entry::getKey)
                .toList()
                .subList(0, 3);
    }
}
