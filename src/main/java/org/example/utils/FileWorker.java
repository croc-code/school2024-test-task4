package org.example.utils;


import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FileWorker {
    /**
     * Читает файл "commits.txt" и возвращает Map, где ключ - имя пользователя,
     * а значение - список пар (хэш коммита, дата коммита) для этого пользователя.
     * Файл должен содержать строки в формате:
     * "username commit_hash YYYY-MM-DDThh:mm:ss.SSS"
     * Например:
     * "user1 1234567 2023-04-01T12:00:00.000"
     * Если строка не соответствует формату, она игнорируется.
     *
     * @return Map<String, List<Map.Entry<String, LocalDateTime>>> с информацией о коммитах
     */
    public static Map<String, List<Map.Entry<String, LocalDateTime>>> readFile() {
        String fileName = "commits.txt";
        Map<String, List<Map.Entry<String, LocalDateTime>>> result = new HashMap<>();

        //читаем файл
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line;
            //зададим регулярное выражение, при помощи которого будем отбирать только валидные строки
            Pattern pattern = Pattern.compile("^[A-Za-z_][A-Za-z0-9_]+\\s[a-z0-9]{7}\\s[1-9][0-9]{3}-[0-9]{2}-[0-9]{2}T([0-9]{2}:){2}[0-9]{2}.[0-9]{3}$");
            Matcher matcher;
            while ((line = br.readLine()) != null) {
                matcher = pattern.matcher(line);

                /**
                 * Проверяем соотносится ли строка с регулярным выражением. Если нет, то пропускаем.
                 * Если строка соотносится, то:
                 * 1) разделяем строку на массив элементов username commit_hash date (делим по пробелам)
                 * 2) если в Map нет ключа userName (т.е. пользователь ещё не был добавлен),
                 * то добавим его + проинициализируем массив
                 * 3) получим массив commit_hash + date для имени пользователя
                 * 4) добавим в полученный массив новую пару commit_hash + date
                 * 5) сохраним полученный массив для имени пользователя в Map<Имя, Лист<Пара<Хэш, Дата>>>
                 */
                if (matcher.find()) {
                    List<String> commitLine = List.of(line.split("\\s"));

                    String userName = commitLine.get(0);
                    String commitHash = commitLine.get(1);
                    String dateStr = commitLine.get(2);
                    DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE_TIME;
                    LocalDateTime date = LocalDateTime.parse(dateStr, formatter);

                    if (!result.containsKey(userName))
                        result.put(userName, new ArrayList<>());
                    List<Map.Entry<String, LocalDateTime>> userEntries = result.get(userName);
                    userEntries.add(Map.entry(commitHash, date));
                    result.put(userName, userEntries);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }


    /**
     * Записывает список строк в файл "result.txt".
     * Строки разделяются пробелами.
     *
     * @param list список строк для записи в файл
     */
    public static void writeToFile(List<String> list) {
        String fileName = "result.txt";

        //создадим строку с элементами list, разделенными пробелами
        String result = String.join(" ", list);

        try {
            FileWriter writer = new FileWriter(fileName);
            writer.write(result);
            writer.close(); // Закрытие файла после записи
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
