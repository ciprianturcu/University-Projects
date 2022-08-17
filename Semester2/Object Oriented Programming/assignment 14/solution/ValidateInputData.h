#pragma once
#include "Coat.h"
#include <vector>
#include <string>

class InputException
{
private:
	std::vector<std::string> errors;
public:
	InputException(std::vector<std::string> errors);
	std::vector<std::string> getErrors() const;
};

class InputValidator
{
public:
	InputValidator();
	static void validate(std::string price, std::string quantity);
};