package org.example.task.service;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.HashMap;
import lombok.experimental.UtilityClass;
import org.example.task.configuration.ApplicationConfig;
import org.example.task.validation.Validator;

@UtilityClass
public class Reader {

    private static final ApplicationConfig APPLICATION_CONFIG = new ApplicationConfig();

    /**
     * Этот метод принимает на вход путь к файлу со входными данными и осуществляет построчное считывание файла.
     * Каждая считанная строка проходит валидацию. В случае успеха, происходит увеличение счетчика коммитов
     * соответствующего контрибьютера
     *
     * @param file путь к файлу со входными данными
     * @return Map, где key - имя контрибьютера, value - количество его коммитов
     */
    public static HashMap<String, Integer> readContributors(String file) {
        HashMap<String, Integer> contributors = new HashMap<>();
        Path path = Paths.get(file);

        if (Files.exists(path)) {
            try (BufferedReader br = new BufferedReader(new FileReader(file))) {
                String line;

                while ((line = br.readLine()) != null) {
                    String[] strings = line.split(" ");

                    if (isStringValid(strings)) {
                        contributors.merge(strings[APPLICATION_CONFIG.getUsernameIdx()], 1, Integer::sum);
                    }
                }
            } catch (IOException e) {
                System.err.print("Error reading from file: " + e.getMessage());
            }
        } else {
            System.err.println("File not found: " + file);
        }

        return contributors;
    }

    /**
     * Этот метод принимает строку файла в виде массива строк: элемент массива - слово сроки.
     * Проводит валидацию каждого слова на соответствие требуемому стандарту.
     *
     * @param strings массив строк, представляющий строку данных
     * @return {@code true}, если строка данных валидна, иначе {@code false}
     */
    @SuppressWarnings("ReturnCount")
    private boolean isStringValid(String[] strings) {
        if (!Validator.isLineValid(strings)) {
            System.err.println("Invalid string format: " + Arrays.toString(strings));
            return false;
        }
        if (!Validator.isUsernameValid(strings[APPLICATION_CONFIG.getUsernameIdx()])) {
            System.err.println("Invalid username: " + strings[APPLICATION_CONFIG.getUsernameIdx()]);
            return false;
        }
        if (!Validator.isHashValid(strings[APPLICATION_CONFIG.getHashIdx()])) {
            System.err.println("Invalid hash: " + strings[APPLICATION_CONFIG.getHashIdx()]);
            return false;
        }
        if (!Validator.isDateValid(strings[APPLICATION_CONFIG.getDateIdx()])) {
            System.err.println("Invalid date: " + strings[APPLICATION_CONFIG.getDateIdx()]);
            return false;
        }

        return true;
    }
}
