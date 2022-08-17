#include "gui.h"
#include "stdafx.h"

using namespace std;


gui::gui(QWidget* parent)
    : QWidget(parent)
{
    this->initGui();
    this->populateGui();
    this->connectRelation();
}

void gui::initGui()
{
    QHBoxLayout* mainLayout = new QHBoxLayout(this);
    QTabWidget* tabs = new QTabWidget();
    QWidget* admin = new QWidget();
    QHBoxLayout* adminLayout = new QHBoxLayout(admin);
    QVBoxLayout* adminFunctionsLayout = new QVBoxLayout();
    QFormLayout* objectsListLayout = new QFormLayout();
    QLabel* listLabel = new QLabel("Objects");
    objectsList = new QListWidget();
    objectsList->setSelectionMode(QAbstractItemView::SingleSelection);
    listLabel->setBuddy(objectsList);

    objectsListLayout->addWidget(listLabel);
    objectsListLayout->addWidget(objectsList);

    adminFunctionsLayout->addLayout(objectsListLayout);

    QGridLayout* editsLayout = new QGridLayout();

    QLabel* attr1 = new QLabel("1.Size: ");
    QLabel* attr2 = new QLabel("2.Colour: ");
    QLabel* attr3 = new QLabel("3.Price: ");
    QLabel* attr4 = new QLabel("4.Quantity: ");
    QLabel* attr5 = new QLabel("5.Link: ");

    QLineEdit* line1 = new QLineEdit();
    QLineEdit* line2 = new QLineEdit();
    QLineEdit* line3 = new QLineEdit();
    QLineEdit* line4 = new QLineEdit();
    QLineEdit* line5 = new QLineEdit();

    attr1->setBuddy(line1);
    attr2->setBuddy(line2);
    attr3->setBuddy(line3);
    attr4->setBuddy(line4);
    attr5->setBuddy(line5);

    editsLayout->addWidget(attr1, 0, 0);
    editsLayout->addWidget(line1, 0, 1, 1, 2);
    editsLayout->addWidget(attr2, 1, 0);
    editsLayout->addWidget(line2, 1, 1, 1, 2);
    editsLayout->addWidget(attr3, 2, 0);
    editsLayout->addWidget(line3, 2, 1, 1, 2);
    editsLayout->addWidget(attr4, 3, 0);
    editsLayout->addWidget(line4, 3, 1, 1, 2);
    editsLayout->addWidget(attr5, 4, 0);
    editsLayout->addWidget(line5, 4, 1, 1, 2);

    adminFunctionsLayout->addLayout(editsLayout);

    QGridLayout* buttonsLayout = new QGridLayout();
    
    QPushButton* addButton = new QPushButton("Add");
    QPushButton* deleteButton = new QPushButton("Delete");
    QPushButton* updateButton = new QPushButton("Update");
    filter = new QPushButton("Filter");

    buttonsLayout->addWidget(addButton, 0, 0);
    buttonsLayout->addWidget(deleteButton, 0, 1);
    buttonsLayout->addWidget(updateButton, 0, 2);
    buttonsLayout->addWidget(filter, 0, 3);

    adminFunctionsLayout->addLayout(buttonsLayout);

    QFormLayout* filterBoxLayout = new QFormLayout();
    QLabel* filterBoxLabel = new QLabel("Objects filtered: ");
    filterBox = new QLineEdit();
    filteredObjects = new QListWidget();
    filterBoxLabel->setBuddy(filteredObjects);
    QLabel* editLabel = new QLabel("Input filter: ");
    editLabel->setBuddy(filterBox);

    filterBoxLayout->addRow(editLabel);
    filterBoxLayout->addRow(filterBox);
    filterBoxLayout->addRow(filterBoxLabel);
    filterBoxLayout->addRow(filteredObjects);

    adminLayout->addLayout(adminFunctionsLayout);
    adminLayout->addLayout(filterBoxLayout);

    adminLayout->addLayout(adminFunctionsLayout);

    QWidget* user = new QWidget();

    tabs->addTab(admin, "Admin");
    tabs->addTab(user, "User");

    mainLayout->addWidget(tabs);
}

void gui::populateGui()
{
    filteredObjects->clear();
    repo.readFromFile("data.in");
    for (auto c : this->repo.getAllCoats())
    {
        QString itemInRepo = QString::fromStdString(c.coat_to_string());
        QListWidgetItem* item = new QListWidgetItem{ itemInRepo };
        objectsList->addItem(item);
    }
}

void gui::connectRelation()
{
    QObject::connect(filter, &QPushButton::clicked, this, &gui::filterFunc);
}

void gui::filterFunc()
{
    filteredObjects->clear();
    QString filterData = filterBox->text();
    std::string filterDataString = filterData.toStdString();
    for (auto c : this->repo.getAllCoats())
    {
        if (c.coat_to_string().find(filterDataString) != -1)
        {
            QString itemInList = QString::fromStdString(c.coat_to_string());
            QListWidgetItem* item = new QListWidgetItem{ itemInList };
            filteredObjects->addItem(item);
        }
    }
}
