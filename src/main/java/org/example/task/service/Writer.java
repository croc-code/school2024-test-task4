package org.example.task.service;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;
import java.util.Set;
import lombok.SneakyThrows;
import lombok.experimental.UtilityClass;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.task.configuration.ApplicationConfig;


@UtilityClass
public class Writer {

    private static final Logger LOGGER = LogManager.getLogger();
    private static final ApplicationConfig APPLICATION_CONFIG = new ApplicationConfig();

    /**
     * Этот метод принимает на вход рейтинг контрибьютеров и путь к файлу для записи результата.
     * Если файл существует, он удаляется и создается новый. Затем формируется строка, где в каждой строке
     * либо 1 контрибьютер, либо их список записанный через пробел. Номер строки соответствует позиции в рейтинге.
     * Если на данной позиции в рейтинге никого нет, то строка будет пуста.
     *
     * @param topContributors Map, где key - занимаемое место в рейтинге, value - Set имён контрибьютеров
     * @param filePath        путь к файлу для записи результата
     */
    @SneakyThrows(IOException.class)
    public static void writeToFile(Map<Integer, Set<String>> topContributors, String filePath) {
        int numOfPositionInRating = APPLICATION_CONFIG.getNumOfPositionInRating();
        Path file = Path.of(filePath);

        if (Files.exists(file)) {
            Files.delete(file);
        }
        Files.createFile(file);

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            StringBuilder strContr = new StringBuilder();
            for (int currPosition = 1; currPosition <= numOfPositionInRating; currPosition++) {
                Set<String> contributors = topContributors.get(currPosition);

                if (contributors != null) {
                    for (String element : contributors) {
                        strContr.append(element).append(" ");
                    }
                    strContr.deleteCharAt(strContr.length() - 1);
                }
                strContr.append("\n");
            }
            strContr.deleteCharAt(strContr.length() - 1);
            writer.write(strContr.toString());
        } catch (IOException e) {
            LOGGER.error("Error writing to file: {}", e.getMessage());
        }

        LOGGER.info("File written successfully.");
    }
}
