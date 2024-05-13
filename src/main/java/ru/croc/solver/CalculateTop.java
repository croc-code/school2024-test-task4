package ru.croc.solver;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class CalculateTop {

    public static List<String> calculateTopContributors(Map<String, Integer> contributors) {
        return contributors.entrySet().stream()
                .sorted((e1, e2) -> e2.getValue().compareTo(e1.getValue()))
                .limit(3)
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());
    }
}
