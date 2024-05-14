package ru.croc;

import ru.croc.reader.CommitsFileReader;
import ru.croc.solver.CalculateTop;
import ru.croc.writer.CommitsFileWriter;

import java.util.List;
import java.util.Map;

public class Main {
    public static void main(String[] args) {
        try {
            Map<String, Integer> contributors = CommitsFileReader.readFromResources();
            List<String> topContributors = CalculateTop.calculateTopContributors(contributors);
            CommitsFileWriter.writeToFile(topContributors);

        } catch (Exception e) {
            System.out.println("err during file processing: " + e.getMessage());
        }
    }
}