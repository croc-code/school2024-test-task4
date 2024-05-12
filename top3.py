from datetime import datetime
from collections import Counter
from os import path
import re


class Commit:
    """класс для коммита"""

    def __init__(self, line: str):
        'Конструктор принимает строку, которую разделяет, а затем проверяет данные на выполнение требований'
        separated = line.split()  # разделяем

        if len(separated) != 3:  # проверка на содержание всей информации о коммите
            raise ValueError("incomplete data")
        self.name, self.hash, self.date = separated  # сохраняем имя, хэш и дату
        if not re.match("^[a-zA-Z_][\da-zA-Z_]*?", self.name):  # проверяем имя пользователя
            raise ValueError("invalid username")
        elif not re.match("^[\da-z]{7}?", self.hash):  # проверяем хэш
            raise ValueError("invalid commit hash")

        self.date = datetime.fromisoformat(
            self.date)  # преобразуем строку содержащую дату в объект datetime(в случае некорректной даты, будет вызвана ошибка)


def top3() -> None:
    'функция считывает содержимое файла commits.txt и создаем файл result.txt с топ 3 сотрудниками по количеству коммитов'
    if path.exists("commits.txt"):  # проверка на существование файла
        staff = Counter()  # счетчик для коммитов сотрудников
        current_date = datetime.now()  # сохраняем текущее время

        with open("commits.txt", "r") as file:  # открываем файл
            for line in file:  # построчно считываем содержимое файла
                commit = Commit(line)  # создаем объект commit
                different = (
                            current_date - commit.date).days  # определяем сколько дней назад(от дня запуска функции) был сделан коммит
                if different > 0 and different <= 4 * 7:  # проверка на вхождение в нужный период
                    staff[commit.name] += 1  # добавляем к количеству коммитов данного сотрудника единицу

        with open("result.txt", "w") as resultFile:  # создаем файл для результата
            resultFile.write('\n'.join([staffer[0] for staffer in staff.most_common(
                3)]))  # извлекаем из staff 3 имени сотрудников с наибольшим количеством коммитов, записываем в файл
    else:
        raise FileNotFoundError("Файл commits.txt не найден")


if __name__ == "__main__":  # если запускается файл с кодом, то запускаем функцию top3
    top3()
