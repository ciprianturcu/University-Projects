#pragma once
#include "DynamicVector.h"
#include "Coat.h"

class Repository
{
private:
	DynamicVector<Coat> arr;

public:
	Repository();
	~Repository();
	Coat* getAllCoats() const;
	bool addCoat(const Coat& new_coat);
	bool deleteCoat(const Coat& coat);
	bool updateCoat(const Coat& i_coat, const Coat& n_coat);
	int getArrSize() const;
	Coat getCoatFromPos(int pos) const;
	Coat getCoatByAtr(std::string size, std::string colour, double price, std::string photo_link);
};