#include "Device.h"

Device::Device()
{
	this->haswifi = false;
	this->sensors = {};
}

void Device::addSensor(Sensor* s)
{
	this->sensors.push_back(s);
}

std::vector<Sensor*> Device::getAll()
{
	return this->sensors;
}

std::vector<Sensor*> Device::getAlertingSensors()
{
	std::vector<Sensor*> alerting;
	for (auto s : sensors)
	{
		if (s->sendAlert())
			alerting.push_back(s);
	}
	return alerting;
}

void Device::writeTofile(string filename)
{
	ofstream f(filename);
	if (f.is_open())
	{
		f << to_string(getPrice()) + "\n";
		f << to_string(this->haswifi) + "\n";
	
		for (auto s : sensors)
		{
			f << s->toString();
		}
		f.close();
	}
}

double Device::getPrice()
{
	int price = 0;
	for (auto s : sensors)
	{
		price += s->getPrice();
	}
	return price;
}

void Device::addEnt()
{
	std::vector<double> recordings;
	std::vector<double> recordings1;
	recordings.push_back(100);
	recordings.push_back(40);
	recordings.push_back(42);
	recordings.push_back(1);
	recordings.push_back(10000);
	recordings.push_back(39);
	recordings.push_back(85);
	recordings1.push_back(50);
	recordings1.push_back(40);
	recordings1.push_back(42);
	recordings1.push_back(60);
	recordings1.push_back(82);
	recordings1.push_back(40);
	recordings1.push_back(75);
	Ssensor* s1 = new Ssensor("mircea", recordings);
	Tsensor* s2 = new Tsensor("cristi", recordings, 20, 100);
	Tsensor* s3 = new Tsensor("cristi", recordings, 1, 10);
	Hsensor* s4 = new Hsensor("andrei", recordings1);
	this->sensors.push_back(s1);
	this->sensors.push_back(s2);
	this->sensors.push_back(s3);
	this->sensors.push_back(s4);
}
