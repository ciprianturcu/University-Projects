#pragma once
#include "service.h"

typedef struct
{
	Service* service;
}Ui;

Ui* createUI(Service* s);
void destroyUi(Ui* ui);

void startUi(Ui* ui);