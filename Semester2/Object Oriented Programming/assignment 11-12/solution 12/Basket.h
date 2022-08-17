#pragma once
#include "Repository.h"
#include "RepositoryExceptions.h"
#include <vector>

class Basket
{
protected:
    std::vector<Coat> coats;
    int current;
    int sum;
    std::string filename;

public:
    Basket();
    virtual ~Basket() {}

    void add(const Coat& c);
    Coat getCurrent();
    void next();
    bool isEmpty();
    int getSum();
    std::vector<Coat> getAllCoats();
    virtual void writeToFile() = 0;
    virtual void displayBasket() const = 0;


};

