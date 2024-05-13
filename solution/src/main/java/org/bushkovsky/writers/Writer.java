package org.bushkovsky.writers;
import org.bushkovsky.paths.Paths;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class Writer {
    Paths paths = new Paths();
    public void write(String path, String s){
        String filePath = path + paths.getResult();
        try {
            writeToFile(filePath, s);
            System.out.println("String was successfully wrote");
        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public static void writeToFile(String filename, String s) throws IOException {
        FileWriter fileWriter = new FileWriter(filename);
        BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
        bufferedWriter.write(s);
        bufferedWriter.close();
    }
}
