package org.letunov.core;

import org.letunov.domain.EmployeeCommit;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.util.Arrays;

public class EmployeeCommitReader implements AutoCloseable {
    private final BufferedReader bufferedReader;
    public EmployeeCommitReader(Reader in) {
        this.bufferedReader = new BufferedReader(in);
    }
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
