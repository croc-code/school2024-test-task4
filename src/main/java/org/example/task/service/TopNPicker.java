package org.example.task.service;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import lombok.experimental.UtilityClass;
import org.example.task.configuration.ApplicationConfig;


@UtilityClass
public class TopNPicker {

    private static final ApplicationConfig APPLICATION_CONFIG = new ApplicationConfig();

    /**
     * Этот метод принимает на вход Map, где key - имя контрибьютера, value - количество его коммитов и
     * формирует рейтинг контрибьютеров на основе количества их коммитов в несколько этапов.
     * 1. Сортировка по убыванию входных данных в {@code List<Map.Entry<...>>}
     * 2. Определение количества мест в рейтинге:
     * выбирается меньшее из заданного условием и общего количества контрибьютеров
     * 3. Запись в результат контрибьютеров из отсортированного набора на соответствующее количество мест рейтинга
     * 3.1. Формирование {@code Set<String>} имен контрибьютеров, набравших одинаковое количество баллов
     * 3.2. Запись в результат на текущую позицию в рейтинге этого набора
     * 3.3. Сдвиг позиции рейтинга на значение равное количеству считанных контрибьютеров
     * 3.4. Повтор пока позиция рейтинга не превысит требуемое значение или не закончатся контрибьютеры
     * <p>
     * Пример:
     * на входе: {{"AAA", 10}, {"BBB", 10}, {"CCC", 5}, {"DDD", 1}}
     * на выходе: {{1, ["AAA", "BBB"]}, {3, ["CCC"]}}
     * (Первое место займут пользователи AAA и BBB, второе пустое, третье - CCC)
     *
     * @param contributors Map, где key - имя контрибьютера, value - количество его коммитов
     * @return Map, где key - занимаемое место в рейтинге, value - Set имён контрибьютеров
     */
    public Map<Integer, Set<String>> pickTopContributors(Map<String, Integer> contributors) {
        Map<Integer, Set<String>> topContributors = new HashMap<>();

        List<Map.Entry<String, Integer>> sortedContributors = new LinkedList<>(contributors.entrySet());
        sortedContributors.sort((o1, o2) -> (o2.getValue()).compareTo(o1.getValue()));

        int numOfPositionInRating = Math.min(APPLICATION_CONFIG.getNumOfPositionInRating(), sortedContributors.size());

        int position = 1;
        while (position <= numOfPositionInRating) {
            int idx = position - 1;
            int currPosition = position;
            int score = sortedContributors.get(idx).getValue();
            Set<String> contributorsOnSamePosition = new HashSet<>();

            while (idx < sortedContributors.size() && sortedContributors.get(idx).getValue() == score) {
                contributorsOnSamePosition.add(sortedContributors.get(idx).getKey());
                idx++;
            }

            topContributors.put(currPosition, contributorsOnSamePosition);
            position = idx + 1;
        }

        return topContributors;
    }
}
