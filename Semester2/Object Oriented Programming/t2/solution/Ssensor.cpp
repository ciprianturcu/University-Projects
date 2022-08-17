#include "Ssensor.h"

Ssensor::Ssensor()
{
    this->prod = "";
}

Ssensor::Ssensor(string str, std::vector<double> r)
{
    this->prod = str;
    this->record = r;
}

bool Ssensor::sendAlert() const
{
    for (auto r : record)
    {
        if (r > 1600)
            return true;
    }
    return false;
}

double Ssensor::getPrice() const
{
    return 25;
}

string Ssensor::toString() const
{
    string s = "smoke sensor , " + this->prod;
    for (auto v : record)
    {
        s += " , " + std::to_string(v);
    }
    s += "\n ";
    return s;
}
