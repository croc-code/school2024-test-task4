package ru.croc;

import java.io.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static ru.croc.CommitValidator.*;

/**
 * Класс для работы с файлами
 */
public class FileManager {

    /**
     * Считывает данные о коммитах из файла и подсчитывает количество коммитов для каждого пользователя.
     *
     * @return Словарь, ключ: имя пользователя, значение: количество их коммитов.
     */
    public static Map<String, Integer> read() {
        String path = "commits.txt";

        Map<String, Integer> commitCounts = new HashMap<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(path))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(" ");
                if (parts.length == 3 && isValidCommitInfo(parts[0], parts[1], parts[2])) {
                    commitCounts.put(parts[0], commitCounts.getOrDefault(parts[0], 0) + 1);
                } else {
                    System.out.println("Некорректный формат строки: " + line);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return commitCounts;
    }

    /**
     * Записывает список пользователей в файл.
     *
     * @param users Список пользователей для записи.
     */
    public static void write(List<String> users) {
        String path = "result.txt";

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(path))) {
            // Записываем каждого пользователя на отдельной строке
            for (String user : users) {
                writer.write(user);
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
