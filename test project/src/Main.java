import java.io.*;
import java.lang.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;

public class Main {
    public static void main(String[] args) 
    {
        //файл commits.txt должен лежать в одной папке с приложением
        try {
            BufferedReader bufferedReader = getBufferedReader();
            bufferedReader.close();
        } catch (IOException e) {
            System.err.println("Ошибка чтения файла: " + e.getMessage());
        }
    }
    //считаем, что раз файл commits.txt создается сам, то проверку данных не устраиваем
    public static BufferedReader getBufferedReader() throws IOException
    {
        ArrayList<Commits> data = new ArrayList<>();
        File file = new File("commits.txt");
        FileReader fileReader = new FileReader(file);
        BufferedReader bufferedReader = new BufferedReader(fileReader);

        String line;
        while ((line = bufferedReader.readLine()) != null)
        {
            String[] tmp = line.split(" ");
            //на дату проверять не надо
            //так как владеем информацией только по поводу последнего спринта
            int targetIndex = isInData(data, tmp[0]);
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS");
            LocalDateTime tempDate = LocalDateTime.parse(tmp[2], formatter);
            if (targetIndex == -1) data.add(new Commits(tmp[0], tempDate));
            else
            {
                data.get(targetIndex).setCommitsCount(data.get(targetIndex).getCommitsCount() + 1);
                data.get(targetIndex).setCommitDate(LocalDateTime.parse(tmp[2], formatter));
                //увеличиваем счетчик коммитов на 1 и дату на более актуальную (проверяется в сеттере)
            }
        }
        writeResults(data);
        return bufferedReader;
    }

    public static int isInData(ArrayList<Commits> data, String nick)
    {
        for (int i = 0; i < data.size(); i++)
            if (data.get(i).getNickname().equals((nick)))
                return i;
        return -1;
    }

    //здесь можно рассмотреть все случаи первенств
    //три случая первенства: очевидное; больше трех людей в равных ситуациях; людей слишком мало (два человека)
    //— и результаты уже записать в results.txt
    //насчет второго случая: пусть выше в приоритете те, кто сделал коммит позже
    //логика такая: человек, сделавший один коммит в начале спринта, почти наверняка сделал меньше (и неважно, если он опоздал на пару минут со вторым коммитом)
    //чем тот, кто сделал коммит в конце спринта
    public static void writeResults(ArrayList<Commits> data) throws FileNotFoundException
    {
        try {
            FileOutputStream outputStream = new FileOutputStream("result.txt");
            String[] result = getResults(data);
            for (String i : result)
            {
                outputStream.write(i.getBytes());
                outputStream.write("\n".getBytes());
            }
            outputStream.close();
            System.out.println("Файл успешно записан: " + new File("result.txt").getAbsolutePath());
        } catch (IOException e) {
            System.err.println("Ошибка при записи файла: " + e.getMessage());
        }
    }

    public static String[] getResults (ArrayList<Commits> data)
    {
        if (data.isEmpty()) return new String[] {"Победителей нет."};
        else if (data.size() == 1) return new String[] {data.get(0).getNickname()};

        data.sort(Comparator
                .comparing(Commits::getCommitsCount).reversed()
                .thenComparing(Commits::getCommitDate, Comparator.reverseOrder()));

        String[] ans;
        if (data.size() == 2)
        {
            ans = new String[2];
            for (int i = 0; i < 2; i++)
                ans[i] = data.get(i).getNickname();
        }

        else
        {
            ans = new String[3];
            for (int i = 0; i < 3; i++)
                ans[i] = data.get(i).getNickname();
        }
        return ans;
    }
}