using System.Runtime.CompilerServices;
using BountyService.Utils;
using Microsoft.Extensions.Logging;

namespace BountyService.Services;

public class CommitsParseService
{
    private string _commitsFilePath;
    private ILogger _logger;
    private Dictionary<string, int> _commitOwners; // countains each user's commits amount

    public CommitsParseService(ILogger<CommitsParseService> logger)
    {
        this._logger = logger;
        this._commitOwners = new Dictionary<string, int>();
        this._commitsFilePath = "commits.txt";
    }


    /// <summary>
    /// Returns top <userSliceLen> employees ordered by descending
    /// </summary>
    public IEnumerable<KeyValuePair<string, int>>? GetTopEmployees(Dictionary<string, int> dict, in int userSliceLen)
    {
        var topUsers = dict.OrderByDescending(pair => pair.Value).Take(userSliceLen);
        foreach (var pair in topUsers)
        {
            _logger.LogDebug($"top user {pair.Key}: {pair.Value}");
        }
        return topUsers;
    }

    /// <summary>
    /// Returns disctionary of owners that countains each user's commits amount
    /// </summary>
    public Dictionary<string, int> ParseCommits()
    {
        List<string> commitLines;

        try
        {
            commitLines = File.ReadAllLines(_commitsFilePath).ToList(); // Read 'commits.txt' file
        }
        catch (FileNotFoundException)
        {
            _logger.LogError("File not found!");
            return _commitOwners;
        }

        foreach (string line in commitLines)
        {
            string[] commitData = line.Split(' ');
            string username = commitData[0];

            // Check if username is valid
            if (UserValidator.IsUsernameValid(username))
            {
                if (_commitOwners.ContainsKey(username))
                {
                    _commitOwners[username]++;
                }
                else
                {
                    _commitOwners[username] = 1;
                }
            }
        }

        return _commitOwners;
    }
}