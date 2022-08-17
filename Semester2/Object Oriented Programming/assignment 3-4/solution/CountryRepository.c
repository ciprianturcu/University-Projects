#pragma once
#include "CountryRepository.h"
#include <stdlib.h>
#include <string.h>
#include "assert.h"

CountryRepo* createRepo()
{
	CountryRepo* v = (CountryRepo*)malloc(sizeof(CountryRepo));
	if (v == NULL)
	{
		return NULL;
	}
	v->countries = createDynamicVector(10, destroyCountry);
	return v;
}

void destroyRepo(CountryRepo* v)
{
	if (v == NULL)
		return;
	destroyDynamicVector(v->countries);
	free(v);
}



int find(CountryRepo* v, char name[])
{
	int size = getLengthVector(v->countries);
	for (int i = 0; i < size; i++)
	{
		Country* c = getCountryFromPosition(v, i);
		if (strcmp(c->name, name) == 0)
			return i;
	}
	return -1;
}

int addCountryRepo(CountryRepo* v, Country* c)
{
	int size = getLengthVector(v->countries);
	if (find(v, c->name) != -1)
	{
		return -1;
	}
	addElement(v->countries, c);
	return 1;
}

int deleteCountryRepo(CountryRepo* v, Country* c)
{
	int size = getLengthVector(v->countries);
	int position = -1;
	if (v == NULL || c == NULL)
		return -1;

	position = find(v, c->name);
	if (position == -1)
	{
		return -1;
	}
	else
	{
		deleteElement(v->countries, position);
	}
	return 1;
}

int updateCountryRepo(CountryRepo* v, Country* c, int population)
{
	if (v == NULL || c == NULL)
		return -1;

	int position_c = -1;
	position_c = find(v, c->name);
	if (position_c == -1)
		return -1;
	else
	{
		getCountryFromPosition(v, position_c)->population = population;
	}
	return 1;
}

int getLength(CountryRepo* v)
{
	return getLengthVector(v->countries);
}

Country* getCountryFromPosition(CountryRepo* v, int position)
{
	int size = getLength(v);
	if (v == NULL || position < 0 || position >= size)
	{
		return NULL;
	}
	return  get(v->countries, position);
}

void setCountryToPosition(CountryRepo* v, int position, Country* c)
{
	int size = getLength(v);
	if (v == NULL || position < 0 || position >= size || c ==NULL)
	{
		return NULL;
	}
	set(v->countries, position, c);
}

void testRepo()
{
	CountryRepo* repo = createRepo();

	Country* a = createCountry("a", "b", 100);
	Country* b = createCountry("c", "d", 1000);

	

	addCountryRepo(repo, a);
	assert(getLength(repo) == 1);
	addCountryRepo(repo, b);
	assert(getLength(repo) == 2);
	assert(find(repo, "a") == 0);
	deleteCountryRepo(repo, a);
	assert(getLength(repo) == 1);

	Country* c = getCountryFromPosition(repo, 0);

	assert(strcmp(c->name, b->name) == 0);
	assert(strcmp(c->continent, b->continent) == 0);
	assert(c->population == b->population);

	updateCountryRepo(repo, b, 100000);
	assert(getCountryFromPosition(repo, 0)->population == 100000);

	destroyRepo(repo);

}