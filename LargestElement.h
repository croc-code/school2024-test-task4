#pragma once

#include <vector>

int searchFirstLargest(std::vector<int>* vct) { //обычная функция для поиска наибольшего элемента в массиве, 
    int max = 0;                                //что будет соответствовать наиболшему количеству коммитов
    for (int i = 0; i < vct->size(); i++)
    {

        if (vct->at(i) > max)
        {
            max = vct->at(i);
        }
    }
    return max;
}
