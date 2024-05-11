package org.example;


import org.example.utils.FileWorker;
import org.example.utils.Statistics;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

public class Main {
    public static void main(String[] args) {

        //читаем файл commits.txt
        Map<String, List<Map.Entry<String, LocalDateTime>>> commits = FileWorker.readFile();

        //определяем статистику
        List<String> names = Statistics.getTop3(commits);

        //записываем статистику в файл result.txt
        FileWorker.writeToFile(names);

    }
}