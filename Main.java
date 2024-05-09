import java.io.*;
import java.util.*;

public class Main {
    public static void main(String[] args) throws IOException {
        String fileInput = "commits.txt";
        String fileOutput = "result.txt";
        theMainProgramLogic(fileInput, fileOutput);
    }

    /**
     * Основная функция программы, которая совмещает все функции класса и в итоге генерирует файл с ответом.
     * @throws IOException
     */
    public static void theMainProgramLogic(String fileInput, String fileOutput) throws IOException {
        Map<String, HashSet<String>> userCommits = fromFileToMapCount(fileInput);
        List<String> resultList = findTopThreeFromOurHashMap(userCommits);
        fromResultListToOutputFile(resultList, fileOutput);
    }

    /**
     * Функция, которая принимает название файла.
     * Создает HashMap с никами программистов и Set-ом всех его хэшей коммитов.
     * Данные так же проверяются по регулярным выражениям.
     * Дальше файл читается и в HashMap отправляются все данные.
     * Проверяется, содержит ли словарь пользователя.
     * Если нет - добавляет, если да - работает с его уникальными хэшами.
     * @param filename
     * @return словарь: {ник : set-хэши пользователя}
     * @throws IOException
     */
    public static Map<String, HashSet<String>> fromFileToMapCount(String filename) throws IOException {
        Map<String, HashSet<String>> userCommits = new HashMap<>();
        //Используем try-with-resources и инициализируем Exception при работе с файловой системой
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] resultOfSplit = line.split(" ");
                if (resultOfSplit.length > 0 && resultOfSplit.length < 4 &&
                        isValidUsername(resultOfSplit[0]) &&
                        isValidCommitHash(resultOfSplit[1]) &&
                        isValidDateTime(resultOfSplit[2])) {
                    String username = resultOfSplit[0];
                    String commitHash = resultOfSplit[1];

                    if (!userCommits.containsKey(username)) {
                        userCommits.put(username, new HashSet<>());
                    }

                    HashSet<String> commits = userCommits.get(username);
                    commits.add(commitHash);
                }
            }
        }
        return userCommits;
    }

    /**
     * Функция, которая принимает словарь и находит топ 3 наибольший значений.
     * Я решил сделать это с помощью рекурсии.
     * Находится максимальное значение и добавляется в список максимальных ключей по этим значениям.
     * После этого это значение удаляется и пока размер листа < 3 - рекурсивно вызывается.
     * та же функция для словаря уже без максимального элемента.
     * @param userCommits
     * @return список вида [top1,top2,top3], где top{1,2,3} - это ключи максимальных значений
     */
    public static List<String> findTopThreeFromOurHashMap(Map<String, HashSet<String>> userCommits) {
        List<String> list = new ArrayList<>();
        int maxCount = -99999;
        String maxKey = null;

        for (Map.Entry<String, HashSet<String>> entry : userCommits.entrySet()) {
            String key = entry.getKey();
            int size = entry.getValue().size();
            if (size > maxCount) {
                maxCount = size;
                maxKey = key;
            }
        }

        if (maxKey != null) {
            list.add(maxKey);
            userCommits.remove(maxKey);
            if (list.size() < 3) {
                List<String> OST = findTopThreeFromOurHashMap(userCommits);
                if (OST.size() > 0) {
                    list.addAll(OST.subList(0, Math.min(3 - list.size(), OST.size())));
                }
            }
        }

        return list;
    }

    /**
     * В данной функции берется результивный лист.
     * идет проход по циклу и в файл записываются все значения [top1,top2,top3].
     * @param list
     * @param fileOutput
     * @throws IOException
     */
    public static void fromResultListToOutputFile(List<String> list, String fileOutput) throws IOException {
        //Используем try-with-resources и инициализируем Exception при работе с файловой системой
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileOutput))) {
            for (int i = 0; i < list.size(); i++) {
                writer.write(list.get(i) + " ");
            }
        }
    }

    /**
     * Регулярка для имени пользователя.
     * @param username
     * @return
     */
    public static boolean isValidUsername(String username) {
        return username.matches("[a-zA-Z_][a-zA-Z0-9_]*");
    }

    /**
     * Регулярка для коммита.
     * @param commitHash
     * @return
     */
    public static boolean isValidCommitHash(String commitHash) {
        return commitHash.matches("[a-f0-9]{7}");
    }

    /**
     * Регулярка для даты коммита.
     * @param dateTime
     * @return
     */
    public static boolean isValidDateTime(String dateTime) {
        String regex = "\\d{4}-\\d{2}-\\d{2}T\\d{2}:\\d{2}:\\d{2}\\.\\d+";
        return dateTime.matches(regex);
    }
}
