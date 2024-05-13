package school2024.test.task4.processing;

import school2024.test.task4.processing.exceptions.CommitFileProcessorException;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CommitFileProcessor {
    private static final Pattern COMMIT_PATTERN = Pattern.compile("^[a-zA-Z][a-zA-Z0-9_]+ " +
            "[a-z0-9]{7} [1-9][0-9]{3}-[0-9]{2}-[0-9]{2}T([0-9]{2}:){2}[0-9]{2}$");
    /*В данном методе:
    * считываются данные из файла в HashMap;
    * происходит первичная обработка данных с использованием регулярных выражений;
    * проверяется дата;
    * происходит избавление от дублирований строк входного файла;
    * финальное преобразование данных (см. следующий метод)
    * */
    public static Map<String, Integer> processInputFile(String filename) {
        Map<String, Integer> commits = new HashMap<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            Matcher matcher;
            while ((line = reader.readLine()) != null) {
                // проверка корректности вводимых данных с использованием RegEx
                matcher = COMMIT_PATTERN.matcher(line);
                if (matcher.find()) {
                    List<String> commitLine = List.of(line.split(" "));
                    String username = commitLine.get(0);
                    String hashCommit = commitLine.get(1);
                    String dateTime = commitLine.get(2);

                    // проверка даты: она не может быть в будущем времени
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
                    LocalDateTime commitDateTime = LocalDateTime.parse(dateTime, formatter);
                    LocalDateTime currentDateTime = LocalDateTime.now();
                    if (commitDateTime.isAfter(currentDateTime)) {
                        break;
                    }

                    // избавление от дублирования строк в данных
                    String key = username + " " + hashCommit;
                    commits.putIfAbsent(key, 0);
                    if (commits.get(key) == 0) {
                        commits.put(key, commits.get(key) + 1);
                    }
                }
            }
        } catch (IOException e) {
            throw new CommitFileProcessorException(e);
        }
        // преобразование данных
        commits = removeHashFromUsername(commits);
        return commits;
    }

    /*В данном методе:
    * удаляются побочные хэши
    * суммируется количество коммитов для каждого контрибьютера
    * */
    private static Map<String, Integer> removeHashFromUsername(Map<String, Integer> commits) {
        Map<String, Integer> result = new HashMap<>();
        Iterator<Map.Entry<String, Integer>> iterator = commits.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<String, Integer> entry = iterator.next();
            String usernameHash = entry.getKey();
            Integer count = entry.getValue();

            String[] parts = usernameHash.split(" ");
            String username = parts[0];

            result.putIfAbsent(username, 0);
            result.put(username, result.get(username) + count);
        }
        return result;
    }
}
