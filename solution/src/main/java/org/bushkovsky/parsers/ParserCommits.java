package org.bushkovsky.parsers;
import java.text.ParseException;
import java.util.HashMap;
import java.util.regex.Matcher;
public class ParserCommits {
    private final String USERNAME_REGEX = "^[a-zA-Z][a-zA-Z0-9_]*$";
    private final String COMMIT_HASH_REGEX = "^[a-f0-9]{7}$";
    private final String DATE_TIME_REGEX = "^\\d{4}-\\d{2}-\\d{2}T\\d{2}:\\d{2}:\\d{2}$";
    public String parse(String line) throws ParseException {
        String[] splitLine = line.split(" ");
        if (checkLine(splitLine)) {
            return splitLine[0];
        }
        else {
            throw new ParseException("Incorrect line " + splitLine[0] + " " + splitLine[1] + " " + splitLine[2] + " " + splitLine.length, 0);
        }
    }

    private boolean checkLine(String[] splitLine){
        return splitLine.length == 3 && checkName(splitLine[0]) && checkCommit(splitLine[1])
                && checkDate(splitLine[2]);
    }


    private boolean checkName(String name) {
        return name.matches(USERNAME_REGEX);
    }

    private boolean checkCommit(String commitHash) {
        return commitHash.matches(COMMIT_HASH_REGEX);
    }

    private boolean checkDate(String dateTime) {
        return dateTime.matches(DATE_TIME_REGEX);
    }
}
