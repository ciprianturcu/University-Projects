#include "gui.h"
#include <QtWidgets/QApplication>
#include "service.h"

int main(int argc, char *argv[])
{
    QApplication a(argc, argv);
    const Repo& r = Repo();
    Service s = Service(r);
    gui w{s};
    w.show();
    return a.exec();
}
