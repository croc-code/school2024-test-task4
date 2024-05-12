package ru.croc;

import java.util.List;
import java.util.Map;

public class Main {
    public static void main(String[] args) {
        Map<String, Integer> commitCounts = FileManager.read();
        List<String> topThreeUsers = ActivityAnalyzer.findTopThreeUsersWithMostCommits(commitCounts);
        FileManager.write(topThreeUsers);
    }
}