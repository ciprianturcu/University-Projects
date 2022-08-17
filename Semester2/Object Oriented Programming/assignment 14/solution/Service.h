#pragma once
#include "Repository.h"
#include "Basket.h"
#include "ValidateData.h"
#include "ValidateInputData.h"
#include "UndoRedo.h"
#include <memory>

class Service
{
private:
    Repository repo;
    Basket* basket;
    InputValidator vi;
    CoatValidator v;
    std::string filename;
    std::vector<std::shared_ptr<UndoRedo>> undoAdmin;
    std::vector<std::shared_ptr<UndoRedo>> redoAdmin;
    std::vector<std::shared_ptr<UndoRedo>> undoUser;
    std::vector<std::shared_ptr<UndoRedo>> redoUser;


public:
    //constructors/destructors
    Service(Repository r,Basket* b, std::string filename);
    ~Service();
    Service();
    Service(const Service& s);

    void undoServiceAdmin();
    void undoServiceUser();
    void redoServiceAdmin();
    void redoServiceUser();

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
    void lowerQuantity(int pos);
    void readFromFile();
    void writeToFile();


};
