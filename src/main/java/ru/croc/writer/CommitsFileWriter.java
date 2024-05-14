package ru.croc.writer;

import ru.croc.exceptions.WritingException;

import java.io.PrintWriter;
import java.util.List;

public class CommitsFileWriter {

    private static final String FILE_PATH = "src/main/resources/result.txt";

    public static void writeToFile(List<String> topContributors) {
        try (PrintWriter writer = new PrintWriter(FILE_PATH)) {
            topContributors.forEach(writer::println);
        } catch (Exception e) {
            throw new WritingException("WritingException: " + e.getMessage());
        }
    }
}
