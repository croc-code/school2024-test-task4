package org.bushkovsky.Reader;

import org.bushkovsky.paths.Paths;
import org.bushkovsky.services.Service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;

public class Reader {
    private final Paths localPaths = new Paths();
    public void read(String path, Service service) {

        File file = new File(path + localPaths.getCommits());
        try {
            FileReader fileReader = new FileReader(file);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String commit;

            while ((commit = bufferedReader.readLine()) != null) {
                service.addLine(commit);
            }

            bufferedReader.close();
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage());
        } catch (ParseException e) {
            System.out.println(e.getMessage());
        }
    }
}
