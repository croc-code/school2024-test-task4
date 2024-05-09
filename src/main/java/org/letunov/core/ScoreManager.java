package org.letunov.core;

import org.letunov.domain.EmployeeCommit;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Класс для формирования топа сотрудников
 */
public class ScoreManager {
    private final Map<String, Integer> employees = new HashMap<>();

    /**
     * Конструктор по-умолчанию
     */
    public ScoreManager() {}

    /**
     * Позволяет положить очередной коммит сотрудника, учавствующего в соревновании.
     * @param employeeCommit очередной коммит сотрудника
     */
    public void pushEmployeeCommit(EmployeeCommit employeeCommit) {
        employees.merge(employeeCommit.getName(),1, Integer::sum);
    }

    /**
     * Возвращает список наиболее активных сотрудников по числу коммитов,
     * начиная с наибольшего числа.
     * @param topNumber размер топа, который необходимо сформировать
     * @return список наиболее активных сотрудников
     */
    public List<String> getTop(int topNumber) {
        return employees.entrySet().stream().sorted((x,y) -> y.getValue() - x.getValue())
                .limit(topNumber).map(Map.Entry::getKey).toList();
    }
}
