#pragma once
#include "Service.h"
#include <iostream>


class Ui {
private:
    Service service;
    int mode;
public:
    Ui();
    
    void choose_file();
    void choose_mode();

    void start_app();

    void readCoatContents(std::string* size, std::string* colour, std::string* price, std::string* quantity, std::string* link);

    //admin
    void start_admin();
    void admin_menu();
    void add_coat_admin();
    void remove_coat_admin();
    void update_coat_admin();
    void show_all();

    //user
    void start_user();
    void user_menu();

    void parse_coats();
    void see_basket();



};

