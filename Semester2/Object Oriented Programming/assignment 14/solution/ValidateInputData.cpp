#include "ValidateInputData.h"

InputException::InputException(std::vector<std::string> errors) : errors{ errors }
{
}

std::vector<std::string> InputException::getErrors() const
{
	return this->errors;
}

InputValidator::InputValidator()
{
}

void InputValidator::validate(std::string price, std::string quantity)
{
	std::vector<std::string> errors;
    if (price.find_first_not_of("0123456789.") != std::string::npos)
    {
        errors.push_back( std::string("Price not a double.\n"));
    }
    if (quantity.find_first_not_of("0123456789.") != std::string::npos)
    {
        errors.push_back(std::string("Quantity not a double.\n"));
    }
    if (errors.size() > 0)
        throw InputException(errors);
}
