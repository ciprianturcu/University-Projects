#include "Tsensor.h"

Tsensor::Tsensor()
{
	this->diam = 0;
	this->len = 0;
	this->prod = "";
}

Tsensor::Tsensor(string str, std::vector<double> r, double dim, double len)
{
	this->prod = str;
	this->record = r;
	this->diam = dim;
	this->len = len;
}

bool Tsensor::sendAlert() const
{
	int cnt = 0;
	for (auto r : record)
	{
		if (r < 10 || r>30)
			cnt++;
		if (cnt >= 2)
		{
			return true;
		}
	}
	return false;
}

double Tsensor::getPrice() const
{
    int price = 0;
    if (diam < 3 && len < 50)
        price = price + 8;
    price += 9;
    return price;

}

string Tsensor::toString() const
{
	string s = "temp sensor , " + this->prod + " , " + "diam: " + to_string(this->diam) + " , " + "len: " + to_string(this->len);
	for (auto v : record)
	{
		s += " , " + std::to_string(v);
	}
	s += "\n ";
	return s;
}
