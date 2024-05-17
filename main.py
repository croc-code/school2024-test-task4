import re


def find_winners(commits_file: str, result_file: str, winner_count: int) -> None:
    users = {}

    # Шаблон строки - сведений о коммите
    sample = re.compile(r'^(([a-zA-Z][a-zA-z0-9_]*)\s[a-z0-9]{7}\s\d{4}-\d{2}-\d{2}T\d{2}:\d{2}:\d{2}(.\d{3})?)$')

    # Считываем данные из файла commits_file
    with open(commits_file, 'r') as f:
        for line in f:
            if sample.match(line):
                username: str = line.split()[0]
                if username in users.keys():
                    users[username] += 1
                else:
                    users[username] = 1
            else:
                print(f"Ошибка в формате строки:{line}строка игнорируется")

    # Находим топ-3 пользователей
    top_users = sorted(users, key=users.get, reverse=True)[:winner_count]

    # Записываем топ пользователей в файл
    with open(result_file, 'w') as f:
        for user in top_users:
            f.write(user + '\n')

    print(f'Топ пользователей записан в {result_file}')


if __name__ == '__main__':
    find_winners('commits.txt', 'result.txt', 3)

