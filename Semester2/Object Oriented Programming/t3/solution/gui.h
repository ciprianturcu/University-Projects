#pragma once

#include <QtWidgets/QWidget>
#include "ui_gui.h"
#include "service.h"

class gui : public QWidget
{
    Q_OBJECT

public:
    gui(Service& s,QWidget *parent = Q_NULLPTR);

private:
    Ui::guiClass ui;
    Service& s;
    void populate();
    void add();
    void connect();
    void compute();
    int getSelectedIndex();
};
