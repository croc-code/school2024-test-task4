# Импортируем функции для обработки данных о коммитах из модуля contributorsTop
from contributorsTop import read_commits, result
from checkUp import commitCheckUp
# Путь к файлу с данными о коммитах
source = "commits.txt"

# Путь к файлу, в который будут записаны результаты
destination = "result.txt"

# Проверка файла commits.txt на наличие невалидных коммитов и их фильтрацию в случае их наличия
commitCheckUp(source)

# Читаем данные о коммитах из файла
read_commits(source)

# Записываем результаты анализа коммитов в файл
result(destination, read_commits(source))