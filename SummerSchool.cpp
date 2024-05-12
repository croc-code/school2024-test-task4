#include <iostream>
#include <fstream>
#include "functions.h"
#include"structs.h"
int main(int argc, const char* argv[])
{
    std::ifstream in(argv[1]);
    std::ofstream out(argv[2]);
    
    if (!isArgCorrect(in, out, argc)) { return -1; }
    DataBase D;
    int x = 0;
    int line = 1;
    while ((x = in.get()) != EOF) {
        std::string name; 
        if (!(x > 64 && x < 91 || x > 96 && x < 123 || x == 95)) { std::cout << "incorrect name at line: " << line; return -2; }
        while (x != ' ') { 
            if (!(x > 64 && x < 91 || x > 96 && x < 123 || x > 47 && x < 58 || x == 95)) { std::cout << "incorrect name at line: " << line; return -2; }
            name.push_back(char(x)); x = in.get();
        }
        uint32_t counter = 0;
        while (x == ' ') { x = in.get(); }
        while (x != ' ' && (x > 47 && x < 58 || x > 96 && x < 123)) { x = in.get(); ++counter; }
        if (counter != 7) { std::cout << "incorrect hash at line: " << line; return -2; }
        uint32_t year=0;
        uint32_t month=0;
        uint32_t day=0;
        in >> year;
        in.ignore();
        in >> month;
        in.ignore();
        in >> day;
        in.ignore();
        ++line;
        if (!isDateCorrect(year, month, day)) { std::cout << "incorrect date at line: " << line; return -2; }
        uint32_t hours = 0;
        uint32_t minutes = 0;
        double seconds = 0;
        in >> hours;
        in.ignore();
        in >> minutes;
        in.ignore();
        in >> seconds;
        while (in.peek() == '\r' || in.peek() == '\n' || in.peek() == ' ') { in.ignore(); }
        if (hours > 24 || hours < 0) { std::cout << "incorrect hours value at line: " << line; return -2; }
        if (minutes > 60 || minutes < 0) { std::cout << "incorrect minutes value at line: " << line; return -2; }
        if (seconds > 60.0 || minutes < 0.0) { std::cout << "incorrect second value at line: " << line; return -2; }
        D.push(name);
        //3n
    }
    D.up3JuniorToMiddle(out);
    in.close();
    out.close();
}
