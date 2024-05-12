# Функция для обработки данных из файла
def process_commits(file_name):
    commit_data = {}
    with open(file_name, 'r') as file:
        for line in file:
            user, _, _ = line.split(maxsplit=2)  # Разделение строки на части
            commit_data[user] = commit_data.get(user, 0) + 1  # Подсчет коммитов для пользователя
    return commit_data

# Функция для записи результатов в файл
def write_result(file_name, top_contributors):
    with open(file_name, 'w') as file:
        for contributor in top_contributors:
            file.write(contributor + '\n')

if __name__ == "__main__":
    commits_file = "commits.txt"
    result_file = "result.txt"

    # Обработка данных из файла с коммитами
    commit_data = process_commits(commits_file)

    # Нахождение топ-3 контрибьютеров
    top_contributors = sorted(commit_data, key=commit_data.get, reverse=True)[:3]

    # Запись результатов в файл
    write_result(result_file, top_contributors)
