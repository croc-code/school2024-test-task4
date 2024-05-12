import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ReportApp {
    private final String path;

    public ReportApp(String path) {
        this.path = path;
    }

    public void getReportOfActiveUsers() {
        HashMap<String, Integer> commitsByUser = new HashMap<>(); // Словарь с сотрудниками и кол-м их коммитов

        try {
            BufferedReader reader = new BufferedReader(new FileReader(path));
            String line = reader.readLine();

            while (line != null) {
                processLine(line, commitsByUser);
                line = reader.readLine();
            }
            reader.close();
        } catch (IOException e) {
            System.out.print("Произошла ошибка при чтении файла:\n" + e.getMessage());
        }

        List<String> activeUsers = getMostActiveUsers(commitsByUser); // Получаем список наиболее активных сотрудников
        getResultFile(activeUsers); // На основе полученных данных формируем итоговый файл
    }

    private void processLine(String line, HashMap<String, Integer> commitsByUser) {
        // Записываем количество коммитов для каждого сотрудника

        String surname = line.substring(0, line.indexOf(" "));
        if (!commitsByUser.containsKey(surname)) {
            commitsByUser.put(surname, 0);
        }
        commitsByUser.put(surname, commitsByUser.get(surname) + 1);
    }

    private List<String> getMostActiveUsers(Map<String, Integer> commitsByUser) {

        // Конвертируем HashMap в список Map.Entry
        List<Map.Entry<String, Integer>> list =
                new ArrayList<>(commitsByUser.entrySet());

        // Сортируем список по значению в обратном порядке
        list.sort((o1, o2) -> o2.getValue().compareTo(o1.getValue()));

        if (list.size() == 2) return List.of(list.get(0).getKey(), list.get(1).getKey());
        else if (list.size() == 1) return List.of(list.get(0).getKey());

        return List.of(list.get(0).getKey(), list.get(1).getKey(), list.get(2).getKey());
    }

    private void getResultFile(List<String> users) {
        // Формируем итоговый файл из списка наиболее активных сотрудников

        try {
            FileWriter writer = new FileWriter("result.txt");
            for (String surname : users) {
                writer.write(surname + "\n");
            }
            writer.close();
            System.out.print("Данные успешно записаны в файл result.txt!");
        } catch (IOException e) {
            System.out.print("Произошла ошибка при записи в файл:\n" + e.getMessage());
        }
        return;
    }
}