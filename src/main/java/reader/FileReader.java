package reader;

import commit.Commit;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.Scanner;

// Чтение файлов (.txt и .properties)
public class FileReader {
    private static final String PROPERTIES_PATH = "src/main/resources/properties/paths.properties";

    private FileReader() {}

    // Чтение данных из файла с коммитами, инициализация и наполение списка с коммитами
    public static List<Commit> readCommits(String path) {
        List<Commit> commits = new ArrayList<>();

        try (Scanner scanner = new Scanner(new File(path))) {
            while (scanner.hasNextLine()) {
                String[] input = scanner.nextLine().split(" ");

                commits.add(
                        new Commit(
                            input[0],
                            input[1],
                            input[2]));
            }
        } catch (FileNotFoundException e) {
            System.out.println("Не найден файл с коммитами");
        }
        return commits;
    }

    // Чтение пути до файла на основе переданного ключа
    public static String readPathFromProperties(String key) {
        Properties config = new Properties();

        try (FileInputStream fileInputStream = new FileInputStream(PROPERTIES_PATH)) {
            config.load(fileInputStream);
        } catch (IOException e) {
            System.out.println("Не найден файл с путями");
        }

        return config.getProperty(key);
    }
}
