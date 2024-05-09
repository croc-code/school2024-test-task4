package org.letunov.core;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.OpenOption;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.nio.file.attribute.FileAttribute;

public class ReportManager {
    private static final Path commitsPath
            = Path.of(System.getProperty("user.dir"), "commits.txt");
    private static final Path resultReportPath
            = Path.of(System.getProperty("user.dir"), "result.txt");
    public static Reader getCommitsReader() throws IOException {
        if (!Files.exists(commitsPath))
            throw new FileNotFoundException("Файл с коммитами %s не был найден".formatted(commitsPath.toAbsolutePath().toString()));
        return new InputStreamReader(Files.newInputStream(commitsPath, StandardOpenOption.READ));
    }
    public static Writer getResultReportWriter() throws IOException {
        return new OutputStreamWriter(Files.newOutputStream(resultReportPath));
    }
}
