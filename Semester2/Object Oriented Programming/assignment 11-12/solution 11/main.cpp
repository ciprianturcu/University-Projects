#include "Gui.h"
#include "stdafx.h"
#include <QtWidgets/QApplication>

int main(int argc, char *argv[])
{
    QApplication a(argc, argv);
    gui w;
    w.show();
    return a.exec();
}
