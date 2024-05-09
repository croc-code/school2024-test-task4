package org.letunov;

import org.letunov.core.EmployeeCommitReader;
import org.letunov.core.ReportManager;
import org.letunov.core.ScoreManager;
import org.letunov.domain.EmployeeCommit;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;

public class App {
  public static void main(String[] args) throws Exception {
    ScoreManager scoreManager = new ScoreManager();
    try (EmployeeCommitReader employeeCommitReader
                 = new EmployeeCommitReader(ReportManager.getCommitsReader())) {
      EmployeeCommit employeeCommit = employeeCommitReader.nextEmployeeCommit();
      while (employeeCommit != null) {
        scoreManager.pushEmployeeCommit(employeeCommit);
        employeeCommit = employeeCommitReader.nextEmployeeCommit();
      }
    } catch (FileNotFoundException fileNotFoundException) {
      System.out.println(fileNotFoundException.getMessage());
    } catch (Exception exception) {
      throw new Exception(exception);
    }
    System.out.println("Finish");
  }
}
