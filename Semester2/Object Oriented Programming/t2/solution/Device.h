#pragma once
#include "Sensor.h"
#include "Ssensor.h"
#include "Hsensor.h"
#include "Tsensor.h"
#include <fstream>
#include <algorithm>
class Device
{
private:
	bool haswifi;
	std::vector<Sensor*> sensors;
public:
	Device();
	void addSensor(Sensor* s);
	std::vector<Sensor*> getAll();
	std::vector<Sensor*> getAlertingSensors();
	void writeTofile(string filename);
	double getPrice();
	void addEnt();
};