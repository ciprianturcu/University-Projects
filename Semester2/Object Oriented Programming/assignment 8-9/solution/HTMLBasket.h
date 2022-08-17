#pragma once
#include "Basket.h"
class HTMLBasket : public Basket
{
private:
	std::string filepath;
public:
	HTMLBasket(std::string filepath);
	void writeToFile() override;
	void displayBasket() const override;
};