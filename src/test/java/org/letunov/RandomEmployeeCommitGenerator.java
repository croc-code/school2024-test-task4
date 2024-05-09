package org.letunov;

import org.letunov.domain.EmployeeCommit;

import java.time.LocalDateTime;
import java.util.*;

public class RandomEmployeeCommitGenerator {
    private final Random random = new Random();
    public List<EmployeeCommit> getEmployeeCommits(int number) {
        int employeeCount = (int) (number * 0.2);
        List<EmployeeCommit> employeeCommits = new ArrayList<>();
        for (int i = 0; i < employeeCount; i++) {
            EmployeeCommit employeeCommit
                    = new EmployeeCommit(getRandomName(), getRandomReducedHash(), getRandomLocalDateTime());
            employeeCommits.add(employeeCommit);
        }
        for (int i = 0; i < number-employeeCount; i++) {
            EmployeeCommit employeeCommit = employeeCommits.get(random.nextInt(0, employeeCount));
            EmployeeCommit anotherCommit = new EmployeeCommit(employeeCommit.getName(),
                    getRandomReducedHash(), getRandomLocalDateTime());
            employeeCommits.add(anotherCommit);
        }
        return employeeCommits;
    }
    public String getRandomName() {
        int nameLength = random.nextInt(3, 10);
        StringBuilder name = new StringBuilder();
        name.append((char) random.nextInt(0x0041, 0x005B));
        for (int i = 1; i < nameLength; i++) {
            int chose = random.nextInt(0, 4);
            if (chose == 1)
                name.append((char) random.nextInt(0x0041, 0x005B));
            if (chose == 2)
                name.append((char) random.nextInt(0x0061, 0x007B));
            else if (chose == 3)
                name.append((char) random.nextInt(0x0030, 0x003A));
            else if (chose == 0)
                name.append('_');
        }
        return name.toString();
    }
    public String getRandomReducedHash() {
        int hashLength = 7;
        StringBuilder hash = new StringBuilder();
        for (int i = 0; i < hashLength; i++) {
            int chose = random.nextInt(0, 2);
            if (chose == 0)
                hash.append((char) random.nextInt(0x0061, 0x007B));
            else if (chose == 1)
                hash.append((char) random.nextInt(0x0030, 0x003A));
        }
        return hash.toString();
    }
    public String getRandomLocalDateTime() {
        LocalDateTime localDateTime = LocalDateTime.now();
        return EmployeeCommit.FORMATTER.format(
                localDateTime.plusHours(random.nextInt(0, 100))
        );
    }
}
