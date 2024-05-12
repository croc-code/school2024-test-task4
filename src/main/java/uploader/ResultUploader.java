package uploader;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

// Запись победивших пользователей в конечный файл
public class ResultUploader {
    private ResultUploader() {}

    public static void uploadResult(List<String> contributorsRanked, int numberOfAwardees, String path) {
        try (FileWriter writer = new FileWriter(path, false))
        {
            for (int i = 0; i < numberOfAwardees; i++) {
                writer.write(contributorsRanked.get(i) + "\n");
            }

            writer.flush();
        } catch (IOException e) {
            System.out.println("Не найден указанный путь до файла с результатами");
        }
    }
}
