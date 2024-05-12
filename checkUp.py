#Этот модуль содержит функции для фильтрации невалидных коммитов
import re
import shutil

def is_valid_commit(commit):
    """
    Проверяет, соответствует ли строка коммита заданному шаблону.

    Аргументы:
    commit (str): Строка коммита.

    Возвращает:
    bool: True, если строка коммита соответствует шаблону, False в противном случае.
    """
    # Шаблон(регулярка) для проверки строки коммита
    return re.fullmatch(
        r'^[a-zA-Z_][a-zA-Z0-9_]* [a-f0-9]{7} \d{4}-\d{2}-\d{2}T\d{2}:\d{2}:\d{2}.\d{3}$', commit
    ).group(0) == commit


def commitCheckUp(source):
    """
    Функция фильтрует невалидные коммиты из файла и перезаписывает его.

    Аргументы:
    source (str): Путь к файлу с коммитами.
    """
    try:
        # Открываем файл с коммитами и считываем данные
        with open(source, "r") as file:
            commits = file.readlines()

        # Открываем временный файл для записи валидных коммитов
        with open('temp_commits.txt', "w") as temp_file:
            # Проверяем каждую строку на соответствие шаблону
            for commit in commits:
                if is_valid_commit(commit):
                    temp_file.write(commit)

        shutil.copyfile('temp_commits.txt', source)


    except FileNotFoundError:
        print(f"Файл {source} не найден")
    except IOError:
        print(f"Ошибка ввода-вывода при работе с файлом {source}")
