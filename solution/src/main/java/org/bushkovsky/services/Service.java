package org.bushkovsky.services;

import org.bushkovsky.parsers.ParserCommits;

import java.text.ParseException;
import java.util.HashMap;

public class Service {

    ParserCommits parserCommits = new ParserCommits();
    private final HashMap<String, Integer> contributorsRating = new HashMap<String, Integer>();

    public void addLine(String line) throws ParseException {
        doCount(parserCommits.parse(line));
    }

    private void doCount(String name){
        if (contributorsRating.containsKey(name)) {
            Integer num = contributorsRating.get(name);
            num++;
            contributorsRating.put(name, num);
        }
        else {
            contributorsRating.put(name, 1);
        }
    }

    public HashMap<String, Integer> getContributorsRating() {
        return contributorsRating;
    }
}
