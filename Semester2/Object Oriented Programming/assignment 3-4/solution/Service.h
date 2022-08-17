#pragma once
#include "CountryRepository.h"

typedef int (*typeFct)(Country*, char*);

typedef struct
{
	CountryRepo* repo;
	DynamicVector* undoStack;
	DynamicVector* redoStack;
}Service;

/// <summary>
/// Function to create and allocate the service.
/// </summary>
/// <param name="r">Repository</param>
/// <returns>Service object of possible, null otherwise</returns>
Service* createService(CountryRepo* r);

/// <summary>
/// Function to destroy and free the memory of a service
/// </summary>
/// <param name="s">the service we want to destroy</param>
void destroyService(Service* s);

/// <summary>
/// Function that adds a country
/// </summary>
/// <param name="s">the service</param>
/// <param name="name">name of the country to be created</param>
/// <param name="continent"></param>
/// <param name="population"></param>
/// <returns>1 for a succesfull add, -1 otherwise</returns>
int addCountry(Service* s, char* name, char* continent, int population);

/// <summary>
/// Function to delete a country
/// </summary>
/// <param name="s">the service</param>
/// <param name="country">Country object to be deleted</param>
/// <returns>1 for succesful delete, -1 otherwise</returns>
int deleteCountry(Service* s, Country* country);

/// <summary>
/// Function to update a country population
/// </summary>
/// <param name="s">Service</param>
/// <param name="c">Country to be updated</param>
/// <param name="population">new population</param>
/// <returns>1 for succesfull update, -1 otherwise</returns>
int updateCountry(Service* s, Country* c, int population);

/// <summary>
/// Function to get the repository from the service
/// </summary>
/// <param name="s">Service</param>
/// <returns>CountryRepo object representing the repository.</returns>
CountryRepo* getRepo(Service* s);

/// <summary>
/// Function to filter countries by a string and a function that works whit our string
/// </summary>
/// <param name="s">service</param>
/// <param name="string">querry string</param>
/// <param name="typeFunction">comparison or verification function</param>
/// <returns>CountryRepo, containing a list of the good countries</returns>
CountryRepo* filterByStringFunction(Service* s, char string[], typeFct typeFunction);

/// <summary>
/// Function to filter countries by population
/// </summary>
/// <param name="repo">CountryRepo</param>
/// <param name="population">population threshold</param>
/// <returns>CountryRepo, containing a list of the good countries</returns>
CountryRepo* filterByPopulation(CountryRepo* repo, int population);

/// <summary>
/// Function to undo the LPO's
/// </summary>
/// <param name="s">Service</param>
/// <returns>1 for succesfull undo </returns>
int undo(Service* s);

int redo(Service* s);

/// <summary>
///  Function to sort a repository ascending by the popualtion
/// </summary>
/// <param name="repo">repo to be sorted</param>
void sortAscendingByPopulation(CountryRepo* repo);

void initializeData(Service* s);

void testService();

