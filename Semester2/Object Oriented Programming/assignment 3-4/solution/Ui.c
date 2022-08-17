#include "Ui.h"
#include "stdlib.h"
#include "stdio.h"


Ui* createUI(Service* s)
{
	Ui* ui = malloc(sizeof(Ui));
	if (ui == NULL)
		return NULL;
	ui->service = s;

}

void destroyUi(Ui* ui)
{
	if (ui == NULL)
		return;
	destroyService(ui->service);
	free(ui);
}


/*
	Prints the available menu for the problem
	Input: -
	Output: the menu is printed at the console
*/
void printMenu()
{
	printf("\n**********************************************************\n");
	printf("1 - register a country.\n");
	printf("2 - delete a country.\n");
	printf("3 - update a country.\n");
	printf("4 - list all countries.\n");
	printf("5 - list countries that contain a given string in the name.\n");
	printf("6 - list countries above a given population.\n");
	printf("7 - list countries that have a population over a given threshold and are on a specific continent.\n");
	printf("8 - undo.\n");
	printf("9 - redo.\n");
	printf("0 - to exit.\n");
	printf("**********************************************************\n");
}

/*
	Verifies if the commmand is whitin bounds
	Input: - command number
	Output: 1 true, 0 false
*/
int validCommand(int command)
{
	if (command >= 0 && command <= 9)
		return 1;
	return 0;
}

/*
	Function to read an integer number as a command
	input - output message for input
	output - integer number representing the command
*/

int readIntegerCommand(const char* message)
{
	char s[16] = { 0 };
	int res = 0;
	int flag = 0;
	int r = 0;

	while (flag == 0)
	{
		printf(message);
		int scanf_result = scanf("%15s", s);

		r = sscanf(s, "%d", &res);	// reads data from s and stores them as integer, if possible; returns 1 if successful
		flag = (r == 1);
		if (flag == 0)
			printf("Error reading number!\n");
	}
	return res;
}

int addCountryUi(Ui* ui)
{
	char name[60], continent[20];
	int population = 0;
	printf("Please enter the country name: ");
	int scanf_result = scanf("%49s", &name);
	printf("Please enter the continent: ");
	scanf_result = scanf("%20s", &continent);
	printf("Please input the population of the country(in millions): ");
	scanf_result = scanf("%d", &population);

	return addCountry(ui->service, name, continent, population);
}

int listCountries(CountryRepo* repo)
{
	int size = getLength(repo);
	if (size == 0)
	{
		return 0;
	}
	for (int i = 0; i < size; i++)
	{
		Country* country = getCountryFromPosition(repo, i);
		char result_string[100];
		toString(country, result_string);
		printf("%s\n", result_string);
	}
	return 1;
}

void listAllCountries(Ui* ui)
{
	if (ui == NULL)
	{
		return;
	}
	CountryRepo* repo = getRepo(ui->service);
	if (repo == NULL)
	{
		return;
	}
	listCountries(repo);
}

int FilterCountriesByPopulation(Ui* ui)
{
	int population_goal = 0;
	printf("Show countries with an above population of(in millions): ");

	int scanf_result = scanf("%d", &population_goal);

	CountryRepo* repo = malloc(sizeof(CountryRepo));
	CountryRepo* resultrepo = malloc(sizeof(CountryRepo));

	repo = getRepo(ui->service);
	if (repo == NULL)
	{
		return -1;
	}
	resultrepo = filterByPopulation(ui->service->repo, population_goal);
	if (resultrepo == NULL)
	{
		return 0;
	}
	listCountries(resultrepo);
	free(repo);
	free(resultrepo);
	return 1;
}

int undoUi(Ui* ui)
{
	return undo(ui->service);
}

int redoUi(Ui* ui)
{
	return redo(ui->service);
}


int FilterCountriesByStringUi(Ui* ui, typeFct Function, int read_population)
{
	int population = 0;
	char string[60];
	char smth[1];
	gets(smth); // ask why is this needed
	printf("Enter the string you want to search in the countries: ");
	gets(string);

	if (read_population == 1)
	{
		printf("Please enter the threshold population: ");
		int scanf_result = scanf("%d", &population);
	}

	CountryRepo* repo = getRepo(ui->service);
	if (repo == NULL)
	{
		return -1;
	}
	CountryRepo* resultrepo = filterByStringFunction(ui->service, string, Function);
	if (resultrepo == NULL)
	{
		return 0;
	}
	CountryRepo* resultrepo2 = filterByPopulation(resultrepo, population);
	sortAscendingByPopulation(resultrepo2);
	int res = listCountries(resultrepo2);
	destroyRepo(resultrepo);
	destroyRepo(resultrepo2);
	return res;
}

int deleteCountryUi(Ui* ui)
{
	char country_name[60];
	printf("Enter the name of the country you want to delete: ");
	int scanf_result = scanf("%60s", &country_name);
	CountryRepo* repo = getRepo(ui->service);
	if (repo == NULL)
	{
		return -1;
	}
	int position = find(repo, country_name);
	if (position == -1)
	{
		return -1;
	}
	Country* c = getCountryFromPosition(repo, position);
	return deleteCountry(ui->service, c);
}

int updateCountryUi(Ui* ui)
{
	char country_name1[60];
	char country_name2[60];
	int population = -1;

	printf("Enter the destination country: ");
	int scanf_result = scanf("%60s", &country_name1);
	printf("\nEnter the source country: ");
	scanf_result = scanf("%60s", &country_name2);
	printf("\nEnter the migrating population(in millions): ");
	scanf_result = scanf("%d", &population);

	CountryRepo* repo = getRepo(ui->service);
	if (repo == NULL)
	{
		return -1;
	}
	int positionc1 = find(repo, country_name1);
	int positionc2 = find(repo, country_name2);
	if (positionc1 == -1 || positionc2 == -1 || population == -1)
	{
		return -1;
	}


	Country* c1 = getCountryFromPosition(repo, positionc1);
	Country* c2 = getCountryFromPosition(repo, positionc2);
	updateCountry(ui->service, c1, c1->population + population);
	updateCountry(ui->service, c2, c2->population - population);

}

void startUi(Ui* ui)
{
	while (1)
	{
		printMenu();
		int command = readIntegerCommand("Input command: ");
		while (validCommand(command) == 0)
		{
			printf("Please input a valid command!\n");
			command = readIntegerCommand("Input command: ");
		}
		if (command == 0)
			return 0;
		switch (command)
		{
		case 1:
		{
			int result = addCountryUi(ui);
			if (result == 1)
				printf("Country successfully added. \n");
			else
				printf("Error! Country could not be added, as there is another Country with the same name!\n");
			break;
		}
		case 2:
		{
			deleteCountryUi(ui);
			break;
		}
		case 3:
		{
			updateCountryUi(ui);
			break;
		}
		case 4:
		{
			listAllCountries(ui);
			break;
		}
		case 5:
		{
			int result = FilterCountriesByStringUi(ui, &isSubStringInName, 0);
			if (result == 0)
				printf("No countries name that match the given string.\n");
			else
			{
				if (result == -1)
					printf("Error! No countries in list, cannot filter.");
			}
			break;
		}
		case 6:
		{
			int result = FilterCountriesByPopulation(ui);
			if (result == 0)
			{
				printf("No countries meet the requirement");
			}
			else if (result == -1)
			{
				printf("Error! No countries in list, cannot filter");
			}
			break;
		}
		case 7:
		{
			int result = FilterCountriesByStringUi(ui, &isOnContinent, 1);
			if (result == 0)
			{
				printf("No countries name that match the given population threshold.\n");
			}
			else
			{
				if (result == -1)
				{
					printf("Error! No countries in list, cannot filter.");
				}
			}
			break;
		}
		case 8:
		{
			int res = undoUi(ui);
			if (res == 0)
				printf("Undo successful");
			break;
		}
		case 9:
		{
			int res = redoUi(ui);
			if (res == 0)
				printf("Redo successful");

			break;
		}
		default:
			break;
		}

	}
}