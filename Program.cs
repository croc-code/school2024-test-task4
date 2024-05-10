using System.Runtime.CompilerServices;
using System.Runtime.Serialization.Formatters;
using BountyService.Services;
using BountyService.Utils;
using Microsoft.Extensions.DependencyInjection;
using Microsoft.Extensions.Hosting;
using Microsoft.Extensions.Logging;


HostApplicationBuilder builder = Host.CreateApplicationBuilder(args);

builder.Services.AddLogging();
builder.Services.AddTransient<CommitsParseService>();
builder.Logging.AddConsole();

using IHost host = builder.Build();

// Get best emplyees
var be = new BestEmployees(host.Services.GetRequiredService<CommitsParseService>());

be.Write(be.Get());


public class BestEmployees(CommitsParseService parseService)
{
    private const int userSliceLen = 3; // count of users to be in top

    public IEnumerable<KeyValuePair<string, int>>? Get()
    {
        var employeesStats = parseService.ParseCommits();
        var topEmployees = employeesStats.OrderByDescending(pair => pair.Value).Take(userSliceLen);
        return topEmployees;
    }

    /// <summary>
    /// Write top employees to file
    /// </summary>
    public void Write(IEnumerable<KeyValuePair<string, int>>? topEmployees)
    {
        FileSaver.WriteToFile(topEmployees);
    }
}




