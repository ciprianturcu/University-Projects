#include "Repository.h"
#include "RepositoryExceptions.h"
#include <string>
#include <assert.h>
#include <algorithm>
#include <fstream>


Repository::Repository() {
}

Repository::~Repository() {
}

std::vector<Coat> Repository::getAllCoats() const {
    return arr;
}

void Repository::addCoat(const Coat& new_coat) {
    int position = arr.size();
    int check_repo = getPosOfCoat(new_coat);
    if (check_repo != -1) {
        throw DuplicateCoat();
    }
    arr.push_back(new_coat);

}

void Repository::deleteCoat(const Coat& coat) {
    int size = arr.size();
    auto it = std::remove(arr.begin(), arr.end(), coat);
    arr.erase(it, end(arr));
    if (arr.size() != size - 1)
        throw InexistentCoat();
}

void Repository::updateCoat(const Coat& i_coat, const Coat& n_coat) {
    int pos = getPosOfCoat(i_coat);
    if (pos == -1)
        throw InexistentCoat();
    arr.at(pos) = n_coat;
}

int Repository::getArrSize() const {
    return arr.size();
}

Coat Repository::getCoatFromPos(int pos) const {
    return arr[pos];
}

Coat Repository::getCoatByAtr(std::string size, std::string colour, double price, std::string photo_link) {
    for (auto i : arr) {

        if (i.getSize() == size && i.getColour() == colour && i.getPrice() == price && i.getLink() == photo_link)
            return i;
    }
    return Coat();
}

void Repository::readFromFile(std::string filename)
{
    std::ifstream f(filename);
    if (!f.is_open())
        return;
    Coat c{};
    while (f >> c)
        addCoat(c);
    f.close();

}

void Repository::writeToFile(std::string filename)
{
    std::ofstream f(filename, std::ios::in);
    if (!f.is_open())
        return;
    for (auto c : arr)
    {
        f << c;
    }
    f.close();
}

int Repository::getPosOfCoat(const Coat& c) const {
    int cnt = 0;
    for (auto i : arr) {
        if (i == c) {
            return cnt;
        }
        cnt++;
    }
    return -1;
}




