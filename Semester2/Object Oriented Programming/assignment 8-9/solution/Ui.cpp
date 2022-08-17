#include "Ui.h"
#include "CVSBasket.h"
#include "HTMLBasket.h"
#include <iostream>
#include <string>
using namespace std;

Ui::Ui()
{
}

void Ui::choose_file()
{
    std::cout << "Please choose file to store shopping basket(csv/html): ";
    std::string mode;
    std::cin >> mode;
    if (mode == "csv")
    {
        CVSBasket* cvsb = new CVSBasket("C:/Users/cipri/source/repos/a8-9-917-Turcu-Ciprian/basket.csv");
        const Repository& r = Repository();
        this->service = Service(r, cvsb, "data.txt");
        return;
    }
    else if (mode == "html")
    {
        HTMLBasket* cvsb = new HTMLBasket("C:/Users/cipri/source/repos/a8-9-917-Turcu-Ciprian/basket.html");
        const Repository& r = Repository();
        this->service = Service(r, cvsb, "data.txt");
        return;
    }
    else
        cout << "Wrong Mode.\n";
    choose_file();
}



void Ui::choose_mode()
{
    std::cout << "Please choose operating mode (admin/user/exit) : ";
    std::string mode;
    std::cin >> mode;
    if (mode == "admin")
    {
        this->mode = 0;
        start_admin();

    }
    else if (mode == "user")
    {
        this->mode = 1;
        start_user();
    }
    else if (mode == "exit")
    {
        cout << "Program exited.\n";
        return;
    }
    else
        cout << "Wrong mode.\n";
    choose_mode();

}

void Ui::start_app()
{
    choose_file();
    service.initializeRepo();
    choose_mode();
    
}

void Ui::readCoatContents(std::string *size, std::string *colour, std::string *price, std::string *quantity, std::string *link)
{
    cout << "Size: ";
    cin >> *size;
    cout << "Colour: ";
    cin >> *colour;
    cout << "Price: ";
    cin >> *price;
    cout << "Quantity: ";
    cin >> *quantity;
    cout << "Photo Link: ";
    cin >> *link;
}

void Ui::start_admin()
{
    while (true)
    {
        admin_menu();
        cout << "Choose an option: \n";
        string choice;
        cin >> choice;
        if (choice.find_first_not_of("0123456789") == std::string::npos)
        {
            int choice_int = stoi(choice);
            switch (choice_int)
            {
            case 1:
            {
                add_coat_admin();
                break;
            }
            case 2:
            {
                remove_coat_admin();
                break;
            }
            case 3:
            {
                update_coat_admin();
                break;
            }
            case 4:
            {
                show_all();
                break;
            }
            case 5:
            {
                return;
                break;
            }
            }

        }
    }
}

void Ui::admin_menu()
{
    cout << "Admin menu:\n";
    cout << "1.Add coat.\n";
    cout << "2.Delete coat.\n";
    cout << "3.Update coat.\n";
    cout << "4.Show all.\n";
    cout << "5.Exit.\n";
}



void Ui::add_coat_admin()
{
    try
    {
        string size, colour, photo_link, price, quantity;
        readCoatContents(&size, &colour, &price, &quantity, &photo_link);
        
        int rez = service.addToRepo(size, colour, price, quantity, photo_link);
        if (rez == true)
        {
            cout << "Coat added.\n";
        }
        else
        {
            cout << "Add failed.\n";
        }
    }
    catch (InputException& ie)
    {
        for (auto e : ie.getErrors())
            cout << e;
    }
    catch (CoatException& ce)
    {
        for (auto e : ce.getErrors())
            cout << e;
    }
}

void Ui::remove_coat_admin()
{
    show_all();
    int option;
    cout << "Option: \n";
    cin >> option;
    std::vector<Coat> list = service.getCoats();
    int size = service.getSize();
    int rez;
    if (option <= size - 1)
    {
        rez = this->service.deleteCoat(list[option]);
    }
    else
    {
        rez = false;
    }
    if (rez == true)
    {
        cout << "Coat removed.\n";
    }
    else
    {
        cout << "Removed failed.\n";
    }
}

void Ui::update_coat_admin()
{
    show_all();
    int option;
    cout << "Option: ";
    cin >> option;
    if (option > service.getSize() - 1)
    {
        cout << "No coat on that position.\n";
        return;
    }
    try
    {
        string size, colour, photo_link, price, quantity;
        readCoatContents(&size, &colour, &price, &quantity, &photo_link);
        int rez = service.updateCoat(size, colour, price, quantity, photo_link, option);
        if (rez == true)
        {
            cout << "Update successful.\n";

        }
        else
        {
            cout << "Update failed.\n";
        }
    }
    catch (InputException& ie)
    {
        for (auto e : ie.getErrors())
            cout << e;
    }
    catch (CoatException& ce)
    {
        for (auto e : ce.getErrors())
            cout << e;
    }

}

void Ui::show_all()
{
    std::vector<Coat> list = service.getCoats();
    int cnt = 0;
    for (auto i : list)
    {
        cout << to_string(cnt) + ". " + i.coat_to_string() + "\n";
        cnt++;
    }
    cout << endl;
}

void Ui::start_user()
{
    string choice;
    string fileoption;
    while (true)
    {
        user_menu();
        cout << "Choose option: \n";
        cin >> choice;
        if (choice.find_first_not_of("0123456789") == string::npos)
        {
            int choice_int = stoi(choice);
            switch (choice_int)
            {
            case 1:
            {
                parse_coats();
                break;
            }
            case 2:
            {
                see_basket();
                break;
            }
            case 3:
            {
                return;
                break;
            }
            }
        }
    }
}

void Ui::user_menu()
{
    cout << "\n1.Browse coats. \n";
    cout << "2.See basket. \n";
    cout << "3.Exit. \n";
}

void Ui::parse_coats()
{
    string size;
    string a;
    int list_size;
    std::vector<Coat> list;
    bool run = true;
    int choice;
    int i = 0;
    getline(cin, a);
    while(run)
    {
        try
        {
            cout << "Please enter your size: ";
            getline(cin, size);
            list = this->service.filterCoatsBySize(size);
            break;
        }
        catch (CoatException& ce)
        {
            for (auto e : ce.getErrors())
                cout << e;
        }
    }
    while (run)
    {
        if (i == list.size())
        {
            i = 0;
        }
        if (list[i].getQuantity() != 0)
        {
            cout << list[i].coat_to_string();
            //list.getCoatFromPos(i).display_link();
            cout << "\nWould you like to add the coat to your basket ? (y/n/e): \n";
            string y_n_e;
            cin >> y_n_e;
            if (y_n_e.compare("y") == 0)
            {
                this->service.addToBasket(list[i]);
            }
            else if (y_n_e.compare("e") == 0)
            {
                cout << "\nExiting browsing.\n";
                run = false;
            }
            else if (y_n_e.compare("n") != 0)
            {
                i--;
            }
        }
        i++;
    }
}

void Ui::see_basket()
{
    /*auto list = this->service.getCoatsFromBasket();
    int cnt = 0;
    for (auto i : list)
    {
        cout << to_string(cnt) + ". " + i.coat_to_string() + "\n";

    }
    cout << "Total basket sum is: " + to_string(this->service.getBasketSum());*/
    this->service.showBasket();
}

