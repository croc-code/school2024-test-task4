package org.letunov.core;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

/**
 * Класс, представляющий пути к файлам для чтения и записи.
 */
public class ReportManager {
    /**
     * Путь к файлу для чтения коммитов.
     */
    public static final Path COMMITS_PATH
            = Path.of(System.getProperty("user.dir"), "commits.txt");
    /**
     * Путь для записи результатов обработки коммитов
     */
    public static final Path RESULT_REPORT_PATH
            = Path.of(System.getProperty("user.dir"), "result.txt");

    /**
     * Конструктор по-умолчанию.
     */
    public ReportManager() {}

    /**
     * Позволяет получить поток для чтения коммитов.
     * @return поток для чтения коммитов
     * @throws IOException если файл не существует или не удается открыть поток
     */
    public static Reader getCommitsReader() throws IOException {
        if (!Files.exists(COMMITS_PATH))
            throw new FileNotFoundException("Файл с коммитами %s не был найден".formatted(COMMITS_PATH.toAbsolutePath().toString()));
        return new InputStreamReader(Files.newInputStream(COMMITS_PATH, StandardOpenOption.READ));
    }

    /**
     * Позволяет получить поток для записи результатов обработки коммитов.
     * @return поток для записи результатов
     * @throws IOException если не удается открыть поток
     */
    public static Writer getResultReportWriter() throws IOException {
        return new OutputStreamWriter(Files.newOutputStream(RESULT_REPORT_PATH));
    }
}
