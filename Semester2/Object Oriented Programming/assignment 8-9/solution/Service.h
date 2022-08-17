#pragma once
#include "Repository.h"
#include "Basket.h"
#include "ValidateData.h"
#include "ValidateInputData.h"

class Service
{
private:
    Repository repo;
    Basket* basket;
    InputValidator vi;
    CoatValidator v;
    std::string filename;

public:
    //constructors/destructors
    Service(Repository r,Basket* b, std::string filename);
    ~Service();
    Service();
    Service(const Service& s);

    //admin
    bool addToRepo(std::string size, std::string colour, std::string price, std::string quantity, std::string photo_link);
    bool deleteCoat(const Coat& c);
    bool updateCoat(std::string size, std::string colour, std::string price, std::string quantity, std::string photo_link, int position);
    std::vector<Coat> getCoats() const;
    int getSize() const;
    void initializeRepo();

    //user
    std::vector<Coat> filterCoatsBySize(std::string size);
    
    
    void addToBasket(const Coat& c);
    int getBasketSum();
    std::vector<Coat> getCoatsFromBasket();
    int getBasketSize();
    void showBasket();
    void readFromFile(std::string filename);
    void writeToFile(std::string filename);


};
