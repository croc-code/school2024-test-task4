package org.letunov.core;

import org.letunov.domain.EmployeeCommit;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ScoreManager {
    private final Map<String, Integer> employees = new HashMap<>();
    public void pushEmployeeCommit(EmployeeCommit employeeCommit) {
        employees.merge(employeeCommit.getName(),1, Integer::sum);
    }
    public List<String> getTop(int topNumber) {
        return employees.entrySet().stream().sorted((x, y) -> y.getValue() - x.getValue())
                .limit(topNumber).map(Map.Entry::getKey).collect(Collectors.toList());
    }
}
