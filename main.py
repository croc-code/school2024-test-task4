import os
from datetime import date, timedelta, datetime


#функция для проверки корректности имени пользователя
def checking_username(username:str)->bool:
    if username[0].isdigit() or not all(char.isalnum() or char == "_" for char in username):
        return False
    return True


#функия для проверки корректности хэша коммита
def checking_hash(hash:str)->bool:
    if hash.isalnum() and len(hash)==7:
        return True
    return False


#функия для проверки, что коммит сделан в течении 4 недель 
def checking_date(date_commit:str, days_ago:int = 28)->bool:
    temp = date_commit.split('T')
    temp = datetime.strptime(temp[0],"%Y-%m-%d").date()
    date_today = date.today()
    four_week = timedelta(days_ago)
    if date_today-four_week<=temp<date_today:
        return True
    return False


#функция которая получает список имен пользователей входящих в топ-3 
def get_top3(dict_info:dict[str,int])->list[str]:
    dict_sorted = dict(sorted(dict_info.items(), key=lambda item: item[1], reverse=True))
    keys = list(dict_sorted.keys())
    i =0 
    count = 0
    names_winners = []
    flg_divide = False
    while i<len(dict_sorted)-1 and (count<3 or flg_divide):
        names_winners.append(keys[i])
        if dict_sorted[keys[i]]==dict_sorted[keys[i+1]]:
            flg_divide = True
        else:
            flg_divide = False
        i+=1
        count+=1

    if flg_divide or count<3:
        names_winners.append(keys[-1])
        
    return names_winners


#функция, которая из полученных данных из файла возвращает словарь, соответствующий требованиям,
#где ключ это имя пользователя, а значение количество коммитов
def get_data(file):
    dict_info = {}
    info_commit = file.readline()
    while info_commit:
        temp = info_commit.split()
        if len(temp)==3:
            if checking_username(temp[0]) and checking_hash(temp[1]) and checking_date(temp[2]):
                if temp[0] not in dict_info:
                    dict_info[temp[0]]=1
                else:
                    dict_info[temp[0]]+=1
        info_commit = file.readline()
        
    return dict_info

#процедура, которая создает файл "result.txt" со списком имен
def create_result_file(file):
    dict_info = get_data(file)
    if dict_info:
        names_winners = get_top3(dict_info)
        with open("result.txt","w+") as res_file:
            for i in names_winners:
                res_file.write(i+"\n")     
    else:
        print("Файл с коммитами пуст или коммиты не соответствуют требованиям.")
        with open("result.txt", "w") as res_file:
            pass
    return


if __name__ == "__main__":
    if "commits.txt" in os.listdir():
        with open("commits.txt", "r") as file:
            create_result_file(file)
    else:   
        print("Файл commits.txt не найден")
