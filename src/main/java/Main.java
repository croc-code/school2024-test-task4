import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

public class Main {
    private final static String fileIn = "src/main/resources/commits.txt";
    private final static String fileOut = "src/main/resources/result.txt";
    public static void main(String[] args) throws IOException {
            HashMap<String, Integer> authorCommitsMap = new HashMap<>();
            try (BufferedReader bf = new BufferedReader(new FileReader(fileIn))) {
                String line = bf.readLine();
                while (line != null) {
                    System.out.println(line);
                    String author = getAuthorFromLine(line);

                    if (authorCommitsMap.containsKey(author)) {
                        authorCommitsMap.put(author, authorCommitsMap.get(author) + 1);
                    } else {
                        authorCommitsMap.put(author, 1);
                    }
                    line = bf.readLine();
                }
                System.out.println(authorCommitsMap);

                List<String> topAuthors = getTopAuthors(authorCommitsMap);

                writeAuthorsToFile(topAuthors);
            }
            catch (FileNotFoundException ex) {
                System.out.println(ex.getMessage());
            }

    }

    private static List<String> getTopAuthors(HashMap<String, Integer> authorCommitsMap) {
        return authorCommitsMap
                .entrySet()
                .stream()
                .sorted(Map.Entry.<String, Integer>comparingByValue().reversed())
                .limit(3)
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());
    }

    private static void writeAuthorsToFile(List<String> keys) throws IOException {
        try (BufferedWriter br = new BufferedWriter(new FileWriter( fileOut))) {
            for (String s: keys) {
                String line =  s + "\n";
                br.write(line);
            }
        }
    }

    private static String getAuthorFromLine(String line) {
        return line.split(" ")[0];
    }
}
