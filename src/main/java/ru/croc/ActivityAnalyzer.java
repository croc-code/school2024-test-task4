package ru.croc;

import java.util.*;

/**
 * Класс для анализа активности пользователей по количеству коммитов.
 */
public class ActivityAnalyzer {

    /**
     * Находит три пользователя с наибольшим количеством коммитов.
     *
     * @param commitCounts Словарь, ключ: имя пользователя, значение: количество их коммитов.
     * @return Список имен трех пользователей с наибольшим количеством коммитов, в порядке убывания.
     */
    public static List<String> findTopThreeUsersWithMostCommits(Map<String, Integer> commitCounts) {
        List<String> users = new ArrayList<>();

        // Создаем копию Map для сортировки по значению (количеству коммитов)
        Map<String, Integer> sortedMap = new LinkedHashMap<>();
        commitCounts.entrySet().stream()
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .forEachOrdered(entry -> sortedMap.put(entry.getKey(), entry.getValue()));

        // Выбираем первые три записи (пользователей с наибольшим количеством коммитов)
        int count = 0;
        for (String user : sortedMap.keySet()) {
            users.add(user);
            count++;
            if (count == 3) break;
        }

        return users;
    }
}
