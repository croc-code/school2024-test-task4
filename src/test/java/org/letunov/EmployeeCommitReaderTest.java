package org.letunov;

import org.junit.jupiter.api.Test;
import org.letunov.core.EmployeeCommitReader;
import org.letunov.domain.EmployeeCommit;

import java.io.Reader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

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
}
