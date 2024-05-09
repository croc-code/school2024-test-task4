package org.letunov;

import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;
import org.letunov.core.EmployeeCommitReader;
import org.letunov.core.ScoreManager;
import org.letunov.domain.EmployeeCommit;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertIterableEquals;

public class EmployeeCommitReaderTest {
    @Test
    public void nextEmployeeCommitTest() {
        String text = """
                Jonathan 25ec012 2024-04-03T13:30:39.300
                Ramirez 25ec034 2024-04-12T17:06:39.200
                John 25ec035 2024-04-14T11:36:39.422
                """;
        List<EmployeeCommit> exampleList = new ArrayList<>();
        exampleList.add(new EmployeeCommit("Jonathan", "25ec012", "2024-04-03T13:30:39.300"));
        exampleList.add(new EmployeeCommit("Ramirez", "25ec034", "2024-04-12T17:06:39.200"));
        exampleList.add(new EmployeeCommit("John", "25ec035", "2024-04-14T11:36:39.422"));
        List<EmployeeCommit> resultList = new ArrayList<>();
        try (EmployeeCommitReader employeeCommitReader =
                     new EmployeeCommitReader(new StringReader(text))) {
            EmployeeCommit employeeCommit = employeeCommitReader.nextEmployeeCommit();
            while (employeeCommit != null) {
                resultList.add(employeeCommit);
                employeeCommit = employeeCommitReader.nextEmployeeCommit();
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        assertIterableEquals(exampleList, resultList);
    }

    @RepeatedTest(10)
    public void nextEmployeeCommitTest_2() {
        RandomEmployeeCommitGenerator randomEmployeeCommitGenerator = new RandomEmployeeCommitGenerator();
        Path path = Path.of(System.getProperty("user.dir"), "src", "test", "commits.txt");
        int commitsCount = 10000;
        ScoreManager scoreManager = new ScoreManager();
        List<EmployeeCommit> employeeCommits = randomEmployeeCommitGenerator.getEmployeeCommits(commitsCount);
        List<String> names = new ArrayList<>();
        names.add(randomEmployeeCommitGenerator.getRandomName());
        names.add(randomEmployeeCommitGenerator.getRandomName());
        names.add(randomEmployeeCommitGenerator.getRandomName());
        for (int i = 0; i <  names.size(); i++)
            for (int j = 0; j < commitsCount-i; j++)
                employeeCommits.add(new EmployeeCommit(names.get(i),
                        randomEmployeeCommitGenerator.getRandomReducedHash(),
                        randomEmployeeCommitGenerator.getRandomLocalDateTime()));
        Collections.shuffle(employeeCommits);

        try(BufferedWriter bufferedWriter = new BufferedWriter(
                new OutputStreamWriter(Files.newOutputStream(path)))) {
            for (EmployeeCommit employeeCommit : employeeCommits) {
                bufferedWriter.write(employeeCommit.toString());
                bufferedWriter.newLine();
                bufferedWriter.flush();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        List<EmployeeCommit> inputList = new ArrayList<>();
        try(EmployeeCommitReader employeeCommitReader
                    = new EmployeeCommitReader(
                            new InputStreamReader(Files.newInputStream(path)))) {
            EmployeeCommit employeeCommit = employeeCommitReader.nextEmployeeCommit();
            while (employeeCommit != null) {
                inputList.add(employeeCommit);
                employeeCommit = employeeCommitReader.nextEmployeeCommit();
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        for (EmployeeCommit employeeCommit : inputList)
            scoreManager.pushEmployeeCommit(employeeCommit);
        List<String> top3 = scoreManager.getTop(3);

        assertEquals(names, top3);
    }
}
