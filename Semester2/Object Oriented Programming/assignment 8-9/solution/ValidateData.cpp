#include "ValidateData.h"
#include <algorithm>

CoatException::CoatException(std::vector<std::string> errors) : errors{ errors }
{
}

std::vector<std::string> CoatException::getErrors() const
{
    return this->errors;
}

CoatValidator::CoatValidator()
{
}

void CoatValidator::validate(const Coat& c)
{
    std::vector<std::string> errors;
    auto size = c.getSize();
    auto colour = c.getColour();
    auto link = c.getLink();
    if (!(size == "XXS" || size == "XS" || size == "S" || size == "M" || size == "L" || size == "XL" || size == "XXL"))
        errors.push_back(std::string("\nSize doesn't exist!\n"));
    if (std::any_of(colour.begin(), colour.end(), ::isdigit))
        errors.push_back(std::string("\nColour contains digits!\n"));
    if (link.find("https") != 0)
        errors.push_back(std::string("\nLink should begin with https.\n"));

    if (errors.size() > 0)
        throw CoatException(errors);

}       
