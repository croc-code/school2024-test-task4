import re 
from datetime import datetime

'''
Проверяет считанное имя пользователя на соответствие требованиям:
латинские символы в любом регистре, цифры (но не в начале строки), а также символ "_".
Фунция принимает строку, которую необходимо проверить, возвращает булевое значение
'''
def check_name(user_name):
    return re.fullmatch(r'^(?!^\d)[a-zA-Z0-9_]+$', user_name)

'''
Проверяет считанный хэш коммита на соответствие требованиям:
строка в нижнем регистре, состоящая из 7 символов: букв латинского алфавита, а также цифр
Фунция принимает строку, которую необходимо проверить, возвращает булевое значение
'''
def check_hash(commit_hash):
    return re.fullmatch(r'^[a-z0-9]{7}$', commit_hash)

'''
Проверяет считанные дату и время коммита на соответствие формату YYYY-MM-ddTHH:mm:ss
Фунция принимает строку, которую необходимо проверить, возвращает булевое значение
'''
def check_date(commit_date):
    try:
        datetime.strptime(commit_date, '%Y-%m-%dT%H:%M:%S.%f')
        return True
    except ValueError:
        return False

'''
Проверяет данные в считанной строке на соответствие требованиям
Принимает строку, возвращает имя пользователя или False
'''
def check_line(line):
    if len(line.split()) != 3:
        return False
    user_name, commit_hash, commit_date = line.split()
    if check_name(user_name) and check_hash(commit_hash) and check_date(commit_date):
        return user_name
    else:
        return False

'''
Считывает файл с данными о коммитах
Проверяет правильность полученных данных
Формирует словарь с именами пользователей и количеством совершённых ими коммитов за период
Возвращает этот словарь
'''
def read_commits():
    dev_names = {}
    with open('commits.txt', 'r') as f:
        for line in f:
            user_name = check_line(line)
            if user_name:
                if user_name not in dev_names:
                    dev_names[user_name] = 0
                dev_names[user_name] += 1
            else:
                  print(f'Invalid data: {line}')  
            # if len(line.split()) == 3:
            #     user_name, commit_hash, commit_date = line.split()
            #     if check_name(user_name) and check_hash(commit_hash) and check_date(commit_date):
                    # if user_name not in dev_names:
                    #     dev_names[user_name] = 0
                    # dev_names[user_name] += 1
            #     else:
            #         print(f'Invalid data: {line}')
            # else:
            #     print(f'Invalid data: {line}')
    return dev_names

'''
Находит 3 самых активных пользователей
Принимает словарь {Имя пользователя : количество сделанных коммитов}
Возвращает список из 3 пользователей, сделавших больше всего коммитов (в порядке убывания места в рейтинге)
'''
def find_top(commits_dict):
    sorted_contributors = sorted(commits_dict.items(), key=lambda x: x[1], reverse=True)
    return list(map(lambda x: x[0], sorted_contributors))[:3]

'''
Записывает именах 3-х самых активных пользователей в файл result.txt
Принимает список с 3-мя пользователями
'''
def write_winners(winners):
    with open('result.txt', 'w') as f:
        f.write(str('\n'.join(winners)))

def main():
    commits_dict = read_commits()
    winners = find_top(commits_dict)
    write_winners(winners)

if __name__ == '__main__':
    main()