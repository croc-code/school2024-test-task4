package org.example;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CommitParser {
    private static final Logger LOGGER = Logger.getLogger(CommitParser.class.getName());

    private String inputFileName;
    private String outputFileName;

    public CommitParser(String inputFileName, String outputFileName) {
        this.inputFileName = inputFileName;
        this.outputFileName = outputFileName;
    }

    public void parseFile() {
        FileFinder fileFinder = new FileFinder();
        Validator validator = new Validator();

        try {
            Map<String, User> contributorMap = new HashMap<>();
            // Поиск файла ввода во всех директориях
            // String inputFilePath = fileFinder.findFile(inputFileName);
            // Поиск файла вывода во всех директориях
            // String outputFilePath = fileFinder.findFile(outputFileName);
            // Пути к файлам равны самим файлам, если они лежат в каталоге с проектом
            String inputFilePath = inputFileName;
            String outputFilePath = outputFileName;
            // Очистка файла вывода перед началом записи новых данных
            Files.write(Paths.get(outputFilePath), new byte[0], StandardOpenOption.TRUNCATE_EXISTING);

            Files.lines(Paths.get(inputFilePath))
                    .forEach(line -> {
                        String validationResult = validator.validateCommit(line);
                        if (validationResult != null) {
                            LOGGER.warning(validationResult);
                            return;
                        }

                        String[] parts = line.split(" ");
                        String username = parts[0];
                        LocalDateTime commitDateTime = LocalDateTime.parse(parts[2]);

                        contributorMap.putIfAbsent(username, new User());
                        User user = contributorMap.get(username);
                        user.setUserName(username);
                        user.incrementCommitCount();
                        user.setLastCommitDate(commitDateTime);
                    });

            contributorMap.entrySet().stream()
                    .sorted((entry1, entry2) -> {
                        int compareByCommits = Integer.compare(entry2.getValue().getCommitCount(), entry1.getValue().getCommitCount());
                        if (compareByCommits == 0) {
                            return entry1.getValue().getLastCommitDate().compareTo(entry2.getValue().getLastCommitDate());                        }
                        return compareByCommits;
                    })
                    .limit(3)
                    .map(Map.Entry::getKey)
                    .forEachOrdered(username -> {
                        try {
                            Files.write(Paths.get(outputFilePath), (username + "\n").getBytes(), java.nio.file.StandardOpenOption.APPEND);
                        } catch (IOException e) {
                            LOGGER.log(Level.SEVERE, "Ошибка при записи в файл", e);
                        }
                    });

            LOGGER.info("Рейтинг успешно записан в файл " + outputFileName);
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Ошибка при чтении файла", e);
        }
    }
}

