package org.example;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ValidatorTest {

    @Test
    public void testValidateCommit_ValidCommit_ReturnsNull() {
        Validator validator = new Validator();
        String validCommit = "User1 1234567 2024-05-10T10:00:00";
        assertNull(validator.validateCommit(validCommit),
                "Ожидался null для корректного коммита");
    }

    @Test
    public void testValidateCommit_InvalidCommitFormat_ReturnsErrorMessage() {
        Validator validator = new Validator();
        String invalidCommit = "InvalidCommit";
        String expectedErrorMessage = "Некорректный формат строки - " + invalidCommit;
        assertEquals(expectedErrorMessage, validator.validateCommit(invalidCommit),
                "Ожидалось сообщение об ошибке для некорректного формата коммита");
    }

    @Test
    public void testValidateCommit_InvalidCommitHash_ReturnsErrorMessage() {
        Validator validator = new Validator();
        String invalidCommit = "User1 invalidHash 2024-05-10T10:00:00";
        String expectedErrorMessage = "Некорректный хэш коммита - invalidHash";
        assertEquals(expectedErrorMessage, validator.validateCommit(invalidCommit),
                "Ожидалось сообщение об ошибке для некорректного хэша коммита");
    }

    @Test
    public void testValidateCommit_InvalidCommitDate_ReturnsErrorMessage() {
        Validator validator = new Validator();
        String invalidCommit = "User1 1234567 invalidDate";
        String expectedErrorMessage = "Некорректная дата коммита - invalidDate";
        assertEquals(expectedErrorMessage, validator.validateCommit(invalidCommit),
                "Ожидалось сообщение об ошибке для некорректной даты коммита");
    }
}

