#pragma once

#include "Coat.h"
#include <vector>
#include <string>

class CoatException
{
private:
	std::vector<std::string> errors;
public:
	CoatException(std::vector<std::string> errors);
	std::vector<std::string> getErrors() const;
};

class CoatValidator
{
public:
	CoatValidator();
	static void validate(const Coat& c);

};
