package org.example;

import java.io.File;

public class FileFinder {
    public FileFinder() {
    }

    public String findFile(String fileName) {
        // Начинаем поиск с корневого каталога
        File root = new File("/");
        return searchFile(root, fileName);
    }

    private String searchFile(File directory, String fileName) {
        File[] files = directory.listFiles();
        if (files != null) {
            for (File file : files) {
                if (file.isDirectory()) {
                    // Рекурсивный поиск в подкаталогах
                    String filePath = searchFile(file, fileName);
                    if (filePath != null) {
                        return filePath;
                    }
                } else if (file.getName().equals(fileName)) {
                    // Файл найден
                    return file.getAbsolutePath();
                }
            }
        }
        // Файл не найден
        return null;
    }
}
