#include "Ui.h"

using namespace std;


Ui::Ui(Repository repo)
{
	service = Service(repo);
	mode = -1;
}

void Ui::initialize_app()
{
	service.initializeRepo();
	choose_mode();
}

void Ui::choose_mode()
{
	std::cout << "Please choose operating mode (admin/user) : ";
	std::string mode;
	std::cin >> mode;
	if (mode == "admin")
	{
		this->mode = 0;
		start_admin();

	}
	else if (mode == "user") { return; }
	else if (mode == "exit") { return; }
	else
		cout << "Wrong mode.";
	choose_mode();
	
}

void Ui::start_app()
{
	service.initializeRepo();
	choose_mode();
}

void Ui::start_admin()
{
	while (true)
	{
		admin_menu();
		cout << "Choose an option: ";
		string choice;
		cin >> choice;
		if (choice.find_first_not_of("0123456789") == std::string::npos)
		{
			int choice_int = stoi(choice);
			switch (choice_int)
			{
			case 1:
				add_coat_admin();
				break;

			case 4:
				show_all();
				break;
			case 5:
				return;
				break;
			}
			
		}
	}
}

void Ui::admin_menu()
{
	cout << "Admin menu:\n";
	cout << "1.Add coat.\n";
	cout << "2.Delte coat.\n";
	cout << "3.Update coat.\n";
	cout << "4.Show all.\n";
	cout << "5.Exit.\n";
}



void Ui::add_coat_admin()
{
	string size, colour, photo_link;
	double price, quantity;
	cout << "Size: ";
	cin >> size;
	cout << "Colour: ";
	cin >> colour;
	cout << "Price: ";
	cin >> price;
	cout << "Quantity: ";
	cin >> quantity;
	cout << "Photo Link: ";
	cin >> photo_link;
	int rez = service.addToRepo(size, colour, price, quantity, photo_link);
	if (rez == true)
	{
		cout << "added";
	}
	else
	{
		cout << "failed.";
	}
	

}

void Ui::remove_coat_admin()
{

}

void Ui::show_all()
{
	Coat* list = service.getCoats();
	for (int i = 0; i < service.getSize(); i++)
	{
		cout << endl;
		cout << list[i].coat_to_string()+"\n";
		cout << endl;
	}

}
