#pragma once
#include <string>
#include <vector>
using namespace std;

class Sensor
{
protected:
	string prod;
	std::vector<double> record;

public:
	Sensor();
	virtual bool sendAlert() const = 0;
	virtual double getPrice() const = 0;
	virtual string toString() const;

};