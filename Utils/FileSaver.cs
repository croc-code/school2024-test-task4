namespace BountyService.Utils;

public static class FileSaver
{
    public static void WriteToFile(IEnumerable<KeyValuePair<string, int>>? topEmployees)
    {
        // Создаем или открываем файл для записи результатов
        using (StreamWriter writer = File.CreateText("results.txt"))
        {
            // Записываем в файл имена из топ 3 в алфавитном порядке
            foreach (var pair in topEmployees.OrderBy(пара => пара.Key))
            {
                writer.Write(pair.Key);
                writer.Write(" ");
            }
        }
    }
}