package ru.croc.reader;

import ru.croc.exceptions.ReadingException;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class CommitsFileReader {

    private static final String FILE_PATH = "src/main/resources/commits.txt";

    public static Map<String, Integer> readFromResources() {
        File file = new File(FILE_PATH);
        if (!file.exists()) {
            throw new RuntimeException("check file: " + FILE_PATH);
        }

        Map<String, Integer> contributors = new HashMap<>();
        try (Scanner scanner = new Scanner(file)) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                if (!CommitValidator.validateLine(line)) {
                    System.out.println("invalid line: [" + line + "]. check format or ensure that commit is no older than 4 weeks.");
                    continue;
                }

                String username = line.split(" ")[0];
                contributors.put(username, contributors.getOrDefault(username, 0) + 1);
            }
        } catch (Exception e) {
            throw new ReadingException("ReadingException: " + e.getMessage());
        }

        return contributors;
    }
}
