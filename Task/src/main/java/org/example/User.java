package org.example;

import java.time.LocalDateTime;

public class User {
    private String userName;
    private int commitCount;
    private LocalDateTime lastCommitDate;

    public User() {
        this.commitCount = 0;
        this.lastCommitDate = LocalDateTime.MIN;
    }

    public int getCommitCount() {
        return commitCount;
    }

    public void incrementCommitCount() {
        this.commitCount++;
    }

    public LocalDateTime getLastCommitDate() {
        return lastCommitDate;
    }

    public void setLastCommitDate(LocalDateTime lastCommitDate) {
        if (this.lastCommitDate.equals(LocalDateTime.MIN)) {
            this.lastCommitDate = lastCommitDate;
        }
    }

    public String getUserName() {
        return userName;
    }

    public User setUserName(String userName) {
        this.userName = userName;
        return this;
    }
}

