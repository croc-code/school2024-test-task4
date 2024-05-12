package org.example.task;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.HashMap;
import org.example.task.service.Reader;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

public class ReaderTest {

    private final String validInputFile = "src/test/resources/valid-commits-test.txt";
    private final String invalidNameInputFile = "src/test/resources/invalid-name-commits-test.txt";
    private final String invalidHashInputFile = "src/test/resources/invalid-hash-commits-test.txt";
    private final String invalidDateInputFile = "src/test/resources/invalid-date-commits-test.txt";

    @Test
    public void readFileTest_whenValid() {
        System.out.println();
        HashMap<String, Integer> expectedContributors = new HashMap<>();
        expectedContributors.put("AIvanov", 2);
        expectedContributors.put("BIvanov", 2);
        expectedContributors.put("CIvanov", 1);
        expectedContributors.put("DIvanov", 1);

        HashMap<String, Integer> actualContributors = Reader.readContributors(validInputFile);

        assertEquals(expectedContributors, actualContributors);
    }

    @Test
    public void readFileTest_whenInvalidName() {
        System.out.println();
        HashMap<String, Integer> expectedContributors = new HashMap<>();
        expectedContributors.put("AIvanov", 2);
        expectedContributors.put("CIvanov", 1);
        expectedContributors.put("DIvanov", 1);

        HashMap<String, Integer> actualContributors = Reader.readContributors(invalidNameInputFile);

        assertEquals(expectedContributors, actualContributors);
    }

    @Test
    public void readFileTest_whenInvalidHash() {
        System.out.println(Duration.between(LocalDateTime.now(), LocalDateTime.now().plusDays(20)));

        System.out.println(Duration.between(LocalDateTime.now().plusDays(20), LocalDateTime.now()));

        System.out.println();
        HashMap<String, Integer> expectedContributors = new HashMap<>();
        expectedContributors.put("AIvanov", 2);
        expectedContributors.put("CIvanov", 1);

        HashMap<String, Integer> actualContributors = Reader.readContributors(invalidHashInputFile);

        assertEquals(expectedContributors, actualContributors);
    }

    @Test
    public void readFileTest_whenInvalidDate() {
        System.out.println(Duration.between(LocalDateTime.now(), LocalDateTime.now().plusDays(20)));

        System.out.println(Duration.between(LocalDateTime.now().plusDays(20), LocalDateTime.now()));

        System.out.println();

        HashMap<String, Integer> actualContributors = Reader.readContributors(invalidDateInputFile);

        assertTrue(actualContributors.isEmpty());
    }
}
