#include "CVSBasket.h"
#include <fstream>
#include <Windows.h>

CVSBasket::CVSBasket(std::string filepath)
{
	this->current = 0;
	this->sum = 0;
	this->filepath = filepath;
}

void CVSBasket::writeToFile()
{
	std::ofstream f(this->filepath);
	
	if (!f.is_open())
		throw FileException("The file could not be opened.");
	for (auto c : this->coats)
	{
		f << c;
	}
	f.close();
}

void CVSBasket::displayBasket() const
{
	std::string command = "start excel.exe " + this->filepath;
	system(command.c_str());
	
}
