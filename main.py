import heapq


def find_winners(input_file: str, output_file: str, winners_count: int) -> None:
    """
    A function to find the most active contributors.

    The function takes an input file with commit data,
    finds the most active contributors and writes their names to a new file.
    """
    # Read the input file and count the number of commits for each contributor
    commits = {}
    with open(input_file, 'r') as file:
        for line in file:
            line = line.strip()
            if line:
                contributor = line.split()[0]
                commits[contributor] = commits.get(contributor, 0) + 1

    # Find the most active contributors
    winners_heap = []
    for contributor, commits_count in commits.items():
        if len(winners_heap) < winners_count:
            heapq.heappush(winners_heap, (commits_count, contributor))
        else:
            if commits_count > winners_heap[0][0]:
                heapq.heappop(winners_heap)
                heapq.heappush(winners_heap, (commits_count, contributor))

    # Write the most active contributors to the output file in descending order
    sorted_winners = sorted(winners_heap, key=lambda x: (x[0]))
    winners = [winner[1] for winner in sorted_winners]
    with open(output_file, 'w') as file:
        for winner in winners:
            file.write(winner + '\n')


if __name__ == '__main__':
    commits_file = 'commits.txt'
    result_file = 'result.txt'
    number_of_winners = 3
    find_winners(commits_file, result_file, number_of_winners)
