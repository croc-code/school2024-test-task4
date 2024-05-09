package org.letunov;

import org.junit.jupiter.api.Test;
import org.letunov.core.ScoreManager;
import org.letunov.domain.EmployeeCommit;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertIterableEquals;

public class ScoreManagerTest {
    private final RandomEmployeeCommitGenerator randomEmployeeCommitGenerator = new RandomEmployeeCommitGenerator();

    @Test
    public void getTopTest() {
        List<EmployeeCommit> employeeCommits = new ArrayList<>();
        String name1 = randomEmployeeCommitGenerator.getRandomName();
        String name2 = randomEmployeeCommitGenerator.getRandomName();
        String name3 = randomEmployeeCommitGenerator.getRandomName();
        String name4 = randomEmployeeCommitGenerator.getRandomName();
        String name5 = randomEmployeeCommitGenerator.getRandomName();
        for (int i = 0; i < 2; i++)
            employeeCommits.add(new EmployeeCommit(name1,
                    randomEmployeeCommitGenerator.getRandomReducedHash(),
                    randomEmployeeCommitGenerator.getRandomLocalDateTime()));
        for (int i = 0; i < 4; i++)
            employeeCommits.add(new EmployeeCommit(name2,
                    randomEmployeeCommitGenerator.getRandomReducedHash(),
                    randomEmployeeCommitGenerator.getRandomLocalDateTime()));
        for (int i = 0; i < 3; i++)
            employeeCommits.add(new EmployeeCommit(name3,
                    randomEmployeeCommitGenerator.getRandomReducedHash(),
                    randomEmployeeCommitGenerator.getRandomLocalDateTime()));
        for (int i = 0; i < 12; i++)
            employeeCommits.add(new EmployeeCommit(name4,
                    randomEmployeeCommitGenerator.getRandomReducedHash(),
                    randomEmployeeCommitGenerator.getRandomLocalDateTime()));
        for (int i = 0; i < 20; i++)
            employeeCommits.add(new EmployeeCommit(name5,
                    randomEmployeeCommitGenerator.getRandomReducedHash(),
                    randomEmployeeCommitGenerator.getRandomLocalDateTime()));
        ScoreManager scoreManager = new ScoreManager();
        for (EmployeeCommit employeeCommit : employeeCommits)
            scoreManager.pushEmployeeCommit(employeeCommit);
        List<String> top3 = new ArrayList<>();
        top3.add(name5);
        top3.add(name4);
        top3.add(name2);
        assertIterableEquals(top3, scoreManager.getTop(3));

        scoreManager.pushEmployeeCommit(new EmployeeCommit(name3,
                randomEmployeeCommitGenerator.getRandomReducedHash(),
                randomEmployeeCommitGenerator.getRandomLocalDateTime()));

        assertEquals(4, scoreManager.getTop(3).size());
    }

    @Test
    public void df() {
        ScoreManager scoreManager = new ScoreManager();
        List<EmployeeCommit> employeeCommits = randomEmployeeCommitGenerator.getEmployeeCommits(100);
        for (EmployeeCommit employeeCommit : employeeCommits) {
            System.out.println(employeeCommit.toString());
        }
    }
}
