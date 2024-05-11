package org.example;

public class Main {
    public static void main(String[] args) {
        // Создание экземпляра CommitParser с указанием входного и выходного файлов
        CommitParser commitParser = new CommitParser("commits.txt", "result.txt");

        // Вызов метода для парсинга файла и записи результатов
        commitParser.parseFile();
    }
}