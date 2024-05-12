package org.example.task;

import java.time.LocalDateTime;
import org.example.task.configuration.ApplicationConfig;
import org.example.task.validation.Validator;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

public class ValidatorTest {

    private final String split = " ";

    @ParameterizedTest
    @CsvSource(value = {
            "qwerty qwerty qwerty",
            "qwe qwe qwe"
    })
    void matchLine_whenValid(String line) {
        assertTrue(Validator.isLineValid(line.split(split)));
    }

    @ParameterizedTest
    @CsvSource(value = {
            "qwerty qwerty qwerty qwerty",
            "qwerty qwerty qwerty qwerty qwerty",
            "qwerty",
            "qwe qwe"
    })
    void matchLine_whenInvalid(String line) {
        assertFalse(Validator.isLineValid(line.split(split)));
    }

    @ParameterizedTest
    @CsvSource(value = {
            "qwerty",
            "q1w1e1r1t1y1",
            "q1werty",
            "q_w_e_r__t_y_____",
            "____qwerty11",
            "QWERTY",
            "_QWER45TY_",
            "qw_23ERTY",
    })
    void matchUsername_whenValid(String username) {
        assertTrue(Validator.isUsernameValid(username));
    }

    @ParameterizedTest
    @CsvSource(value = {
            "1qwerty",
            "1111q1w1e1r1t1y1",
            "qw@erty",
            "qw#erty",
            "qw$erty",
            "qw%erty",
            "qw^erty",
            "qw&erty",
            "qw*erty",
            "qwe(rty",
            "qwer)ty",
            "__--_qwerty11",
            "имя",
            "qwerИМЯ"
    })
    void matchUsername_whenInvalid(String username) {
        assertFalse(Validator.isUsernameValid(username));
    }

    @ParameterizedTest
    @CsvSource(value = {
            "1234567",
            "123456a",
            "a123456",
            "1a3s5f7",
            "1234qwe"
    })
    void matchHash_whenValid(String hash) {
        assertTrue(Validator.isHashValid(hash));
    }

    @ParameterizedTest
    @CsvSource(value = {
            "1",
            "12",
            "123",
            "1234",
            "12345",
            "123456",
            "12345678",
            "123456789",
            "_a3s5f7",
            "A234qwe",
            "123A567",
            "123456A",
            "a^23456",
            "1a3%5f7",
            "123(567",
            "12345#a"
    })
    void matchHash_whenInvalid(String hash) {
        assertFalse(Validator.isHashValid(hash));
    }

    @ParameterizedTest
    @CsvSource(value = {
            "2024-05-11T13:56:39.492",
            "2024-05-11T13:00:00.000"
    })
    void matchDateTime_whenValid(String date) {
        assertTrue(Validator.isDateValid(date));
    }

    @ParameterizedTest
    @CsvSource(value = {
            "2024-44-24T13:56:39.492",
            "2024-11-50T13:56:39.492",
            "2024-11-24T60:56:39.492",
            "2024-11-24T13:68:39.492",
            "2024-04-24",
            "13:00:00.000",
            "sdsdsd"
    })
    void matchDateTime_whenInvalidFormat(String date) {
        assertFalse(Validator.isDateValid(date));
    }

    @Test
    void matchDateTime_whenInvalidPeriod() {
        LocalDateTime now = LocalDateTime.now();
        ApplicationConfig applicationConfig = new ApplicationConfig();
        LocalDateTime past = now.minusDays(applicationConfig.getPeriodDays() * 2L);
        LocalDateTime future1 = now.plusDays(applicationConfig.getPeriodDays() / 2);
        LocalDateTime future2 = now.plusDays(applicationConfig.getPeriodDays() * 2L);
        assertAll(
                () -> assertFalse(Validator.isDateValid(String.valueOf(past))),
                () -> assertFalse(Validator.isDateValid(String.valueOf(future1))),
                () -> assertFalse(Validator.isDateValid(String.valueOf(future2)))
        );
    }
}
