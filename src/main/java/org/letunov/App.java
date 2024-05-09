package org.letunov;

import org.letunov.core.EmployeeCommitReader;
import org.letunov.core.ReportManager;
import org.letunov.core.ScoreManager;
import org.letunov.domain.EmployeeCommit;

import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

/**
 * Главный класс программы. Главный поток выполнения операций.
 */
public class App {
  public static void main(String[] args) {
    ScoreManager scoreManager = new ScoreManager();
    System.out.println("Считывание коммитов из файла..");
    try (EmployeeCommitReader employeeCommitReader
                 = new EmployeeCommitReader(ReportManager.getCommitsReader())) {
      EmployeeCommit employeeCommit = employeeCommitReader.nextEmployeeCommit();
      while (employeeCommit != null) {
        scoreManager.pushEmployeeCommit(employeeCommit);
        employeeCommit = employeeCommitReader.nextEmployeeCommit();
      }
    } catch (FileNotFoundException fileNotFoundException) {
      System.out.println(fileNotFoundException.getMessage());
      System.exit(-1);
    } catch (Exception exception) {
      System.out.printf("Ошибка при чтении файла %s%n", ReportManager.COMMITS_PATH);
      System.exit(-1);
    }
    System.out.println("Коммиты успешно считаны");

    List<String> top3 = scoreManager.getTop(3);
    try(BufferedWriter bufferedWriter =
                new BufferedWriter(ReportManager.getResultReportWriter())) {
      for (String name : top3) {
        bufferedWriter.write(name);
        bufferedWriter.newLine();
        bufferedWriter.flush();
      }
    } catch (IOException exception) {
      System.out.printf("Ошибка при записи в файл %s%n", ReportManager.RESULT_REPORT_PATH);
      System.exit(-1);
    }

    System.out.println("Файл result.txt обновлен:");
    for (String name : top3)
      System.out.println(name);
  }
}
