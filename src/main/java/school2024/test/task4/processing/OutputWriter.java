package school2024.test.task4.processing;

import school2024.test.task4.processing.exceptions.OutputWriteException;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class OutputWriter {
    /* В данном методе:
    * вызов метода для поиска топ3 контрибьютера (см. следующий метод)
    * запись в файл result.txt
    * */
    public static void writeToFile(Map<String, Integer> commits, String filename) {
        List<Map.Entry<String, Integer>> result = getTop3Contributors(commits);
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
            for (Map.Entry<String, Integer> entry : result) {
                writer.write(entry.getKey());
                writer.newLine();
            }
        } catch (IOException e) {
            throw new OutputWriteException(e);
        }
    }

    /* В данном методе:
     * сортировка по убыванию
     * возвращение списка с топ 3 контрибьютерами
     * */
    public static List<Map.Entry<String, Integer>> getTop3Contributors(Map<String, Integer> commits) {
        List<Map.Entry<String, Integer>> entryList = new ArrayList<>(commits.entrySet());
        entryList.sort((e1, e2) -> e2.getValue().compareTo(e1.getValue()));
        return entryList.subList(0, Math.min(3, entryList.size()));
    }
}
