package school2024.test.task4;

import school2024.test.task4.processing.CommitFileProcessor;
import school2024.test.task4.processing.OutputWriter;

import java.util.Map;

public class Main {
    public static void main(String[] args) {
        String inputFilename = "commits.txt";
        String outputFilename = "result.txt";
        Map<String, Integer> commits = CommitFileProcessor.processInputFile(inputFilename);
        OutputWriter.writeToFile(commits, outputFilename);
    }
}
