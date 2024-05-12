package org.example.task;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import lombok.SneakyThrows;
import org.example.task.service.Writer;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

public class WriterTest {

    private final String outputFile = "src/test/resources/result.txt";

    public String readFileToString() {
        try {
            // Считываем содержимое файла в виде массива байт
            byte[] bytes = Files.readAllBytes(Path.of(outputFile));

            // Преобразуем массив байт в строку, используя кодировку UTF-8
            return new String(bytes, StandardCharsets.UTF_8);
        } catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
            return null;
        }
    }

    public boolean compareFileContents(String expected, String actual) {
        String[] actualLines = expected.split("\n");
        String[] expectedLines = actual.split("\n");
        if (actualLines.length != expectedLines.length) {
            return false;
        }

        for (int i = 0; i < actualLines.length; i++) {
            String[] actualLineN = actualLines[i].split(" ");
            String[] expectedLineN = expectedLines[i].split(" ");
            if (actualLineN.length != expectedLineN.length) {
                return false;
            }
            if (!Set.of(actualLineN).equals(Set.of(expectedLineN))) {
                return false;
            }
        }
        return true;
    }

    @SneakyThrows
    @Test
    public void writeToFile_simpleData() {
        Map<Integer, Set<String>> topContributors = new HashMap<>();
        topContributors.put(1, Set.of("CIvanov"));
        topContributors.put(2, Set.of("BIvanov"));
        topContributors.put(3, Set.of("AIvanov"));
        String expectedTop = """
                CIvanov
                BIvanov
                AIvanov""";

        Writer.writeToFile(topContributors, outputFile);
        String actualTop = readFileToString();

        assertEquals(expectedTop, actualTop);
    }

    @SneakyThrows
    @Test
    public void writeToFile_whenTwoPersonsOnOnePosition() {
        Map<Integer, Set<String>> topContributors = new HashMap<>();
        topContributors.put(1, Set.of("CIvanov", "BIvanov"));
        topContributors.put(3, Set.of("AIvanov"));
        String expectedTop = """
                CIvanov BIvanov
                                
                AIvanov""";

        Writer.writeToFile(topContributors, outputFile);
        String actualTop = readFileToString();

        assertTrue(compareFileContents(expectedTop, actualTop));
    }

    @SneakyThrows
    @Test
    public void writeToFile_whenThreePersonsOnOnePosition() {
        Map<Integer, Set<String>> topContributors = new HashMap<>();
        topContributors.put(1, Set.of("CIvanov", "BIvanov", "DIvanov"));
        String expectedTop = """
                CIvanov BIvanov DIvanov
                                
                """;

        Writer.writeToFile(topContributors, outputFile);
        String actualTop = readFileToString();

        assertTrue(compareFileContents(expectedTop, actualTop));
    }

    @SneakyThrows
    @Test
    public void writeToFile_whenFourPersonInOnePosition() {
        Map<Integer, Set<String>> topContributors = new HashMap<>();
        topContributors.put(1, Set.of("CIvanov", "BIvanov", "AIvanov", "DIvanov"));
        String expectedTop = """
                CIvanov BIvanov DIvanov AIvanov
                                
                """;

        Writer.writeToFile(topContributors, outputFile);
        String actualTop = readFileToString();

        assertTrue(compareFileContents(expectedTop, actualTop));
    }

    @SneakyThrows
    @Test
    public void writeToFile_whenFourPersonInRating() {
        Map<Integer, Set<String>> topContributors = new HashMap<>();
        topContributors.put(1, Set.of("CIvanov", "AIvanov"));
        topContributors.put(3, Set.of("BIvanov", "DIvanov"));
        String expectedTop = """
                CIvanov AIvanov
                                
                BIvanov DIvanov""";

        Writer.writeToFile(topContributors, outputFile);
        String actualTop = readFileToString();

        assertTrue(compareFileContents(expectedTop, actualTop));
    }

    @SneakyThrows
    @Test
    public void writeToFile_whenAFewContributorsAndDifferentCount() {
        Map<Integer, Set<String>> topContributors = new HashMap<>();
        topContributors.put(1, Set.of("AIvanov"));
        topContributors.put(2, Set.of("BIvanov"));
        String expectedTop = """
                AIvanov
                BIvanov
                """;

        Writer.writeToFile(topContributors, outputFile);
        String actualTop = readFileToString();

        assertTrue(compareFileContents(expectedTop, actualTop));
    }

    @SneakyThrows
    @Test
    public void writeToFile_whenAFewContributorsAndSameCount() {
        Map<Integer, Set<String>> topContributors = new HashMap<>();
        topContributors.put(1, Set.of("AIvanov", "BIvanov"));
        String expectedTop = """
                AIvanov BIvanov
                                
                """;

        Writer.writeToFile(topContributors, outputFile);
        String actualTop = readFileToString();

        assertTrue(compareFileContents(expectedTop, actualTop));
    }
}
