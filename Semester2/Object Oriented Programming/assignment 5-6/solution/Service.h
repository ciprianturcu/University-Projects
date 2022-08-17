#pragma once
#include "Repository.h"

class Service
{
private:
	Repository repo;

public:
	//constructors/destructors
	Service(Repository r);
	~Service();
	Service();
	Service(const Service& s);

	//admin
	bool addToRepo(std::string size, std::string colour, double price, double quantity, std::string photo_link);
	bool deleteCoat(const Coat& c);
	bool updateCoat(std::string size, std::string colour, double price, double quantity, std::string photo_link, int position);
	Coat* getCoats() const;
	int getSize() const;
	void initializeRepo();


	

	

	
};
