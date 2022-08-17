#pragma once

#include <QtWidgets/QWidget>
#include <QtCharts/QChartView>
#include <QtCharts/QPieSeries>
#include "ui_Gui.h"
#include "Service.h"

class Gui : public QWidget
{
    Q_OBJECT

public:
    Gui(Service& s,QWidget *parent = Q_NULLPTR);

private:
    Ui::GuiClass ui;
    Service& serv;

    void connectSignalsAndSlots();
    int getSelectedIndex();
    void populateGui();
    void deleteCoat();
    void addCoat();
    void updateCoat();
    bool fileSelector();
    void filterCoat();
    void populateUserGui();
    void transferController();
    void populateUserBasket();
    int userGetSelectedIndex();
    void showBasket();
    void adminModeSelector();
    void userModeSelector();
    void goToUser();
    void goToAdmin();
    void displayChart();

};
