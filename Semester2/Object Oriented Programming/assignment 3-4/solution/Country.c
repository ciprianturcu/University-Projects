#include "Country.h"
#include <stdio.h>
#include <string.h>
#include <stdlib.h>


Country* createCountry(char* name, char* continent, int population)
{
	Country* c = malloc(sizeof(Country));
	if (c == NULL)
	{
		return NULL;
	}

	c->name = malloc(sizeof(char) * strlen(name) + 1);
	if (c->name != NULL)
		strcpy(c->name, name);
	c->continent = malloc(sizeof(char) * strlen(continent) + 1);

	if (c->continent != NULL)
		strcpy(c->continent, continent);
	c->population = population;
	return c;
}

void destroyCountry(Country* c)
{
	if (c == NULL)
		return;
	free(c->name);
	free(c->continent);
	free(c);
}

int isSubStringInName(Country* c, char string[])
{

	if (strstr(c->name, string))
	{
		return 1;
	}
	else {
		return 0;
	}
}

int isOnContinent(Country* c, char continent[])
{
	if (strcmp(continent, c->continent) == 0)
		return 1;
	return 0;
}

char* getName(Country* c)
{
	if (c == NULL)
		return NULL;
	return c->name;
}

char* getContinent(Country* c)
{
	if (c == NULL)
		return NULL;
	return c->continent;
}

int getPopulation(Country* c)
{
	if (c == NULL)
		return -1;
	return c->population;

}

int setPopulation(Country* c, int newPopulation)
{
	if (c == NULL)
		return -1;
	c->population = newPopulation;
	return 1;
}

void toString(Country* c, char str[])
{
	if (c == NULL)
		return;
	sprintf(str, "Country %s is on the continent of %s and has a population of %d million.", c->name, c->continent, c->population);
}

Country* copyCountry(Country* c)
{
	if (c == NULL)
		return NULL;
	return createCountry(c->name, c->continent, c->population);
}