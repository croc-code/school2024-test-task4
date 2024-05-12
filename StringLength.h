#pragma once

#include <fstream>
#include <string>

int countString(std::ifstream& inMyStream) { //подсчёт количества строк в файле
    std::string line;

    int c = 0; //переменная колиства строк

    if (inMyStream.is_open())
    {

        while (getline(inMyStream, line)) { //если не дало из потока, значит 0

            c++;
        }
    }
    inMyStream.close();
    return c;
}
