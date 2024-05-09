package org.letunov;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.letunov.core.ScoreManager;
import org.letunov.domain.EmployeeCommit;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

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
    }

    @Test
    public void getTopTest_2() {
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
        for (EmployeeCommit employeeCommit : employeeCommits)
            scoreManager.pushEmployeeCommit(employeeCommit);
        List<String> top3 = scoreManager.getTop(3);
        assertIterableEquals(names, top3);
    }

    @Disabled
    @Test
    public void getCommits() {
        List<EmployeeCommit> employeeCommits = randomEmployeeCommitGenerator.getEmployeeCommits(100);
        for (EmployeeCommit employeeCommit : employeeCommits) {
            System.out.println(employeeCommit.toString());
        }
    }
}
