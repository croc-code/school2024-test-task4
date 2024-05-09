using System.Linq;

namespace TopContributors
{
    internal class Program
    {
        static void Main(string[] args)
        {
            const string inputFile = "commits.txt";
            const string outputFile = "result.txt";
            const int numberOfPlaces = 3;

            string projectPath = GetProjectPath();
            string inputPath = Path.Combine(projectPath, inputFile);
            string outputPath = Path.Combine(projectPath, outputFile);

            if (!File.Exists(inputPath))
            {
                throw new FileNotFoundException($"Missing {inputFile} in src directory!");
            }

            string[] contributions = File.ReadAllLines(inputPath);

            Rating rating = new Rating(numberOfPlaces); 
            Dictionary<string, int> topContributors = rating.GetOrderedContributionRating(contributions);

            StreamWriter output = new StreamWriter(outputPath);
            output.Write(string.Join(" ", topContributors.Keys));
            output.Close();
        }

        static string GetProjectPath()
        {
            string processPath = Directory.GetCurrentDirectory();
            string projectPath = Directory.GetParent(processPath).Parent.Parent.FullName;
            return projectPath;
        }
    }
}
