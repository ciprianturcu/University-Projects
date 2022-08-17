
#pragma once
#include "Coat.h"
#include <vector>
#include <algorithm>

class Repository
{
private:
    std::vector<Coat> arr;

public:
    Repository();
    ~Repository();
    int getPosOfCoat(const Coat& c)const;
    std::vector<Coat> getAllCoats() const;
    void addCoat(const Coat& new_coat);
    void deleteCoat(const Coat& coat);
    void updateCoat(const Coat& i_coat, const Coat& n_coat);
    int getArrSize() const;
    Coat getCoatFromPos(int pos) const;
    Coat getCoatByAtr(std::string size, std::string colour, double price, std::string photo_link);
    void readFromFile(std::string filename);
    void writeToFile(std::string filename);

};