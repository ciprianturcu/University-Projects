#include "Gui.h"
#include <QMessageBox>
#include "CVSBasket.h"
#include "HTMLBasket.h"

using namespace std;

Gui::Gui(Service& s,QWidget *parent)
    : QWidget(parent), serv{s}
{
    ui.setupUi(this);
    ui.tabWidget->setTabEnabled(1, false);
    ui.tabWidget->setTabEnabled(2, false);
    ui.tabWidget->setTabEnabled(3, false);
    this->connectSignalsAndSlots();
}

void Gui::connectSignalsAndSlots()
{
    QObject::connect(this->ui.deleteButton, &QPushButton::clicked, this, &Gui::deleteCoat);
    QObject::connect(this->ui.addButton, &QPushButton::clicked, this, &Gui::addCoat);
    QObject::connect(this->ui.updateButton, &QPushButton::clicked, this, &Gui::updateCoat);
    QObject::connect(this->ui.mainAdminButton, &QPushButton::clicked, this, &Gui::adminModeSelector);
    QObject::connect(this->ui.mainUserButton, &QPushButton::clicked, this, &Gui::userModeSelector);
    QObject::connect(this->ui.filterButton, &QPushButton::clicked, this, &Gui::filterCoat);
    QObject::connect(this->ui.transferButton, &QPushButton::clicked, this, &Gui::transferController);
    QObject::connect(this->ui.transferButton, &QPushButton::clicked, this, &Gui::populateUserBasket);
    QObject::connect(this->ui.showBasketButton, &QPushButton::clicked, this, &Gui::showBasket);
    QObject::connect(this->ui.toUser, &QPushButton::clicked, this, &Gui::goToUser);
    QObject::connect(this->ui.goToAdmin, &QPushButton::clicked, this, &Gui::goToAdmin);
    QObject::connect(this->ui.pushButton, &QPushButton::clicked, this, &Gui::displayChart);
}

int Gui::getSelectedIndex()
{
    QModelIndexList selectedIndexes = this->ui.coatListWidget->selectionModel()->selectedIndexes();
    if (selectedIndexes.size() == 0)
    {
        this->ui.sizeLineEdit->clear();
        this->ui.colourLineEdit->clear();
        this->ui.priceLineEdit->clear();
        this->ui.quantityLineEdit->clear();
        this->ui.linkLineEdit->clear();
        return -1;
    }
    int selectedIndex = selectedIndexes.at(0).row();
    return selectedIndex;
}

void Gui::populateGui()
{
    this->ui.coatListWidget->clear();
    for (auto c : this->serv.getCoats())
    {
        QString itemInRepo = QString::fromStdString(c.coat_to_string());
        QListWidgetItem* item = new QListWidgetItem{ itemInRepo };
        this->ui.coatListWidget->addItem(item);
    }
}

void Gui::deleteCoat()
{
    int selectedIndex = this->getSelectedIndex();
    if (selectedIndex < 0)
    {
        QMessageBox::critical(this, "ERROR", "No coat was selected!");
        return;
    }
    Coat c = this->serv.getCoats()[selectedIndex];
    try
    {
        int rez = this->serv.deleteCoat(c);
        if (rez == false)
        {
            QMessageBox::critical(this, "ERROR", "There are still some coats, quantity needs to be 0!");
            return;
        }
    }
    catch (InexistentCoat& ie)
    {
        QMessageBox::critical(this, "ERROR", "Inexistent Coat");
        return;
    }
    this->populateGui();
}

void Gui::addCoat()
{
    string size = this->ui.sizeLineEdit->text().toStdString();
    string colour = this->ui.colourLineEdit->text().toStdString();
    string price = this->ui.priceLineEdit->text().toStdString();
    string quantity = this->ui.quantityLineEdit->text().toStdString();
    string link = this->ui.linkLineEdit->text().toStdString();
    try
    {
        this->serv.addToRepo(size, colour, price, quantity, link);
        this->populateGui();

        int lastElement = this->serv.getCoats().size() - 1;
        this->ui.coatListWidget->setCurrentRow(lastElement);
    }
    catch (InputException& ie)
    {
        QMessageBox::critical(this, "ERROR", "Wrong type of data!");
        return;
    }
    catch (CoatException& ce)
    {
        QMessageBox::critical(this, "ERROR", "Coat data not correct!");
        return;
    }
    catch (DuplicateCoat& dc)
    {
        QMessageBox::critical(this, "ERROR", "Duplicate coat!");
        return;
    }
    
}

void Gui::updateCoat()
{
    int selectedIndex = this->getSelectedIndex();
    if (selectedIndex < 0)
    {
        QMessageBox::critical(this, "ERROR", "No coat was selected!");
        return;
    }
    string size = this->ui.sizeLineEdit->text().toStdString();
    string colour = this->ui.colourLineEdit->text().toStdString();
    string price = this->ui.priceLineEdit->text().toStdString();
    string quantity = this->ui.quantityLineEdit->text().toStdString();
    string link = this->ui.linkLineEdit->text().toStdString();
    try
    {
        this->serv.updateCoat(size, colour, price, quantity, link, selectedIndex);
        this->populateGui();
        this->ui.coatListWidget->setCurrentRow(selectedIndex);
    }
    catch (InputException& ie)
    {
        QMessageBox::critical(this, "ERROR", "Wrong type of data!");
        return;
    }
    catch (CoatException& ce)
    {
        QMessageBox::critical(this, "ERROR", "Coat data not correct!");
        return;
    }
    catch (InexistentCoat& ic)
    {
        QMessageBox::critical(this, "ERROR", "Inexistent coat!");
        return;
    }
}

bool Gui::fileSelector()
{
    if (this->ui.csvButton->isChecked())
    {
        CVSBasket* cvsb = new CVSBasket("C:/Users/cipri/source/repos/a11-12-917-Turcu-Ciprian/basket.csv");
        const Repository& r = Repository();
        this->serv = Service(r, cvsb, "data.txt");
        this->serv.initializeRepo();
        this->populateGui();
        this->populateUserGui();
        return true;
    }
    else if (this->ui.htmlButton->isChecked())
    {
        HTMLBasket* cvsb = new HTMLBasket("C:/Users/cipri/source/repos/a11-12-917-Turcu-Ciprian/basket.html");
        const Repository& r = Repository();
        this->serv= Service(r, cvsb, "data.txt");
        this->serv.initializeRepo();
        this->populateGui();
        this->populateUserGui();
        return true;
    }
    else
    {
        QMessageBox::critical(this, "ERROR", "No file was selected!");
        return false;
    }
    
    
}

void Gui::filterCoat()
{
    this->ui.coatFilteredListWidget->clear();
    QString filterData = this->ui.filterCriteriaLineEdit->text();
    std::string filterDataString = filterData.toStdString();
    for (auto c : this->serv.getCoats())
    {
        if (c.coat_to_string().find(filterDataString) != -1)
        {
            QString itemInList = QString::fromStdString(c.coat_to_string());
            QListWidgetItem* item = new QListWidgetItem{ itemInList };
            this->ui.coatFilteredListWidget->addItem(item);
        }
    }
}

void Gui::populateUserGui()
{   
    this->ui.userCoatsListWidget->clear();
    for (auto c : this->serv.getCoats())
    {
        QString itemInRepo = QString::fromStdString(c.user_coat_to_string());
        QListWidgetItem* item = new QListWidgetItem{ itemInRepo };
        this->ui.userCoatsListWidget->addItem(item);
    }
}

void Gui::transferController()
{
    int selectedIndex = this->userGetSelectedIndex();
    if (selectedIndex < 0)
    {
        QMessageBox::critical(this, "ERROR", "No coat was selected!");
        return;
    }
    Coat c = this->serv.getCoats()[selectedIndex];
    if (c.getQuantity() > 0)
    {
        this->serv.addToBasket(c);
        this->serv.lowerQuantity(selectedIndex);
        this->populateUserGui();
        this->ui.userCoatsListWidget->setCurrentRow(selectedIndex);
    }
    else
    {
        QMessageBox::critical(this, "ERROR", "No coats left!");
        return;
    }
}

void Gui::populateUserBasket()
{
    int sum = this->serv.getBasketSum();
    this->ui.basketListWidget->clear();
    for (auto c : this->serv.getCoatsFromBasket())
    {
        QString itemInRepo = QString::fromStdString(c.user_coat_to_string());
        QListWidgetItem* item = new QListWidgetItem{ itemInRepo };
        this->ui.basketListWidget->addItem(item);
    }
    QString Sum = QString::fromStdString(to_string(sum));
    this->ui.sumTextBrowser->setText(Sum);
}

int Gui::userGetSelectedIndex()
{
    QModelIndexList selectedIndexes = this->ui.userCoatsListWidget->selectionModel()->selectedIndexes();
    int selectedIndex = selectedIndexes.at(0).row();
    return selectedIndex;
}

void Gui::showBasket()
{
    this->serv.showBasket();
}



void Gui::adminModeSelector()
{
    int rez = this->fileSelector();
    if (rez)
    {
        ui.tabWidget->setTabEnabled(0, false);
        ui.tabWidget->setTabEnabled(1, true);
        ui.tabWidget->setTabEnabled(2, false);
        ui.tabWidget->setTabEnabled(3, true);
        ui.tabWidget->setCurrentIndex(1);
    }
}

void Gui::userModeSelector()
{
    int rez = this->fileSelector();
    if (rez)
    {
        ui.tabWidget->setTabEnabled(0, false);
        ui.tabWidget->setTabEnabled(1, false);
        ui.tabWidget->setTabEnabled(2, true);
        ui.tabWidget->setTabEnabled(3, true);
        ui.tabWidget->setCurrentIndex(2);
    }
}

void Gui::goToUser()
{
    ui.tabWidget->setTabEnabled(1, false);
    ui.tabWidget->setTabEnabled(2, true);
    ui.tabWidget->setCurrentIndex(2);
}

void Gui::goToAdmin()
{
    std::string passwd = this->ui.goToAdminLineEdit->text().toStdString();
    if (passwd == "1337")
    {
        this->ui.goToAdminLineEdit->clear();
        ui.tabWidget->setTabEnabled(1, true);
        ui.tabWidget->setTabEnabled(2, false);
        ui.tabWidget->setCurrentIndex(1);
    }
    else
    {
        QMessageBox::critical(this, "ERROR", "Incorrect password!");
        return;
    }
}

void Gui::displayChart()
{
    std::vector<std::string> colours = this->serv.getColours();
    QChart* chart = new QChart();
    QPieSeries* pieSeries = new QPieSeries();
    for (auto colour : colours)
    {
        int nr = this->serv.getNROfCoatsWithColor(colour);
        pieSeries->append(QString::fromStdString(colour + ": " + std::to_string(nr)), nr);
        pieSeries->setLabelsVisible(true);
    }
    chart->addSeries(pieSeries);
    chart->legend()->setAlignment(Qt::AlignBottom);
    auto* chartView = new QChartView{};
    chartView->setChart(chart);

    auto* chartLayout = new QHBoxLayout{};
    auto* chartWidget = new QWidget{};
    chartLayout->addWidget(chartView);
    chartWidget->setLayout(chartLayout);

    chartWidget->show();
}
