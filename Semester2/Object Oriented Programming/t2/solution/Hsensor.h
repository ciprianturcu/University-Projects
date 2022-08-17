#pragma once
#include "Sensor.h"
class Hsensor : public Sensor
{
public:
	Hsensor();
	Hsensor(string str, std::vector<double> r);
	bool sendAlert() const override;
	double getPrice() const override;
	string toString() const override;
};