import re
from collections import Counter

# Создаем пустые списки для имен и дат
names = []
dates = []

# Открываем файл 'commits.txt' в режиме чтения
f = open('commits.txt', 'r')

# Читаем файл построчно
for line in f:
    # Заполняем списки из имен и дат
    names.append(' '.join(re.findall(r'^[a-zA-Z_][a-zA-Z0-9_]*\s', line)))
    dates.append(' '.join(re.findall(r'\d{4}-\d{2}-\d{2}T\d{2}:\d{2}:\d{2}.\d+$', line)))

# Закрываем файл
f.close()

# Создание счетчика для подсчета количества упоминаний каждого имени
counter = Counter(names)

# Открываем файл 'result.txt' в режиме записи
with open('result.txt', 'w') as file:
    # Записываем топ 3 контребьютера, удаляя последний пробел
    file.write(' '.join(list(counter.keys())[:3])[:-1])
