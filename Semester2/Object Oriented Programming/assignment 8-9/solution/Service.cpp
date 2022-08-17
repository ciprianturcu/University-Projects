#include "Service.h"
#include <string>
#include "ValidateData.h"
#include <algorithm>
#include <vector>
#include <iostream>

Service::Service(Repository repo, Basket* b, std::string filename)
{
    this->repo = repo;
    this->filename = filename;
    this->basket = b;
}

Service::~Service()
{
}

Service::Service()
{
}

Service::Service(const Service& s)
{
    this->repo = s.repo;
    this->basket = s.basket;
    this->v = s.v;
    this->filename = s.filename;
    this->vi = s.vi;
}

bool Service::addToRepo(std::string size, std::string colour, std::string price, std::string quantity, std::string photo_link)
{
    this->vi.validate(price, quantity);
    Coat c = Coat(size, colour, stod(price), stod(quantity), photo_link);
    this->v.validate(c);
    repo.addCoat(c);
    return true;
}

bool Service::deleteCoat(const Coat& c)
{
    this->v.validate(c);
    if (c.getQuantity() != 0.00)
        return false;
    repo.deleteCoat(c);
    this->repo.writeToFile(this->filename);
    return true; 
}

bool Service::updateCoat(std::string size, std::string colour, std::string price, std::string quantity, std::string photo_link, int position)
{
    this->vi.validate(price, quantity);
    if (position > repo.getArrSize() || position < 0)
        return false;
    Coat c = repo.getCoatFromPos(position);
    Coat u = Coat(size, colour, stod(price), stod(quantity), photo_link);
    
    this->v.validate(u);
    repo.updateCoat(c, u);
    this->repo.writeToFile(this->filename);
    return true;
}

std::vector<Coat> Service::getCoats() const
{
    return repo.getAllCoats();
}

int Service::getSize() const
{
    return repo.getArrSize();
}

void Service::initializeRepo()
{
    this->repo.readFromFile(this->filename);
}

std::vector<Coat> Service::filterCoatsBySize(std::string size)
{
    std::string empty = "";
    if (size.compare(empty) == 0)
        return this->repo.getAllCoats();
    this->v.validate(Coat(size, "", 0, 0, "https"));
    int count_coats = 0;
    std::vector<Coat> coats = this->getCoats();
    std::vector<Coat> list;
    auto it = std::copy_if(coats.begin(), coats.end(), std::back_inserter(list), [size](const Coat& c) {return c.getSize() == size; });
    return list;
}

void Service::addToBasket(const Coat& c)
{
    basket->add(c);
    return;
}

int Service::getBasketSum()
{
    return basket->getSum();
}

std::vector<Coat> Service::getCoatsFromBasket()
{
    return basket->getAllCoats();
}

int Service::getBasketSize()
{
    return basket->getAllCoats().size();
}

void Service::showBasket()
{
    basket->writeToFile();
    basket->displayBasket();
    return;
}


void Service::readFromFile(std::string filename)
{
    this->repo.readFromFile(filename);
}

void Service::writeToFile(std::string filename)
{
    this->repo.writeToFile(filename);
}


