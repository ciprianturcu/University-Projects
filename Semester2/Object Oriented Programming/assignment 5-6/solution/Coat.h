#pragma once
#include <string>
#include <Windows.h>
#include <stdlib.h>


class Coat {
private:
	std::string size;
	std::string colour;
	double price;
	double quantity;
	std::string photo_link;

public:
	//constructors
	Coat();
	Coat(const std::string size, const std::string colour, const double price, const double quantity, const std::string link); //copy constructor
	Coat(const Coat& copy);

	//geters
	std::string getSize() const { return this->size; }
	std::string getColour() const { return this->colour; }
	double getPrice() const { return this->price; }
	double getQuantity() const { return this->quantity; }
	std::string getLink() const { return this->photo_link; }
	//seters
	void setSize(const std::string size) { this->size = size; }
	void setColour(const std::string colour) { this->colour = colour; }
	void setPrice(const double price) { this->price = price; }
	void setQuantity(const double quantity) { this->quantity = quantity; }
	void setLink(const std::string photo_link) { this->photo_link = photo_link; }

	bool operator==(const Coat& c);

	std::string coat_to_string() const;
	void display_link() const;



};