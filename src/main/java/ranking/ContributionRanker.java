package ranking;

import commit.Commit;
import commit.validation.CommitValidator;

import java.util.*;

// Сортировка пользователей по количеству сделанных ими корректных коммитов
public class ContributionRanker {
    private ContributionRanker() {}

    public static List<String> getContributorsRanked(List<Commit> commits, int numberOfAwardees) {
        removeInvalidCommits(commits);

        Map<String, Integer> results = getResults(commits);

        if (results.size() < numberOfAwardees) {
            System.out.println("Количество участников меньше заданного количества призеров");
        }

        return rankContributors(results);
    }

    // Исключение некорректных коммитов
    private static void removeInvalidCommits(List<Commit> commits) {
        commits.removeIf(commit -> !CommitValidator.isValid(commit));
    }

    // Создание словаря <Пользователь - количество коммитов> для дальнейшей сортировки
    private static Map<String, Integer> getResults(List<Commit> commits) {
        Map<String, Integer> results = new HashMap<>();

        commits.forEach(commit -> {
            String name = commit.username();
            if (!results.containsKey(name)) {
                results.put(name, 1);
            } else {
                results.replace(name, results.get(name) + 1);
            }
        });

        return results;
    }

    // Сортировка пользователей по количеству коммитов
    private static List<String> rankContributors(Map<String, Integer> results) {
        List<String> contributorsRanked = new ArrayList<>();

        results.entrySet().stream()
                .sorted(Map.Entry.comparingByValue((Comparator.reverseOrder())))
                .forEach(entry -> contributorsRanked.add(entry.getKey()));

        return contributorsRanked;
    }
}
