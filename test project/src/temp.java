//import java.io.*;
//import java.lang.*;
//import java.text.SimpleDateFormat;
//import java.time.LocalDate;
//import java.time.format.DateTimeFormatter;
//import java.util.Date;
//import java.util.HashMap;
//import java.util.Map;
//
//public class Main {
//    public static void main(String[] args)
//    {
//        //файл должен лежать в одной папке с приложением
//        try {
//            BufferedReader bufferedReader = getBufferedReader();
//            bufferedReader.close();
//        } catch (IOException e) {
//            System.err.println("Ошибка чтения файла: " + e.getMessage());
//        }
////
////        HashMap<String, Integer> hashMap = new HashMap<>();
////
////        hashMap.put("C", 3);
////        hashMap.put("A", 1);
////        hashMap.put("B", 2);
////        hashMap.put("D", 2);
////        hashMap.put("E", 1);
////
////        hashMap.entrySet().stream()
////                .sorted(Map.Entry.<String, Integer>comparingByValue().reversed());
////
////        int i = 0;
////        for (String key : hashMap.keySet())
////        {
////            System.out.println("Ключ: " + key);
////            i++;
////            if (i == 3) break;
////        }
//
//    }
//
//    //считаем, что раз файл commits.txt создается сам, то проверку данных не устраиваем
//    public static BufferedReader getBufferedReader() throws IOException
//    {
//        HashMap<String, Integer> data = new HashMap<>();
//        File file = new File("out/production/test project/commits.txt");
//        FileReader fileReader = new FileReader(file);
//        BufferedReader bufferedReader = new BufferedReader(fileReader);
//
//        String line;
//        while ((line = bufferedReader.readLine()) != null)
//        {
//            String[] tmp = line.split(" ");
//            String name = tmp[0];
//            if (!isActualDate(tmp[2])) continue;
//
//            if (data.containsKey(name))
//                data.put(name, data.get(name) + 1);
//            else data.put(name, 1);
//        }
//        getResults(data);
//
//        return bufferedReader;
//    }
//
//    //не сказано, что выгружаются коммиты только за последние 4 недели
//    //так что проверяем сами
//    public static boolean isActualDate(String date)
//    {
//        String temp = date.substring(0, 10);
//        LocalDate currentDate = LocalDate.now();
//        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
//        LocalDate targetDate = LocalDate.parse(temp, formatter);
//        return (targetDate.isAfter(currentDate.minusWeeks(4)));
//    }
//
//    //здесь можно рассмотреть все случаи первенств
//    //три случая первенства: очевидное; больше трех людей в равных ситуациях; людей слишком мало (два человека)
//    //— и результаты уже записать в results.txt
//    //насчет второго случая: пусть выше в приоритете те, кто сделал коммит позже
//    // учитывать картину предыдущих спринтов неподходяще (праздники, отпуска, больничные...)
//    public static void getResults(HashMap<String, Integer> data) throws FileNotFoundException
//    {
//        try {
//            FileOutputStream outputStream = new FileOutputStream("out/production/test project/result.txt");
//            // Далее можно использовать outputStream для записи данных в файл
//            // Например:
//            String dat = "Пример текста для записи в файл.";
//            outputStream.write(dat.getBytes());
//            outputStream.close(); // Не забудьте закрыть поток после завершения операций
//            System.out.println("Файл успешно записан.");
//        } catch (IOException e) {
//            System.err.println("Ошибка при записи файла: " + e.getMessage());
//        }
//        if (data.size() <= 3)
//        {
//            for (int i = 0; i < data.size(); i++)
//            {
//
//            }
//        }
//
//    }
//}