package org.example.task;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import lombok.experimental.UtilityClass;
import org.example.task.configuration.ApplicationConfig;
import org.example.task.service.Reader;
import org.example.task.service.TopNPicker;
import org.example.task.service.Writer;

@UtilityClass
public class Application {

    private static final ApplicationConfig APPLICATION_CONFIG = new ApplicationConfig();

    /**
     * Основной метод приложения, который считывает участников из файла,
     * выбирает топ N участников и записывает результат в файл.
     *
     * @param args аргументы командной строки (не используются)
     */
    public static void main(String[] args) {

        HashMap<String, Integer> contributors = Reader.readContributors(APPLICATION_CONFIG.getInputFile());

        Map<Integer, Set<String>> topContributors = TopNPicker.pickTopContributors(contributors);

        Writer.writeToFile(topContributors, APPLICATION_CONFIG.getOutputFile());
    }
}
