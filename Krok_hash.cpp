#include <unordered_map>
#include <vector>
#include <fstream>
#include "FileOutput.h"
#include "StringLength.h"

int main()
{
    
    std::unordered_map<std::string, int> Hash_table; //задаём хеш-таблицу с парой [имя, кол-во коммитов] 
                                                                            //    [key,      value     ]
    std::ifstream in("commits.txt"); //открываем поток на получение данных из commits.txt
    if (!in.is_open()) {             //проверка на жизнь
        std::cout << "File not found.";
        return -1;
    }
    
    int lines = countString(in); //кол-во строк в файле
   
    in.close();

    in.open("commits.txt"); //заново открываем файлик commits.txt
    std::string word;

    for (int i = 0; i < lines; i++) { //цикл на получение первого слова без пробелов(имени) из каждой строки
        in >> word;
        if(!Hash_table[word]) //если такого нет
        {
            Hash_table[word] = 1; //создаём
        }
        else
        {
            Hash_table[word]++; //если есть, увеличиваем счётчик на 1
        }

        in >> word; //пропускаем два ненужных нам слова в i-той строке
        in >> word;
    }
    
    //for (auto elem : Hash_table) {          // цикл поэлементно для Hash_table
    //        std::cout << elem.first /*первый элемент из пары в Hash_table*/ << "\t" << elem.second /*второй элемент из пары в Hash_table*/ << std::endl;
    //    }
    // 
    //for (int i = 0; i < Hash_table.size(); i++)
    //{
    //    std::cout << name[i] << "\t" << CommitsCount[i] << std::endl;
    //} //существуют для отладки, делают одно и то же, только разными способами



    std::vector<int> CommitsCount; //задаём вектор количества коммитов
    CommitsCount.reserve(Hash_table.size());
                                            //и выделяем на них место в памяти

    for (auto data : Hash_table) { //заполение векторов
        CommitsCount.push_back(data.second);
    }
    
    resultOutput(&CommitsCount, &Hash_table); //функция из файла FileOutput.h
    
    return 0;
}
