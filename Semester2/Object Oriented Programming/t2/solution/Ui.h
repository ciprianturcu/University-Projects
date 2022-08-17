#pragma once
#include "Device.h"
#include "Tsensor.h"
#include "Ssensor.h"
#include "Hsensor.h"
#include <iostream>
using namespace std;
class Ui
{
private:
	Device dev;
public:
	Ui(Device d);

	void start_ui();

};