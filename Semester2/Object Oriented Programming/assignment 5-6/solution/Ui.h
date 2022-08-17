#pragma once
#include "Service.h"
#include <iostream>


class Ui {
private:
	Service service;
	int mode;
public:
	Ui(Repository repo);
	void initialize_app();
	void choose_mode();
	void start_app();

	//admin
	void start_admin();
	void admin_menu();
	void add_coat_admin();
	void remove_coat_admin();
	void show_all();


};