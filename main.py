from collections import defaultdict
from datetime import datetime, timedelta
from dataclasses import dataclass
from typing import List
import re
import logging
from os import path


@dataclass(frozen=True)
class Constants:
    target_file = 'commits.txt'  # имя файла откуда считываем коммиты
    result_file = 'result.txt'  # имя файла куда записываем результат
    cnt_winner = 3  # кол-во победителей
    current_date = datetime.now()  # текущее время
    time_pattern = '%Y-%m-%dT%H:%M:%S'  # паттерн для datetime
    cnt_competition_days = 28  # продолжительность игры в днях
    regular_expression = re.compile(
        r'\b[a-zA-z_]\w*\b \b[a-z0-9]{7}\b \b\d{4}-\d{2}-\d{2}T\d{2}:\d{2}:\d{2}')  # регулярное выражение


logging.basicConfig(filename='log.txt', filemode='w')  # логер для логирования ошибок


def check_date(date: str) -> bool:  # проверка на корректную дату
    try:
        commit_date = datetime.strptime(date, Constants.time_pattern)
        time_diff = Constants.current_date - commit_date
        return time_diff <= timedelta(days=Constants.cnt_competition_days)
    except ValueError:  # ловим exception если проблемы с датой
        return False


def file_read(user_dict: dict[str, set], filename: str) -> None:  # открываем файл для чтения
    with open(filename, 'r') as f:
        for line in f:
            line = line.strip()
            regular_line = Constants.regular_expression.match(line)
            if not regular_line:  # проверяем совпадает ли строка с регулярным выражением
                logging.error(f'Invalid login in "{line}"\n')
                continue

            user_name, commit_hash, commit_date = regular_line.group(0).split()

            if check_date(commit_date):  # проверяем корректность даты
                user_dict[user_name].add(commit_hash)
            else:
                logging.error(f'Invalid date in "{line}"\n')


def file_write(winner_list: List[str], filename: str) -> None:  # открываем файл для записи
    with open(filename, 'w') as f:
        for winner_name in winner_list:
            f.write(f"{winner_name}\n")


'''
Алгоритм поиска n лучших результатов за O(n) по скорости
Мы поочередно проходимся по массиву и если встречаем элемент больше чем минимальный в winners, то заменяем его.
Если в winners меньше элементов, чем нам нужно, то просто добавляем элементы без проверок
'''


def winner_search(user_dict: dict[str, set]) -> List[str]:  # ищем 3 победителей
    winners = []
    for key, value in user_dict.items():
        value_len = len(value)
        if len(winners) < Constants.cnt_winner:
            winners.append((key, value_len))
            winners.sort(key=lambda x: x[1], reverse=True)
        elif winners[-1][1] < value_len:
            winners[-1] = (key, value_len)
            winners.sort(key=lambda x: x[1], reverse=True)

    winners = list(map(lambda x: x[0], winners))
    return winners


def main() -> None:
    if not path.exists(Constants.target_file):
        logging.critical(f'{Constants.target_file} file does not exist!')
        return
    count_users_commits = defaultdict(set)
    file_read(count_users_commits, Constants.target_file)
    winners = winner_search(count_users_commits)
    file_write(winners, Constants.result_file)


if __name__ == '__main__':
    main()
