package org.example.task;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import org.example.task.service.TopNPicker;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

public class TopNPickerTest {

    @Test
    public void pickTopContributors_whenSimpleData() {
        System.out.println();
        HashMap<String, Integer> contributors = new HashMap<>();
        contributors.put("AIvanov", 2);
        contributors.put("BIvanov", 3);
        contributors.put("CIvanov", 4);
        contributors.put("DIvanov", 1);

        Map<Integer, Set<String>> expectedTopContributors = new HashMap<>();
        expectedTopContributors.put(1, Set.of("CIvanov"));
        expectedTopContributors.put(2, Set.of("BIvanov"));
        expectedTopContributors.put(3, Set.of("AIvanov"));

        Map<Integer, Set<String>> actualTopContributors = TopNPicker.pickTopContributors(contributors);

        assertEquals(expectedTopContributors, actualTopContributors);
    }

    @Test
    public void pickTopContributors_whenTwoPersonsOnOnePosition() {
        System.out.println();
        HashMap<String, Integer> contributors = new HashMap<>();
        contributors.put("AIvanov", 2);
        contributors.put("BIvanov", 4);
        contributors.put("CIvanov", 4);
        contributors.put("DIvanov", 1);

        Map<Integer, Set<String>> expectedTopContributors = new HashMap<>();
        expectedTopContributors.put(1, Set.of("CIvanov", "BIvanov"));
        expectedTopContributors.put(3, Set.of("AIvanov"));

        Map<Integer, Set<String>> actualTopContributors = TopNPicker.pickTopContributors(contributors);

        assertEquals(expectedTopContributors, actualTopContributors);
    }

    @Test
    public void pickTopContributors_whenThreePersonsOnOnePosition() {
        System.out.println();
        HashMap<String, Integer> contributors = new HashMap<>();
        contributors.put("AIvanov", 2);
        contributors.put("BIvanov", 4);
        contributors.put("CIvanov", 4);
        contributors.put("DIvanov", 4);

        Map<Integer, Set<String>> expectedTopContributors = new HashMap<>();
        expectedTopContributors.put(1, Set.of("CIvanov", "BIvanov", "DIvanov"));

        Map<Integer, Set<String>> actualTopContributors = TopNPicker.pickTopContributors(contributors);

        assertEquals(expectedTopContributors, actualTopContributors);
    }

    @Test
    public void pickTopContributors_whenThirdPositionEmpty() {
        System.out.println();
        HashMap<String, Integer> contributors = new HashMap<>();
        contributors.put("AIvanov", 3);
        contributors.put("BIvanov", 3);
        contributors.put("CIvanov", 4);
        contributors.put("DIvanov", 1);

        Map<Integer, Set<String>> expectedTopContributors = new HashMap<>();
        expectedTopContributors.put(1, Set.of("CIvanov"));
        expectedTopContributors.put(2, Set.of("BIvanov", "AIvanov"));

        Map<Integer, Set<String>> actualTopContributors = TopNPicker.pickTopContributors(contributors);

        assertEquals(expectedTopContributors, actualTopContributors);
    }

    @Test
    public void pickTopContributors_whenFourPersonInOnePosition() {
        System.out.println();
        HashMap<String, Integer> contributors = new HashMap<>();
        contributors.put("AIvanov", 3);
        contributors.put("BIvanov", 3);
        contributors.put("CIvanov", 3);
        contributors.put("DIvanov", 3);
        contributors.put("XIvanov", 1);

        Map<Integer, Set<String>> expectedTopContributors = new HashMap<>();
        expectedTopContributors.put(1, Set.of("CIvanov", "BIvanov", "AIvanov", "DIvanov"));

        Map<Integer, Set<String>> actualTopContributors = TopNPicker.pickTopContributors(contributors);

        assertEquals(expectedTopContributors, actualTopContributors);
    }

    @Test
    public void pickTopContributors_whenFourPersonInRating() {
        System.out.println();
        HashMap<String, Integer> contributors = new HashMap<>();
        contributors.put("AIvanov", 3);
        contributors.put("BIvanov", 2);
        contributors.put("CIvanov", 3);
        contributors.put("DIvanov", 2);
        contributors.put("XIvanov", 1);

        Map<Integer, Set<String>> expectedTopContributors = new HashMap<>();
        expectedTopContributors.put(1, Set.of("CIvanov", "AIvanov"));
        expectedTopContributors.put(3, Set.of("BIvanov", "DIvanov"));

        Map<Integer, Set<String>> actualTopContributors = TopNPicker.pickTopContributors(contributors);

        assertEquals(expectedTopContributors, actualTopContributors);
    }

    @Test
    public void pickTopContributors_whenAFewContributorsAndDifferentCount() {
        System.out.println();
        HashMap<String, Integer> contributors = new HashMap<>();
        contributors.put("AIvanov", 3);
        contributors.put("BIvanov", 2);

        Map<Integer, Set<String>> expectedTopContributors = new HashMap<>();
        expectedTopContributors.put(1, Set.of("AIvanov"));
        expectedTopContributors.put(2, Set.of("BIvanov"));

        Map<Integer, Set<String>> actualTopContributors = TopNPicker.pickTopContributors(contributors);

        assertEquals(expectedTopContributors, actualTopContributors);
    }

    @Test
    public void pickTopContributors_whenAFewContributorsAndSameCount() {
        System.out.println();
        HashMap<String, Integer> contributors = new HashMap<>();
        contributors.put("AIvanov", 3);
        contributors.put("BIvanov", 3);

        Map<Integer, Set<String>> expectedTopContributors = new HashMap<>();
        expectedTopContributors.put(1, Set.of("AIvanov", "BIvanov"));

        Map<Integer, Set<String>> actualTopContributors = TopNPicker.pickTopContributors(contributors);

        assertEquals(expectedTopContributors, actualTopContributors);
    }
}
