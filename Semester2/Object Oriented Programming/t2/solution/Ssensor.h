#pragma once
#include "Sensor.h"
#include <string>
class Ssensor : public Sensor
{
public:
	Ssensor();
	Ssensor(string str, std::vector<double> r);
	bool sendAlert() const override;
	double getPrice() const override;
	string toString() const override;
};