package org.letunov.core;

import org.letunov.domain.EmployeeCommit;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.util.Arrays;

/**
 * Класс позволяющий построчно считывать коммиты сотрудников из потока.
 */
public class EmployeeCommitReader implements AutoCloseable {
    private final BufferedReader bufferedReader;

    /**
     * Конструктор создания объекта.
     * @param in поток, из которого считываются коммиты
     */
    public EmployeeCommitReader(Reader in) {
        this.bufferedReader = new BufferedReader(in);
    }

    /**
     * Метод, позволяющий считать следующий коммит из потока.
     * @return EmployeeCommit, если считан коммит и null, если достигнут конец потока или строка пустая
     * @throws IOException если строка не соответствует формату <Имя пользователя> <Сокращенный хэш коммита> <Дата и время коммита>
     */
    public EmployeeCommit nextEmployeeCommit() throws IOException {
        String string = bufferedReader.readLine();
        if (string == null || string.trim().isEmpty() || string.trim().equals(System.lineSeparator()))
            return null;
        String[] parts = string.split("\\s");
        return new EmployeeCommit(parts[0], parts[1], parts[2]);
    }
    @Override
    public void close() throws Exception {
        bufferedReader.close();
    }
}
