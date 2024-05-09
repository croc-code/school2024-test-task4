using System;
using System.Text.RegularExpressions;

namespace TopContributors
{
    internal class Rating
    {
        public int NumberOfPlaces { get; }

        public Rating(int numberOfPlaces)
        {
            NumberOfPlaces = numberOfPlaces;
        }

        public string[] GetOrderedContributionRating(string[] contributions)
        {
            string[] bestContributors = contributions
                // only unique entries
                .Distinct()
                .Where(IsValidEntry)
                // take only name
                .Select(x => x.Split()[0])
                .GroupBy(name => name)
                .OrderByDescending(g => g.Count())
                .Take(NumberOfPlaces)
                .Select(g => g.Key)
                .ToArray();

             return bestContributors;
        }

        private bool IsValidEntry(string entry)
        {
            const string pattern = @"^[a-zA-Z_]\w*\s[a-z0-9]{7}\s\d{4}-\d{2}-\d{2}T\d{2}:\d{2}:\d{2}\.?\d*$";
            Regex regex = new Regex(pattern);
            
            return regex.IsMatch(entry);
        }
    }
}
