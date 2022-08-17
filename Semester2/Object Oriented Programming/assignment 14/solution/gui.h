#pragma once

#include <QtWidgets/QWidget>
#include "ui_gui.h"
#include "Service.h"
#include <qshortcut.h>
#include <QTableView>

class CoatTableModel : public QAbstractTableModel
{
private:
    std::vector<Coat> basket;
public:
    CoatTableModel(std::vector<Coat> arr);
    int rowCount(const QModelIndex& parent = QModelIndex()) const override;
    int columnCount(const QModelIndex& parent = QModelIndex()) const override;
    QVariant data(const QModelIndex& index, int role = Qt::DisplayRole) const override;
    QVariant headerData(int section, Qt::Orientation orientation, int role = Qt::DisplayRole) const override;

    void update();

};

class Gui : public QWidget
{
    Q_OBJECT

public:
    Gui(Service& s,QWidget *parent = Q_NULLPTR);

private:
    Ui::GuiClass ui;
    Service& serv;
    QWidget* basketTableWindow;
    QTableView* basketListTable;
    CoatTableModel* basketListTableModel;
    QShortcut* undoShortcut;
    QShortcut* redoShortcut;

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
    void undoAdmin();
    void undoUser();
    void redoAdmin();
    void redoUser();
    void createTable();


};
