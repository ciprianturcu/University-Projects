#include "Coat.h"
#include <string>
#include <iostream>

Coat::Coat() : size(""), colour(""), price(0.0), quantity(0.0), photo_link("")
{

}

Coat::Coat(const std::string _size, const std::string _colour, const double _price, const double _quantity, const std::string _photo_link) : size{ _size }, colour{ _colour }, price{ _price }, quantity{ _quantity }, photo_link{_photo_link}
{
}

Coat::Coat(const Coat& copy)
{
	this->size = copy.size;
	this->colour = copy.colour;
	this->price = copy.price;
	this->quantity = copy.quantity;
	this->photo_link = copy.photo_link;
}

bool Coat::operator==(const Coat& c)
{
	if(this->size!=c.size || this->colour!=c.colour || this->photo_link!=c.photo_link || this->price!=c.price)
		return false;
	return true;
}

std::string Coat::coat_to_string() const
{
	std::string coat = "Size: " + getSize() + ",Colour: " + getColour() + ",Price: " + std::to_string(getPrice()) + ",Quantity: " + std::to_string(getQuantity()) + ",Link: " + getLink();
	return coat;
}

void Coat::display_link() const
{
	ShellExecuteA(NULL, NULL, "chrome.exe", this->photo_link.c_str(), NULL, SW_SHOWMAXIMIZED);
}
