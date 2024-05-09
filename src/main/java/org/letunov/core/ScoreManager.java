package org.letunov.core;

import org.letunov.domain.EmployeeCommit;

import java.util.ArrayList;
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
        List<String> result = new ArrayList<>();
        List<Map.Entry<String, Integer>> entries = employees.entrySet().stream().sorted((x, y) -> y.getValue() - x.getValue())
                .toList();
        int place = 1;
        int prev = -1;
        for (Map.Entry<String, Integer> entry : entries) {
            if (prev != entry.getValue() && place == topNumber + 1)
                break;
            else if (prev != entry.getValue()) {
                place++;
                prev = entry.getValue();
                result.add(entry.getKey());
            } else {
                result.add(entry.getKey());
            }
        }
        return result;
    }
}
