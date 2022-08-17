#include "domain.h"
#include <vector>
#include <sstream>

std::vector<std::string> tokenize(std::string str, char delimiter)
{
    std::vector<std::string> result;
    std::stringstream ss(str);
    std::string token;
    while (getline(ss, token, delimiter))
        result.push_back(token);

    return result;
}

std::istream& operator>>(std::istream& is, Equation& e)
{
    string line;
    getline(is, line);
    vector<string> tokens = tokenize(line, ',');
    if (tokens.size() != 3)
        return is;
    e.setA(stod(tokens[0]));
    e.setB(stod(tokens[1]));
    e.setC(stod(tokens[2]));
    return is;


}