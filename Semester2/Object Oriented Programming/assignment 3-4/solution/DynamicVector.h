#pragma once
#include "Country.h"

typedef void* TElem;

typedef void (*destroyFct)(TElem);


typedef struct
{
	TElem* elements;
	int size, capacity;
	destroyFct destroy;
}DynamicVector;

/// <summary>
/// Function to create, initialize and allocate memory for a DynamicVector array.
/// </summary>
/// <param name="capacity">the capacity of the array</param>
/// <param name="destroy">the destroy function of the elements</param>
/// <returns>initialized DynamicVector structure, or null if it couldn't be created</returns>
DynamicVector* createDynamicVector(int capacity, destroyFct destroy);

/// <summary>
/// Function to dealocate(free) the memory used by a DynamicVector structure
/// </summary>
/// <param name="v">DynamicVector, the array we want to delete</param>
void destroyDynamicVector(DynamicVector* v);

/// <summary>
/// Function to obtain an element form the array, knowing the position.
/// </summary>
/// <param name="arr">DynamicVector, the array in which we want to search.</param>
/// <param name="pos">Integer, the position of the searched element</param>
/// <returns>the element if found, null otherwise</returns>
TElem get(DynamicVector* arr, int pos);

void swap(DynamicVector* v, int pos1, int pos2);


/// <summary>
/// Function to obtain the length of the array
/// </summary>
/// <param name="v">the array for which we want to find the length</param>
/// <returns>the size of the array if posiible, -1 otherwise</returns>
int getLengthVector(DynamicVector* v);

/// <summary>
/// Function to add an element to the array
/// </summary>
/// <param name="v">the array in which we add</param>
/// <param name="element">element to be added</param>
void addElement(DynamicVector* v, TElem element);
/// <summary>
/// Function to delte an element from the array
/// </summary>
/// <param name="v">the array in which we delete</param>
/// <param name="position">the position of the item we want to delte</param>
void deleteElement(DynamicVector* v, int position);

/// <summary>
/// Function to set an element to a given position
/// </summary>
/// <param name="v">array</param>
/// <param name="pos">position in array</param>
/// <param name="new_elem">element we want to put</param>
void set(DynamicVector* v, int pos, TElem new_elem);

void testVector();