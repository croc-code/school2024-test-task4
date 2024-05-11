import re
import pandas as pd


def check_format(row):
    """
    функция для проверки данных коммита на соответствование требованиям
    :param row: на вход подается строка из датафрейма
    :return: true если коммит не соответствует условию, false если коммит верный
    """
    pattern = r'^([A-Za-z_][A-Za-z0-9_]+) ([a-f0-9]{7}) (\d{4}-\d{2}-\d{2}T\d{2}:\d{2}:\d{2})$'
    if re.match(pattern, row):
        return True
    else:
        return False


# преобразование данных в датафрейм
df = pd.read_csv('commits.txt', sep=' ', header=None, names=['author', 'hash', 'date'])

# фильтрация данных по условию
df['format_check'] = df.apply(lambda x: check_format(f"{x['author']} {x['hash']} {x['date']}"), axis=1)
wrong_commits = df[df['format_check'] == True]

# подсчет топ3 коммитеров
top_3 = df['author'].value_counts().head(3)


with open('result.txt', 'w', encoding='utf8') as result:
    for _, (author, _) in enumerate(top_3.items()):
        result.write(author + ' ')

