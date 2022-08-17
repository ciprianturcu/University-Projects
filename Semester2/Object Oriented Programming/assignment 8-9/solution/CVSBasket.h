#pragma once
#include "Basket.h"

class CVSBasket : public Basket
{
private:
	std::string filepath;
public:
	CVSBasket(std::string filepath);
	void writeToFile() override;
	void displayBasket() const override;
	
};