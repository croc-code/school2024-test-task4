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

            string projectPath = Directory.GetCurrentDirectory();
            string inputPath = Path.Combine(projectPath, inputFile);

            if (!File.Exists(inputPath))
            {
                projectPath = DebugProjectPath();
                inputPath = Path.Combine(projectPath, inputFile);
            }
            if (!File.Exists(inputPath))
            {
                throw new FileNotFoundException($"Missing {inputFile} in src directory!");
            }

            string outputPath = Path.Combine(projectPath, outputFile);


            string[] contributions = File.ReadAllLines(inputPath);

            Rating rating = new Rating(numberOfPlaces); 
            string[] topContributors = rating.GetOrderedContributionRating(contributions);

            StreamWriter output = new StreamWriter(outputPath);
            output.Write(string.Join(" ", topContributors));
            output.Close();
        }

        static string DebugProjectPath()
        {
            string processPath = Directory.GetCurrentDirectory();
            string projectPath = Directory.GetParent(processPath).Parent.Parent.FullName;
            return projectPath;
        }
    }
}
