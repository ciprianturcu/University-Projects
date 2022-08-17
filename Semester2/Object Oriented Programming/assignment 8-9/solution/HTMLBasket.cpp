#include "HTMLBasket.h"
#include <fstream>
#include <Windows.h>

HTMLBasket::HTMLBasket(std::string filepath)
{
	this->current = 0;
	this->sum = 0;
	this->filepath = filepath;
}

void HTMLBasket::writeToFile()
{
	std::ofstream f(this->filepath);
	if (f.is_open())
	{
		f << "<!DOCTYPE html>\n<html>\n<head>\n<title>Shopping Basket</title>\n</head>\n<body>\n<table border = \"1\">\n<tr>\n<td>Size</td>\n<td>Colour</td>\n<td>Price</td>\n<td>Quantity</td>\n<td>Link</td>\n</tr>\n";
		for (auto c : this->coats)
		{
			f << "<tr>\n";
			f << "<td>" << c.getSize() << "</td>\n";
			f << "<td>" << c.getColour() << "</td>\n";
			f << "<td>" << c.getPrice() << "</td>\n";
			f << "<td>" << c.getQuantity() << "</td>\n";
			f << "<td>" << c.getLink() << "</td>\n";
			f << "</tr>\n";

		}
		f << "</table>\n</body>\n</html>\n";
		f.close();
	}
}

	

void HTMLBasket::displayBasket() const
{
	std::string command = "start chrome.exe " + this->filepath;
	system(command.c_str());
}
