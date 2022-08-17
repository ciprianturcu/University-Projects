#include "Coat.h"
#include <string>
#include <iostream>
#include <vector>
#include <sstream>
#include <string>

Coat::Coat() : size(""), colour(""), price(0.0), quantity(0.0), photo_link("")
{

}

Coat::Coat(const std::string _size, const std::string _colour, const double _price, const double _quantity, const std::string _photo_link) : size{ _size }, colour{ _colour }, price{ _price }, quantity{ _quantity }, photo_link{ _photo_link }
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
    if (this->size != c.size || this->colour != c.colour || this->photo_link != c.photo_link || this->price != c.price)
        return false;
    return true;
}

std::string Coat::coat_to_string() const
{
    std::string coat = "Size: " + getSize() + ",Colour: " + getColour() + ",Price: " + std::to_string(getPrice()) + ",Quantity: " + std::to_string(getQuantity()) + ",Link: " + getLink();
    return coat;
}

std::string Coat::user_coat_to_string() const
{
    std::string coat = "Size: " + getSize() + ",Colour: " + getColour() + ",Price: " + std::to_string(getPrice()) + ",Quantity: " +std::to_string(getQuantity());
    return coat;
}

void Coat::display_link() const
{
    ShellExecuteA(NULL, NULL, "chrome.exe", this->photo_link.c_str(), NULL, SW_SHOWMAXIMIZED);
}

std::vector<std::string> tokenize(std::string str, char delimiter)
{
    std::vector<std::string> result;
    std::stringstream ss(str);
    std::string token;
    while (getline(ss, token, delimiter))
        result.push_back(token);

    return result;
}

std::istream& operator>>(std::istream& is, Coat& c)
{
    std::string line;
    getline(is, line);
    std::vector<std::string> tokens = tokenize(line, ',');
    if (tokens.size() != 5)
        return is;
    c.setSize(tokens[0]);
    c.setColour(tokens[1]);
    c.setPrice(stod(tokens[2]));
    c.setQuantity(stod(tokens[3]));
    c.setLink(tokens[4]);

    return is;
}

std::ostream& operator<<(std::ostream& os, const Coat& c)
{
    os << c.getSize() << "," << c.getColour() << "," << std::to_string(c.getPrice()) << "," << std::to_string(c.getQuantity()) << "," << c.getLink() << "\n";
    return os;

}
