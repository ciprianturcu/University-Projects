#include "Gui.h"
#include <QMessageBox>
#include "CVSBasket.h"
#include "HTMLBasket.h"
#include "PictureDelegate.h"
using namespace std;

Gui::Gui(Service& s,QWidget *parent)
    : QWidget(parent), serv{s}
{
    ui.setupUi(this);
    this->undoShortcut = new QShortcut(QKeySequence(Qt::CTRL + Qt::Key_Z), this);
    this->redoShortcut = new QShortcut(QKeySequence(Qt::CTRL + Qt::Key_Y), this);
    ui.tabWidget->setTabEnabled(1, false);
    ui.tabWidget->setTabEnabled(2, false);
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
    QObject::connect(this->ui.undoAdminButton, &QPushButton::clicked, this, &Gui::undoAdmin);
    QObject::connect(this->ui.redoAdminButton, &QPushButton::clicked, this, &Gui::redoAdmin);
    QObject::connect(this->undoShortcut, &QShortcut::activated, this, &Gui::undoAdmin);
    QObject::connect(this->redoShortcut, &QShortcut::activated, this, &Gui::redoAdmin);
    QObject::connect(this->ui.undoUserButton, &QPushButton::clicked, this, &Gui::undoUser);
    QObject::connect(this->ui.redoUserButton, &QPushButton::clicked, this, &Gui::redoUser);
    QObject::connect(this->ui.tableView, &QPushButton::clicked, this, &Gui::createTable);
    QObject::connect(this->ui.coatListWidget, &QListWidget::itemSelectionChanged, [this]()
        {
            int selectedIndex = getSelectedIndex();
            if (selectedIndex < 0)
                return;
            Coat c = serv.getCoats()[selectedIndex];
            ui.sizeLineEdit->setText(QString::fromStdString(c.getSize()));
            ui.colourLineEdit->setText(QString::fromStdString(c.getColour()));
            ui.priceLineEdit->setText(QString::fromStdString(to_string(c.getPrice())));
            ui.quantityLineEdit->setText(QString::fromStdString(to_string(c.getQuantity())));
            ui.linkLineEdit->setText(QString::fromStdString(c.getLink()));
        });
    
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
    this->ui.sizeLineEdit->clear();
    this->ui.colourLineEdit->clear();
    this->ui.priceLineEdit->clear();
    this->ui.quantityLineEdit->clear();
    this->ui.linkLineEdit->clear();
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
        this->ui.sizeLineEdit->clear();
        this->ui.colourLineEdit->clear();
        this->ui.priceLineEdit->clear();
        this->ui.quantityLineEdit->clear();
        this->ui.linkLineEdit->clear();
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
        ui.tabWidget->setCurrentIndex(2);
    }
}

void Gui::goToUser()
{
    ui.tabWidget->setTabEnabled(1, false);
    ui.tabWidget->setTabEnabled(2, true);
    ui.tabWidget->setCurrentIndex(2);
    this->populateUserGui();
    this->populateUserBasket();
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
        this->populateGui();
    }
    else
    {
        QMessageBox::critical(this, "ERROR", "Incorrect password!");
        return;
    }
}

void Gui::undoAdmin()
{
    try
    {
        this->serv.undoServiceAdmin();
        this->populateGui();
    }
    catch (RepoError)
    {
        QMessageBox::critical(this, "Error", "Error at undo!");
        return;
    }
    catch (InexistentCoat)
    {
        QMessageBox::critical(this, "Error", "Error at undo!");
        return;
    }

}

void Gui::undoUser()
{
    try
    {
        this->serv.undoServiceUser();
        this->populateUserGui();
        this->populateUserBasket();
    }
    catch (RepoError)
    {
        QMessageBox::critical(this, "Error", "Error at undo!");
        return;
    }
}

void Gui::redoAdmin()
{
    try
    {
        this->serv.redoServiceAdmin();
        this->populateGui();
    }
    catch (RepoError)
    {
        QMessageBox::critical(this, "Error", "Error at redo!");
        return;
    }
}

void Gui::redoUser()
{
    try
    {
        this->serv.redoServiceUser();
        this->populateUserGui();
        this->populateUserBasket();
    }
    catch (RepoError)
    {
        QMessageBox::critical(this, "Error", "Error at redo!");
        return;
    }
}

void Gui::createTable()
{
    this->basketTableWindow = new QWidget{};
    auto* basketWindowLayout = new QVBoxLayout(this->basketTableWindow);
    this->basketListTable = new QTableView{};

    this->basketListTableModel = new CoatTableModel(this->serv.getCoatsFromBasket());
    this->basketListTable->setModel(this->basketListTableModel);
    this->basketListTable->setItemDelegate(new PictureDelegate{});
    this->basketListTable->resizeColumnsToContents();
    this->basketListTable->resizeRowsToContents();
    basketWindowLayout->addWidget(this->basketListTable);
    this->basketTableWindow->resize(840, 720);
    this->basketTableWindow->show();
}

CoatTableModel::CoatTableModel(std::vector<Coat> arr)
{
    this->basket = arr;

}

int CoatTableModel::rowCount(const QModelIndex& parent) const
{
    return this->basket.size();
}

int CoatTableModel::columnCount(const QModelIndex& parent) const
{
    return 6;
}

QVariant CoatTableModel::data(const QModelIndex& index, int role) const
{
    int row = index.row();
    int column = index.column();
    Coat currentCoat = this->basket[row];
    if (role == Qt::DisplayRole || role == Qt::EditRole)
    {
        switch (column)
        {
        case 0:
            return QString::fromStdString(currentCoat.getSize());
        case 1:
            return QString::fromStdString(currentCoat.getColour());
        case 2:
            return QString::fromStdString(to_string(currentCoat.getPrice()));
        case 3:
            return QString::fromStdString(to_string(currentCoat.getQuantity()));
        case 4:
            return QString::fromStdString(currentCoat.getLink());
        case 5:
            return QString::fromStdString(currentCoat.getColour());
        default:
            break;
        }
    }
    return QVariant();
}

QVariant CoatTableModel::headerData(int section, Qt::Orientation orientation, int role) const
{
    if (role == Qt::DisplayRole)
    {
        if (orientation == Qt::Horizontal)
        {
            switch (section)
            {
            case 0:
                return QString("Size");
            case 1:
                return QString("Colour");
            case 2:
                return QString("Price");
            case 3:
                return QString("Quantity");
            case 4:
                return QString("Photo Link");
            case 5:
                return QString("Photo");
            default:
                break;
            }
        }
    }
    return QVariant();
}

void CoatTableModel::update()
{
    QModelIndex topLeftCorner = this->index(1, 1);
    QModelIndex bottomRightCorner = this->index(this->rowCount(), this->columnCount());
    emit layoutChanged();
    emit dataChanged(topLeftCorner, bottomRightCorner);
}
