import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

public class Main {
    private final static String fileIn = "src/main/resources/commits.txt";
    private final static String fileOut = "src/main/resources/result.txt";

    public static void main(String[] args) throws IOException {
        // Создаем HashMap для хранения количества коммитов каждого автора
        HashMap<String, Integer> authorCommitsMap = new HashMap<>();
        try (BufferedReader bf = new BufferedReader(new FileReader(fileIn))) {
            String line = bf.readLine();
            // Читаем файл построчно
            while (line != null) {
                // Получаем автора коммита из строки
                String author = getAuthorFromLine(line);

                // Проверяем, содержится ли уже автор в HashMap
                if (authorCommitsMap.containsKey(author)) {
                    // Если автор уже есть, увеличиваем количество его коммитов на 1
                    authorCommitsMap.put(author, authorCommitsMap.get(author) + 1);
                } else {
                    // Если автора еще нет, добавляем его в HashMap и устанавливаем количество его коммитов как 1
                    authorCommitsMap.put(author, 1);
                }
                line = bf.readLine(); // Читаем следующую строку
            }

            // Получаем топ-3 авторов по количеству коммитов
            List<String> topAuthors = getTopAuthors(authorCommitsMap);

            // Записываем топ-3 авторов в файл
            writeAuthorsToFile(topAuthors);
        }
        catch (FileNotFoundException ex) {
            System.out.println(ex.getMessage()); // Выводим сообщение об ошибке, если файл не найден
        }
    }

    // Метод для получения топ-3 авторов по количеству коммитов
    private static List<String> getTopAuthors(HashMap<String, Integer> authorCommitsMap) {
        return authorCommitsMap
                .entrySet()
                .stream()
                .sorted(Map.Entry.<String, Integer>comparingByValue().reversed()) // Сортируем по убыванию количества коммитов
                .limit(3) // Ограничиваем топ-3 авторами
                .map(Map.Entry::getKey) // Получаем только имена авторов
                .collect(Collectors.toList()); // Собираем результат в список
    }

    // Метод для записи авторов в файл
    private static void writeAuthorsToFile(List<String> keys) throws IOException {
        try (BufferedWriter br = new BufferedWriter(new FileWriter( fileOut))) {
            for (String s: keys) {
                String line =  s + "\n"; // Формируем строку для записи
                br.write(line); // Записываем строку в файл
            }
        }
    }

    // Метод для извлечения имени автора из строки коммита
    private static String getAuthorFromLine(String line) {
        return line.split(" ")[0]; // Получаем первое слово из строки
    }

}
