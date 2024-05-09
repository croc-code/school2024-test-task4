package org.letunov.domain;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

public class EmployeeCommit {
    public final static DateTimeFormatter FORMATTER = DateTimeFormatter.ISO_LOCAL_DATE_TIME;
    private final String name;
    private final String reducedHash;
    private final LocalDateTime localDateTime;
    public EmployeeCommit(String name, String reducedHash, String localDateTime) {
        this.name = name;
        this.reducedHash = reducedHash;
        this.localDateTime = LocalDateTime.parse(localDateTime, FORMATTER);
    }
    public EmployeeCommit(EmployeeCommit employeeCommit) {
        this.name = employeeCommit.name;
        this.reducedHash = employeeCommit.reducedHash;
        this.localDateTime = employeeCommit.localDateTime;
    }
    public String getName() {
        return name;
    }
    public String getReducedHash() {
        return reducedHash;
    }
    public LocalDateTime getLocalDateTime() {
        return localDateTime;
    }
    @Override
    public boolean equals(Object object) {
        if (object == this)
            return true;
        if (object == null)
            return false;
        if (object instanceof EmployeeCommit other)
            return Objects.equals(name, other.name) &&
                    Objects.equals(reducedHash, other.reducedHash) &&
                        Objects.equals(localDateTime, other.localDateTime);
        else
            return false;
    }
    @Override
    public int hashCode() {
        return 31 * name.hashCode() + reducedHash.hashCode() + localDateTime.hashCode();
    }
    @Override
    public String toString() {
        return name + " " + reducedHash + " " + FORMATTER.format(localDateTime);
    }
}
