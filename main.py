import heapq
from validators import validate_commit


def read_commits(input_file: str) -> dict:
    """
    A function to read the commits from a file.
    :return: a dictionary with contributors and their commits count.
    """
    commits = {}
    with open(input_file, 'r') as file:
        for line in file:
            line = line.strip()
            if validate_commit(line):
                contributor = line.split()[0]
                commits[contributor] = commits.get(contributor, 0) + 1
    return commits


def write_winners(winners: list, output_file: str) -> None:
    """ A function to write the winners to a file. """
    with open(output_file, 'w') as file:
        for winner in winners:
            file.write(winner + '\n')


def find_winners(commits: dict, winners_count: int) -> list:
    """
    A function to find the most active contributors.
    :return: a list of the most active contributors in descending order.
    """
    winners_heap = []
    for contributor, commits_count in commits.items():
        # Collect the necessary number of winners.
        if len(winners_heap) < winners_count:
            heapq.heappush(winners_heap, (commits_count, contributor))
        else:
            # Replace the contributor with the least commits count if the current contributor has more commits.
            if commits_count > winners_heap[0][0]:
                heapq.heappop(winners_heap)
                heapq.heappush(winners_heap, (commits_count, contributor))

    sorted_winners = sorted(winners_heap, key=lambda x: (x[0]))
    winners_list = [winner[1] for winner in sorted_winners]
    return winners_list


if __name__ == '__main__':
    commits_file = 'commits.txt'
    result_file = 'result.txt'
    number_of_winners = 3

    commits_dict = read_commits(commits_file)
    winners = find_winners(commits_dict, number_of_winners)
    write_winners(winners, result_file)
