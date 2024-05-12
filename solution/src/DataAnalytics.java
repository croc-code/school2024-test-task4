import java.io.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

public class DataAnalytics {

    public String getAnalytics() throws IOException{
        // Указываю путь к файлу с данными которые буду читать
        var inputFile = "data/commits.txt";
        // Путь где будет создаваться файл с результатом
        var outputFile = new File("data/result.txt");
        // Создаю мапу с которой буду хранить имена пользователей и количество их коммитов
        Map<String, Integer> commitTimes= new HashMap<>();

        try {
            // Создаю объект класса BufferedReader, чтобы прочитать файл
            var reader = new BufferedReader(new FileReader(inputFile));
            // Переменная, в которой хранится нынешняя строка
            String currentLine;
            while ((currentLine = reader.readLine()) != null) {
                // Разделяю каждую строка на слова по пробелам
                String[] parts = currentLine.split("\\s+");
                // Беру первое слово(оно же имя)
                String username = parts[0];
                // Добавляю в мапу имя и счётчик который увеличивается, если имя снова появляется в списке коммитов
                commitTimes.put(username, commitTimes.getOrDefault(username, 0) + 1);
            }
        } catch (IOException e){
            e.printStackTrace();
        }
        // Создаю список в который буду добавлять имена из мапы
        List<String> keys = commitTimes.entrySet().stream()
                .sorted(Map.Entry.<String, Integer>comparingByValue().reversed()) // Сортирую имена по кол-ву коммитов
                .limit(3) // Ограничиваю количество записей тремя именами с наибольшим кол-вом
                .map(Map.Entry::getKey) // Преобразую имена
                .collect(Collectors.toList()); //Собираю результаты в список
        // Создаю строку в которую запишу все значения из списка согласно требованиям
        String result = keys.stream().map(Objects::toString)
                .collect(Collectors.joining(" "));// Записываю элементы списка разделяя их пробелом
        // Создаю файл для записи результата, если такого файла нет, вывожу соответствующее сообщение в консоль
        if (outputFile.createNewFile()) {
            System.out.println("Result file created");
        } else {
            System.out.println("Result file already exists");
        }

        try {
            // Записываю строку с результатом в файл
            var writer = new BufferedWriter(new FileWriter(outputFile));
            writer.write(result);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        // Возвращаю строку с результатом
        return result;
    }
}
