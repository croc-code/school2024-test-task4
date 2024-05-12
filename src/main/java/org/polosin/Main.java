package org.polosin;

import org.polosin.processor.CommitProcessor;

import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        try {
            CommitProcessor.getTopThreeContributes("commits.txt");
        } catch (IOException e) {
            System.err.println("Unable to open file.");
        }
    }
}