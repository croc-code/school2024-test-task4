package org.polosin.processor;

import jdk.jfr.Description;
import org.assertj.core.api.Assertions;
import org.junit.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.assertj.core.api.AssertionsForClassTypes.in;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Stream;

public class CommitProcessorTest {

    @Test
    public void testCommitProcessorTargetFileName() {
        Assertions.assertThat(CommitProcessor.getTargetFilename())
                .isEqualTo("src/test/resources/result.txt");
    }

    @Test
    public void testFileNotExists() throws IOException {
        assertThatThrownBy(() -> CommitProcessor.getTopThreeContributes("src/commits_good.txt"))
                .isInstanceOf(java.nio.file.NoSuchFileException.class);
    }

    @Test
    public void testIncorrectLineContainsMoreThenThreeParams() {

    }

    @Test
    public void testIncorrectLineContainsLessThenThreeParams() {

    }

    @Test
    public void testInvalidUsernameContainsFirstDigits() {
        Stream<String> input = Stream.of("256AIvanov 25ec001 2024-04-24T13:56:39.492");
        Assertions.assertThat(CommitProcessor.formRating(input)).isEqualTo(List.of());
    }

    @Test
    public void testInvalidHash() {
        Stream<String> input = Stream.of("AIvanov 25eC001 2024-04-24T13:56:39.492"); // contains capital letter
        Assertions.assertThat(CommitProcessor.formRating(input)).isEqualTo(List.of());
        input = Stream.of("AIvanov 25eC001777 2024-04-24T13:56:39.492"); // more than 7 symbols
        Assertions.assertThat(CommitProcessor.formRating(input)).isEqualTo(List.of());
        input = Stream.of("AIvanov 257 2024-04-24T13:56:39.492"); // less than 7 symbols
        Assertions.assertThat(CommitProcessor.formRating(input)).isEqualTo(List.of());
    }

    @Test
    public void testInvalidDate() {

        Assertions.assertThat(CommitProcessor.formRating(Stream.of("AIvanov 25ec001 2024-04-4T13:56:39.492")))
                .isEqualTo(List.of());
        Assertions.assertThat(CommitProcessor.formRating(Stream.of("AIvanov 25ec001 2024-04-2413:56:39.492")))
                .isEqualTo(List.of());
        Assertions.assertThat(CommitProcessor.formRating(Stream.of("AIvanov 25ec001 24-04-14T13:39.492")))
                .isEqualTo(List.of());
    }

    @Test
    public void testDateWithSpecialEnding() {
        // with last digits with point in the end ".492"
        Assertions.assertThat(CommitProcessor.formRating(Stream.of("AIvanov 25ec001 2024-04-24T13:56:39.492")))
                .isEqualTo(List.of("AIvanov"));
    }

    @Test
    public void testDateWithNoSpecialEnding() {
        // w/o last digits after point in the end ".492"
        Assertions.assertThat(CommitProcessor.formRating(Stream.of("AIvanov 25ec001 2024-04-24T13:56:39")))
                .isEqualTo(List.of("AIvanov"));
    }

    @Test
    public void testRatingWithOneUser() {
        Assertions.assertThat(CommitProcessor.formRating(Stream.of("AIvanov 25ec001 2024-04-24T13:56:39.492",
                        "AIvanov 26ec001 2024-04-24T13:56:39.492")))
                .isEqualTo(List.of("AIvanov"));
    }

    @Test
    public void testRatingWithTwoUsers() {
        Assertions.assertThat(CommitProcessor.formRating(Stream.of("AIvanov 25ec001 2024-04-24T13:56:39.492",
                        "AIvanov 25ec001 2024-04-24T13:56:39.492", "BIvanov 25ec001 2024-04-24T13:56:39.492",
                        "AIvanov 25ec001 2024-04-24T13:56:39.492", "AIvanov 25ec001 2024-04-24T13:56:39.492",
                        "AIvanov 25ec001 2024-04-24T13:56:39.492")))
                .isEqualTo(List.of("AIvanov", "BIvanov"));
    }

    @Test
    public void testRatingWithTwoFirstPlace() {
        Assertions.assertThat(CommitProcessor.formRating(Stream.of("AIvanov 25ec001 2024-04-24T13:56:39.492",
                        "AIvanov 25ec001 2024-04-24T13:56:39.492", "BIvanov 25ec001 2024-04-24T13:56:39.492",
                        "BIvanov 25ec001 2024-04-24T13:56:39.492", "CIvanov 25ec001 2024-04-24T13:56:39.492",
                        "DIvanov 25ec001 2024-04-24T13:56:39.492")))
                .isEqualTo(List.of("BIvanov", "AIvanov", "CIvanov"));
    }

    @Test
    public void testRatingWithThreeFirstPlace() {
        Assertions.assertThat(CommitProcessor.formRating(Stream.of("AIvanov 25ec001 2024-04-24T13:56:39.492",
                        "CIvanov 25ec001 2024-04-24T13:56:39.492", "BIvanov 25ec001 2024-04-24T13:56:39.492",
                        "BIvanov 25ec001 2024-04-24T13:56:39.492", "CIvanov 25ec001 2024-04-24T13:56:39.492",
                        "DIvanov 25ec001 2024-04-24T13:56:39.492", "AIvanov 25ec001 2024-04-24T13:56:39.492")))
                .isEqualTo(List.of("CIvanov", "BIvanov", "AIvanov"));
    }

    @Test
    public void testWriteToFile() throws IOException {
        CommitProcessor.getTopThreeContributes("src/test/resources/commits_good.txt");
        Stream<String> lines = Files.lines(Path.of("src/test/resources/result.txt"), StandardCharsets.UTF_8);
        Assertions.assertThat(lines.toList()).isEqualTo(List.of("AIvanov", "BIvanov", "CIvanov"));

    }
}
