#ifndef MYLIB_HR
#define MYLIB_HR
#include "functions.h"
#include <unordered_map>
class DataBase {
	/*std::set<std::string> S;*/
	std::unordered_map<std::string, uint32_t> M;
public:
	DataBase() = default;
	void push(std::string& name) {
		/*auto it = S.find(name);
		if (it != S.end()) { ++M[name]; }
		else { S.insert(name); ++M[name]; }*/
		M[name] += 1;
	}
	void up3JuniorToMiddle(std::ofstream& out) {
		int count = 3;
		if (M.size() < 3) { count = M.size(); }
		while (count != 0) {
			int max = 0;
			std::string name;
			--count;
			for (auto it = M.begin(); it != M.end(); ++it) {
				if (it->second > max) {
					max = it->second;
					name = it->first;
				}
			}
			if (count == 0) { out << name; }
			else { out << name << ' '; }
			M[name] = 0;
		}
	}
};
#endif MYLIB_HR