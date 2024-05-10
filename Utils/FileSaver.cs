namespace BountyService.Utils;

public static class FileSaver
{
    /// <summary>
    /// Write top employees to file locally with filename.
    /// Default filename value - "results.txt"
    /// </summary>
    public static void WriteToFile(IEnumerable<KeyValuePair<string, int>>? topEmployees, string filename = "results.txt")
    {
        // create or open file to write results
        using (StreamWriter writer = File.CreateText(filename))
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