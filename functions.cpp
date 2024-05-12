#include "functions.h"
void help() {
	std::cout << "input 3 argument :" << '\n';
	std::cout << "program.exe inputFile.txt outputFile.txt"<<'\n';
}
bool isArgCorrect(std::ifstream& in, std::ofstream& out, int argc) {
	if (argc < 2) { std::cout << "can't find input file'\n'"; help(); return false; }
	if (argc < 3) { std::cout << "can't find output file'\n'"; help(); return false; }
	if (!in) { std::cout << "can't read input file'\n'"; return false; }
	if (!out) { std::cout << "can't write to output file'\n'"; return false; }
	return true;
}
bool isDateCorrect(uint32_t year, uint32_t month, uint32_t day) {
	int a = (14 - month) / 12;
	int y = year + 4800 - a;
	int m = month + 12 * a - 3;
	int JD = day + (153 * m + 2) / 5 + 365 * y + y / 4 - y / 100 + y / 400 - 32045;

	a = JD + 32044;
	int b = (4 * a + 3) / 146097;
	int c = a - 146097 * b / 4;
	int d = (4 * c + 3) / 1461;
	int e = c - (1461 * d) / 4;
	m = (5 * e + 2) / 153;
	int cd = e - (153 * m + 2) / 5 + 1;
	int cm = m + 3 - 12 * (m / 10);
	int cy = 100 * b + d - 4800 + m / 10;

	if (cd != day || cm != month || cy != year) { return false; }
	return true;
}