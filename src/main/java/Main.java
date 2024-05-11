import java.io.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public class Main {
    private final static String fileIn = "src/main/resources/commits.txt";
    private final static String fileOut = "src/main/resources/result.txt";
    private final static Logger logger = Logger.getLogger(Main.class.getName());

    public static void main(String[] args) {
        try {
            // Читаем данные из файла и записываем в Map
            Map<String, Integer> authorCommitsMap = readDataAndGetFilledMap();

            // Получаем топ-3 авторов по количеству коммитов
            List<String> topAuthors = getTopAuthors(authorCommitsMap);

            // Записываем топ-3 авторов в файл
            writeAuthorsToFile(topAuthors);
        } catch (IOException e) {
            logger.warning(e.getMessage());
        }
    }

    private static Map<String, Integer> readDataAndGetFilledMap() throws IOException {
        HashMap<String, Integer> authorCommitsMap = new HashMap<>();
        try (BufferedReader bf = new BufferedReader(new FileReader(fileIn))) {
            String line = bf.readLine();
            // Читаем файл построчно
            while (line != null) {
                // Получаем автора коммита из строки
                String author = getAuthorFromLine(line);

                // если не удалось получить автора из строки, то переходим к след. строке
                if (author == null) {
                    line = bf.readLine();
                    continue;
                }

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
        }
        logger.info("Successfully read data from file");
        return authorCommitsMap;
    }

    // Метод для получения топ-3 авторов по количеству коммитов
    private static List<String> getTopAuthors(Map<String, Integer> authorCommitsMap) {
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
        try (BufferedWriter br = new BufferedWriter(new FileWriter(fileOut))) {
            for (String s : keys) {
                String line = s + "\n"; // Формируем строку для записи
                br.write(line); // Записываем строку в файл
            }
        }
        logger.info("Successfully written to file");
    }

    // Метод для извлечения имени автора из строки коммита
    private static String getAuthorFromLine(String line) {
        String[] tokens = line.split(" ");
        if (
                Validator.isValidAuthor(tokens[0]) // проверка имени автора
                        && Validator.isValidHash(tokens[1]) // проверка хэша
                        && Validator.isValidDate(tokens[2]) // проверка даты
                        && Validator.isValidDateBounds(tokens[2]) // проверка принадлежности даты к спринту
        ) {
            return line.split(" ")[0]; // Получаем автора, если все проверки прошли
        }
        logger.warning("Wrong data in line: " + line);
        return null;
    }

}
