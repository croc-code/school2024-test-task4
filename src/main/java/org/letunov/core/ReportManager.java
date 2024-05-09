package org.letunov.core;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

public class ReportManager {
    public static final Path COMMITS_PATH
            = Path.of(System.getProperty("user.dir"), "commits.txt");
    public static final Path RESULT_REPORT_PATH
            = Path.of(System.getProperty("user.dir"), "result.txt");
    public static Reader getCommitsReader() throws IOException {
        if (!Files.exists(COMMITS_PATH))
            throw new FileNotFoundException("Файл с коммитами %s не был найден".formatted(COMMITS_PATH.toAbsolutePath().toString()));
        return new InputStreamReader(Files.newInputStream(COMMITS_PATH, StandardOpenOption.READ));
    }
    public static Writer getResultReportWriter() throws IOException {
        return new OutputStreamWriter(Files.newOutputStream(RESULT_REPORT_PATH));
    }
}
