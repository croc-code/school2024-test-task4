import os
import datetime
from collections import Counter


def top_contributors(input_file):
    if not os.path.isfile(input_file):
        print(f"Файл {input_file} не найден.")
        return

    with open(input_file, 'r') as file:
        commit_data = [line.strip().split() for line in file if line.strip()]
    # Проходим по каждому элементу в списке данных о коммитах
    for commit in commit_data:
        # Проверяем, что каждый коммит имеет три элемента (имя пользователя, хэш коммита и время коммита)
        if len(commit) != 3:
            print(f"Неверный формат данных: {' '.join(commit)}")
            return
        username, commit_hash, commit_time = commit

        # Проверка корректности формата имени пользователя
        if not all(char.isalnum() or char == '_' for char in username) or username[0].isdigit():
            # Если имя пользователя имеет неверный формат, выводим сообщение об ошибке и завершаем выполнение функции
            print(f"Неверный формат имени пользователя: {username}")
            return
        # Проверяем корректность формата хэша коммита
        # Если хэш коммита имеет неверный формат, выводим сообщение об ошибке и завершаем выполнение функции
        if len(commit_hash) != 7 or not commit_hash.islower() or not commit_hash.isalnum():
            print(f"Неверный формат хэша коммита: {commit_hash}")
            return
        # Разбираем дату и время коммита с помощью двух возможных форматов
        try:
            datetime.datetime.strptime(commit_time, '%Y-%m-%dT%H:%M:%S')
        except ValueError:
            try:
                datetime.datetime.strptime(commit_time, '%Y-%m-%dT%H:%M:%S.%f')
            except ValueError:
                # Если формат даты и времени коммита неверен, выводим сообщение об ошибке и завершаем выполнение функции
                print(f"Неверный формат даты и времени коммита: {commit_time}")
                return

    # Подсчитываем количество коммитов для каждого пользователя
    commit_counts = Counter(username for username, _, _ in commit_data)

    # Находим имена трех пользователей с наибольшим количеством коммитов
    top_three_contributors = [username for username, _ in commit_counts.most_common(3)]

    # Записываем результаты в выходной файл
    output_file = 'result.txt'
    with open(output_file, 'w') as file:
        for contributor in top_three_contributors:
            file.write(contributor + '\n')


# Вызов процедуры с путем к файлу с данными о коммитах
top_contributors('commits.txt')
