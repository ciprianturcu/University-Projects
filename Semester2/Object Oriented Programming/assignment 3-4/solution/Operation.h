#pragma once
#include "Country.h"

typedef enum
{
	ADD, DELETE, UPDATE
}operationType;

typedef struct
{
	operationType type;
	Country* c;
}Operation;

/// <summary>
/// Function to create initialize and allocate memory for a Operation structure
/// </summary>
/// <param name="type">Type of operation.</param>
/// <param name="c">Country structure</param>
/// <returns>Operation structure if possible, NULL otherwise</returns>
Operation* createOperation(operationType type, Country* c);

/// <summary>
/// Function to free the memory allocated for an Operation structure and delete it.
/// </summary>
/// <param name="o">Operation to be destroyed</param>
void destroyOperation(Operation* o);

/// <summary>
/// Function to obtain the type of an operation
/// </summary>
/// <param name="o">Operation from which we get the type</param>
/// <returns>the operationType if possible, NULL otherwise</returns>
operationType getOpType(Operation* o);
Country* getOpObject(Operation* o);

Operation* copyOperation(Operation* o);

void testOperation();