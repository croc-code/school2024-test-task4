#ifndef MYLIB_H
#define MYLIB_H
#include <iostream>
#include <fstream>
#include <string>
#include <vector>
#include <map>
#include <set>
bool isArgCorrect(std::ifstream& in, std::ofstream& out, int argc);
bool isDateCorrect(uint32_t year, uint32_t month, uint32_t day);
#endif