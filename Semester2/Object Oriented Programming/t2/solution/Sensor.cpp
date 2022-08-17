#include "Sensor.h"

Sensor::Sensor()
{
}

string Sensor::toString() const
{
    string s = this->prod;
    for (auto v : record)
    {
        s += " , " + std::to_string(v);
    }
    s += "\n ";
    return s;
}
