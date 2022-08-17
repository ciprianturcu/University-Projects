#pragma once
#include "Country.h"
#include "DynamicVector.h"

typedef struct
{
	DynamicVector* countries;

}CountryRepo;
/// <summary>
/// Function to initiate and allocate memory for the CountryRepo structure;
/// </summary>
/// <returns>initialized CountryRepo structure.</returns>
CountryRepo* createRepo();

/// <summary>
/// Function to free the memory used by a CountryRepo structure.
/// </summary>
/// <param name="v">the repository we want to delete</param>
void destroyRepo(CountryRepo* v);

/// <summary>
/// Function to search for a given Country(by name) in the repository.
/// </summary>
/// <param name="v">the repository in which we search</param>
/// <param name="name">the country name to be searched</param>
/// <returns>the position if the element was found, -1 otherwise</returns>
int find(CountryRepo* v, char name[]);

/// <summary>
/// Function to add a country to the repository.
/// </summary>
/// <param name="v">the repository in which we want to add</param>
/// <param name="c">the country we want to add</param>
/// <returns>1 if it was added, -1 if it was already in repository.</returns>
int addCountryRepo(CountryRepo* v, Country* c);

/// <summary>
/// Function to delete a country form the repository and free the memory occupied by the element of the repo.
/// </summary>
/// <param name="v">the repository in which we want to perform the delete</param>
/// <param name="c">the country we want to delete</param>
/// <returns>1 for a succesfull delete, -1 otherwise</returns>
int deleteCountryRepo(CountryRepo* v, Country* c);

/// <summary>
/// Function to update a country with a new population
/// </summary>
/// <param name="v">the repository that contains the country we want to update</param>
/// <param name="c">the country we want to update</param>
/// <param name="population">the new population of the updated country</param>
/// <returns>1 for a succesfull update, -1 otherwise</returns>
int updateCountryRepo(CountryRepo* v, Country* c, int population);
/// <summary>
/// Function to find the length of the repository.
/// </summary>
/// <param name="v">the repository for which we want to calculate the length</param>
/// <returns>size of repository, -1 if repository is NULL</returns>
int getLength(CountryRepo* v);
/// <summary>
/// Function to Obtain the country object from the repo, knowing the position.
/// </summary>
/// <param name="v">the repository in which we search</param>
/// <param name="position">the position on which the country is situated</param>
/// <returns> Country structure , null otherwise</returns>
Country* getCountryFromPosition(CountryRepo* v, int position);

void setCountryToPosition(CountryRepo* v, int position, Country* c);

void testRepo();
