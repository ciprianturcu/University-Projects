#include "Hsensor.h"

Hsensor::Hsensor()
{
	this->prod = "";
}

Hsensor::Hsensor(string str, std::vector<double> r)
{
	this->prod = str;
	this->record = r;
}

bool Hsensor::sendAlert() const
{
	int cnt=0;
	for (auto r : record)
	{
		if (r < 30 || r>85)
			cnt++;
		if (cnt >= 2)
		{
			return true;
		}
	}
	return false;
}

double Hsensor::getPrice() const
{
	return 4;
}

string Hsensor::toString() const
{
	string s = "humidity sensor , " + this->prod;
	for (auto v : record)
	{
		s += " , " + std::to_string(v);
	}
	s += "\n ";
	return s;
}
