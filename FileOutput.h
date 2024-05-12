#pragma once

//функция вывода имён трёх лучших конрибьютеров в конечный файл result

#include "LagestElement.h"
#include <fstream>
#include <iostream>
#include <vector>
#include <unordered_map>

int resultOutput(std::vector<int> *CommitsCount, std::unordered_map<std::string, int>* Hash_table) { 
    int iter = 0;
    int MAX = searchFirstLargest(CommitsCount);

    std::ofstream out("result.txt"); //проверка на жизнь
    if (!out.is_open()) {
        std::cout << "File not exist.";
        return -1;
    }

    while (MAX > 0) {
        for (auto i = Hash_table->begin(); i != Hash_table->end(); i++) //работа с итераторoм i и счётчиком iter))
        {
            if (i->second == MAX) {
                out << i->first << "\t";  //просто продолжаем работать с итераторами
                iter++;
                i->second;
            }
            if (iter == 3) break; //условия для 3-х человек
        }
        MAX--;
        if (iter == 3) break; //и здесть так же
    }

    out.close(); //закончили
    return 1;
}
