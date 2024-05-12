
import java.io.*;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        Map <String, Integer> commits = new HashMap<>();

        try (BufferedReader reader = new BufferedReader(new FileReader("commits.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(" ");
                String username = parts[0];

                commits.put(username, commits.getOrDefault(username, 0) + 1);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        List<String> bestContributors = new ArrayList<>(commits.keySet());
        bestContributors.sort((a, b) -> commits.get(b) - commits.get(a));

        try (PrintWriter writer = new PrintWriter("result.txt")) {
            for (int i = 0; i < Math.min(3, bestContributors.size()); i++) {
                writer.println(bestContributors.get(i));
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
