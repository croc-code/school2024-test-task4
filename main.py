from dataclasses import dataclass
from collections import Counter
from datetime import datetime
import re


@dataclass(frozen=True)
class Constants:
    """
    Класс констант, который содержит необходимые константы для скрипта.

    Attributes:
        res_file (str): Дефолтное имя результурющего файла.
        username_pattern (str): Паттерн регулярных выражений для проверки имен пользователей.
        commit_hash_pattern (str): Паттерн регулярных выражений для проверки хэшей коммита.
        date_pattern (str): Шаблон формата даты для парсинга дат.
        top_users (int): Количество ведущих участников, которые будут включены в список.
    """

    res_file = 'result.txt'
    username_pattern = r'^[a-zA-Z][a-zA-Z0-9_]*$'
    commit_hash_pattern = r'^[a-f0-9]{7}$'
    date_pattern = '%Y-%m-%dT%H:%M:%S'
    top_users = 3


def validate_data(date_str: str) -> bool:
    """
    Проверьте, имеет ли данная строка даты правильный формат и корректную дату.

    Args:
        date_str (str): Строка, содержащая дату, подлежащая проверке.

    Returns:
        bool: True, если строка даты является допустимой, иначе False.
    """
    try:
        date = datetime.strptime(date_str, Constants.date_pattern)

        if date > datetime.now():
            return False

    except ValueError:
        return False

    else:
        return True


def validate_line(line: list) -> bool:
    """
    Проверка на соответствие строки данных ожидаемому формату.

    Args:
        line (list): Список элементов из строки входных данных.

    Returns:
        bool: True, если список является допустимым, иначе False..
    """
    if len(line) != 3:
        return False

    if not re.match(Constants.username_pattern, line[0]):
        return False

    if not re.match(Constants.commit_hash_pattern, line[1]):
        return False

    if not validate_data(line[2]):
        return False

    return True


def form_res_file(t_contributors: list) -> None:
    """
    Записывает имена пользователей лучших участников в файл с названием "result.txt".

    Args:
        t_contributors (list): Список кортежей, содержащих лучших участников и количество их коммитов.
    """

    try:
        with open(Constants.res_file, 'w') as file:
            file.write('\n'.join([pair[0] for pair in t_contributors]))

    except Exception as e:
        print(f"<form_res_file> Error writing to file: {e}")


def read_commits_file(input_file: str) -> list:
    """
     Читает данные о коммитах из файла и возвращает список уникальных пользователей.

     Args:
         input_file (str): Путь к входному файлу, содержащему данные о коммитах.

     Returns:
         list: Список уникальных пользователей, упомянутых в файле коммитов.
    """

    try:
        users = []

        with open(input_file, 'r') as file:
            for line in file:
                elements = line.rstrip('\n').split()

                if not validate_line(elements):
                    continue

                users.append(elements[0])

        return users

    except FileNotFoundError:
        print(f"<top3_contributors> Input file '{input_file}' not found.")
        return []

    except Exception as e:
        print(f"<top3_contributors> An error occurred: {e}")
        return []


def top_contributors(input_file: str) -> None:
    """
    Находит лучших участников из входного файла и записываеи результаты в 'result.txt'.

    Args:
        input_file (str): Путь к входному файлу, содержащему данные о коммитах.
    """
    t_contributors = Counter(read_commits_file(input_file)).most_common(Constants.top_users)

    form_res_file(t_contributors)


top_contributors('commits.txt')
