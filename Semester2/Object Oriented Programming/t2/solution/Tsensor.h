#pragma once
#include "Sensor.h"
class Tsensor : public Sensor
{
private:
	double diam;
	double len;
public:
	Tsensor();
	Tsensor(string str, std::vector<double> r, double dim, double len);
	bool sendAlert() const override;
	double getPrice() const override;
	string toString() const override;
};