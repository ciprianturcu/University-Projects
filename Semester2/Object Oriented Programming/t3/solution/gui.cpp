#include "gui.h"
#include "service.h"
#include "qmessagebox.h"


gui::gui(Service& s, QWidget* parent)
    : QWidget(parent), s(s)
{
    ui.setupUi(this);
    this->populate();
    this->connect();
}

void gui::populate()
{
    this->ui.listWidget->clear();
    for (auto e : this->s.getAll())
    {
        
        QString itemR = QString::fromStdString(e.toString());
        QListWidgetItem* item = new QListWidgetItem{ itemR };
        if (e.getDel() > 0)
        {
            item->setBackground(QColor("green"));
        }
        this->ui.listWidget->addItem(item);
    }
}

void gui::add()
{
    double texta = stod(this->ui.lineEdit->text().toStdString());
    double textb = stod(this->ui.lineEdit_2->text().toStdString());
    double textc = stod(this->ui.lineEdit_3->text().toStdString());
    if (texta != 0)
    {
        this->s.add(texta, textb, textc);
        this->populate();
    }
    else
    {
        QMessageBox::critical(this, "ERROR", "a cannot be 0");
        return;
    }
    this->ui.lineEdit->clear();
    this->ui.lineEdit_2->clear();
    this->ui.lineEdit_3->clear();
}

void gui::connect()
{
    QObject::connect(this->ui.pushButton, &QPushButton::clicked, this, &gui::add);
    QObject::connect(this->ui.pushButton_2, &QPushButton::clicked, this, &gui::compute);
}

void gui::compute()
{
    int index = this->getSelectedIndex();
    if (index == -1)
    {
        QMessageBox::critical(this, "ERROR", "no eq selected");
        return;
    }
    else
    {
        float x1 = this->s.computex1(this->s.getAll()[index]);
        float x2 = this->s.computex2(this->s.getAll()[index]);
        this->ui.textEdit->setText(QString::fromStdString(to_string(x1)));

    }

}

int gui::getSelectedIndex()
{
    QModelIndexList selectedIndexes = this->ui.listWidget->selectionModel()->selectedIndexes();
    if (selectedIndexes.size() == 0)
    {
        return -1;
    }
    int selectedIndex = selectedIndexes.at(0).row();
    return selectedIndex;
}
