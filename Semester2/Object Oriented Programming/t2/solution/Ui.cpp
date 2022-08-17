#include "Ui.h"

Ui::Ui(Device d)
{
	this->dev = d;
}

void print_menu()
{
	cout << "\n0.exit\n1.add\n2.show all\n3.get alerts\n4.save file\n";
}

void Ui::start_ui()
{
	this->dev.addEnt();
	int option;
	while (true)
	{
		
		print_menu();
		cout << "option: ";
		cin >> option;
		switch (option)
		{
		case 0:
			return;
		case 1:
		{
			std::vector<double> rec;
			int n=0,x=0;
			int dim=0, len=0;
			string prod;
			string type;
			cout << "type: ";
			cin >> type;
			cout << "producer: ";
			cin >> prod;
			cout << "nr of recordings: ";
			cin >> n;
			for (int i = 0; i < n; i++)
			{
				cout << "recording: ";
				cin >> x;
				rec.push_back(x);
			}
			if (type == "T")
			{
				cout << "diam: ";
				cin >> dim;
				cout << "len: ";
				cin >> len;
				Tsensor* t = new Tsensor(prod, rec, dim, len);
				this->dev.addSensor(t);
			}
			else if (type == "H")
			{
				Hsensor* s = new Hsensor(prod, rec);
				this->dev.addSensor(s);
			}
			else if (type == "S")
			{
				Ssensor* s = new Ssensor(prod, rec);
				this->dev.addSensor(s);
			}
			break;
		}
		case 2:
		{
			auto list = this->dev.getAll();
			for (auto e : list)
			{
				cout << e->toString();
			}
			break;
		}
		case 3:
		{
			auto list = this->dev.getAlertingSensors();
			for (auto e : list)
			{
				cout << e->toString();
			}
			break;
		}
		case 4:
		{
			cout << "filename: ";
			string filename;
			cin >> filename;
			this->dev.writeTofile(filename);
			break;
		}
		default:
			break;
		}
	}
}
