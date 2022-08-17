#pragma once

typedef struct
{
	char* name;
	char* continent;
	int population;
}Country;

/// <summary>
/// Function to allocate the memory for a function and create it by giving atributes.
/// </summary>
/// <param name="name"> Char , name of the country</param>
/// <param name="continent"> Char name of the country's continent</param>
/// <param name="population"> Int, population of a country</param>
/// <returns>a Country structure</returns>
Country* createCountry(char* name, char* continent, int population);
/// <summary>
/// Function to free the memory occupied by a Country structure
/// </summary>
/// <param name="c"> Country structure representing the country we want to delete.</param>
void destroyCountry(Country* c);

/// <summary>
/// Function to obtain the name of a country
/// </summary>
/// <param name="c">Country structure</param>
/// <returns>Char string containing the country name.</returns>
char* getName(Country* c);
/// <summary>
/// Function to get the continent on which a country is situated.
/// </summary>
/// <param name="c">Country structure</param>
/// <returns>Char string containing the county continent.</returns>
char* getContinent(Country* c);
/// <summary>
/// Function to obtain the population of a country
/// </summary>
/// <param name="c">Country structure</param>
/// <returns>Integer representing the popualtion of a country in millions.</returns>
int getPopulation(Country* c);
/// <summary>
/// Function to set the population of a country.
/// </summary>
/// <param name="c">Country structure</param>
/// <param name="newPopulation">Integer, representing the new population of the country c.</param>
/// <returns>1 if the set was succesfull ,-1 otherwise</returns>
int setPopulation(Country* c, int newPopulation);
/// <summary>
/// Function to get the string form of a country.(used for displaying the country.)
/// </summary>
/// <param name="c">Country structure</param>
/// <param name="str">Char, used as destination for the created string.</param>
void toString(Country* c, char str[]);
/// <summary>
/// Function to determine if a subsring of characters is within the name of a country.
/// </summary>
/// <param name="c">Country structure, in which we search for.</param>
/// <param name="string">Char string, representing what we want to search.</param>
/// <returns>1 if the substring was found, 0 otherwise</returns>
int isSubStringInName(Country* c, char string[]);
/// <summary>
/// Function to determine if a country is on a given continent.
/// </summary>
/// <param name="c">Country structure,which we want to search</param>
/// <param name="continent">Char string that contains the continent we need to verify</param>
/// <returns>1 if true, 0 otherwise</returns>
int isOnContinent(Country* c, char continent[]);
/// <summary>
/// Function to create a copy of a given Country.
/// <summary>
/// <param name="c">Country structure, which we want to copy.</param>
/// <returns> an exact copy of the Country structure c</returns>
Country* copyCountry(Country* c);