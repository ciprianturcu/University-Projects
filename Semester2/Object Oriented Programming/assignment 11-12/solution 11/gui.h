#pragma once

#include <QtWidgets/QMainWindow>
#include "qwidget.h"
#include "qboxlayout"
#include "qformlayout.h"
#include "qpushbutton.h"
#include "qlabel.h"
#include "qtabwidget.h"
#include "qlineedit.h"
#include "qlistwidget.h"
#include "ui_gui.h"
#include <vector>
#include "Repository.h"

class gui : public QWidget
{
private:
    QOBJECT_H
    Repository repo;
    QListWidget* objectsList;
    QListWidget* filteredObjects;
    QLineEdit* filterBox;
    QPushButton* filter;

public:
    gui(QWidget *parent = 0);

    void initGui();

    void populateGui();

    void connectRelation();

    void filterFunc();


};
