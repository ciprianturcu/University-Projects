#include "repo.h"
#include <fstream>

Repo::Repo()
{
    this->readFile();
}

void Repo::readFile()
{
    std::ifstream f("data.in");
    if (!f.is_open())
        return;
    Equation e{};
    while (f >> e)
        arr.push_back(e);
    f.close();
}
