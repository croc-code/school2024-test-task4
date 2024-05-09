package org.letunov.domain;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

/**
 * Представление объекта коммита сотрудника в предметной области.
 * Является неизменяемым.
 */
public class EmployeeCommit {
    /**
     * Представляет формат времени и даты в коммитах.
     */
    public final static DateTimeFormatter FORMATTER = DateTimeFormatter.ISO_LOCAL_DATE_TIME;
    private final String name;
    private final String reducedHash;
    private final LocalDateTime localDateTime;

    /**
     * Конструктор создания объекта.
     * @param name псевдоним сотрудника, сделавшего коммит
     * @param reducedHash сокращенный хэш коммита
     * @param localDateTime дата и время создания коммита в формате ISO_LOCAL_DATE_TIME
     */
    public EmployeeCommit(String name, String reducedHash, String localDateTime) {
        this.name = name;
        this.reducedHash = reducedHash;
        this.localDateTime = LocalDateTime.parse(localDateTime, FORMATTER);
    }

    /**
     * Конструктор копирования
     * @param employeeCommit Коммит сотрудника, который необходимо копировать
     */
    public EmployeeCommit(EmployeeCommit employeeCommit) {
        this.name = employeeCommit.name;
        this.reducedHash = employeeCommit.reducedHash;
        this.localDateTime = employeeCommit.localDateTime;
    }

    /**
     * Вернуть псевдоним сотрудника.
     * @return псевдоним сотрудника
     */
    public String getName() {
        return name;
    }

    /**
     * Вернуть сокращенный хэш коммита
     * @return сокращенный хэш коммита
     */
    public String getReducedHash() {
        return reducedHash;
    }

    /**
     * Вернуть дату и время создания коммита
     * @return дата и время коммита
     */
    public LocalDateTime getLocalDateTime() {
        return localDateTime;
    }
    @Override
    public boolean equals(Object object) {
        if (object == this)
            return true;
        if (object == null)
            return false;
        if (object instanceof EmployeeCommit other)
            return Objects.equals(name, other.name) &&
                    Objects.equals(reducedHash, other.reducedHash) &&
                        Objects.equals(localDateTime, other.localDateTime);
        else
            return false;
    }

    /**
     * Вычисляет хэш объекта.
     * @return хэш объекта
     */
    @Override
    public int hashCode() {
        return 31 * name.hashCode() + reducedHash.hashCode() + localDateTime.hashCode();
    }

    /**
     * Приводит объект к строке.
     * @return строка в формате <Имя пользователя> <Сокращенный хэш коммита> <Дата и время коммита>
     */
    @Override
    public String toString() {
        return name + " " + reducedHash + " " + FORMATTER.format(localDateTime);
    }
}
