#include "Service.h"
#include <stdlib.h>
#include <string.h>
#include <stdio.h>
#include "Operation.h"
#include "DynamicVector.h"
#include "assert.h"

Service* createService(CountryRepo* r)
{
	Service* s = (Service*)malloc(sizeof(Service));
	if (s == NULL)
		return NULL;
	s->repo = r;
	s->undoStack = createDynamicVector(100, destroyOperation);
	s->redoStack = createDynamicVector(100, destroyOperation);
	return s;
}

void destroyService(Service* s)
{
	if (s == NULL)
		return;
	destroyRepo(s->repo);
	destroyDynamicVector(s->undoStack); 
	destroyDynamicVector(s->redoStack); 

	free(s);
}

void sortAscendingByPopulation(CountryRepo* repo)
{
	
	int size = getLength(repo);
	
	for (int i = 0; i < size; i++)
	{
		for (int j = i+1; j < size; j++)
		{
			if (getCountryFromPosition(repo, i)->population > getCountryFromPosition(repo, j)->population)
			{
				swap(repo->countries, i,j);
			}
		}
	}
}

void initializeData(Service* service)
{
	addCountry(service, "Romania", "Europe", 20);
	addCountry(service, "USA", "North America", 160);
	addCountry(service, "Brasil", "South America", 80);
	addCountry(service, "Bulgaria", "Europe", 15);
	addCountry(service, "Italy", "Europe", 50);
	addCountry(service, "France", "Europe", 200);
	addCountry(service, "Spain", "Europe", 110);
	addCountry(service, "China", "Asia", 2000);
	addCountry(service, "Ireland", "Europe", 15);
	addCountry(service, "Belgium", "Europe", 60);
	addCountry(service, "Canada", "North America", 60);
	addCountry(service, "Mexico", "North America", 20);
	addCountry(service, "Argentina", "South America", 79);
	addCountry(service, "Poland", "Europe", 300);
	addCountry(service, "Greece", "Europe", 100);
}

int addCountry(Service* s, char* name, char* continent, int population)
{

	Country* c = createCountry(name, continent, population);
	int result = addCountryRepo(s->repo, c);
	
	if (result == -1)
	{
		destroyCountry(c);
		return -1;
	}
	else
	{
		addElement(s->undoStack, createOperation(ADD, c));
	}
	return result;
}

int deleteCountry(Service* s, Country* country)
{
	addElement(s->undoStack, createOperation(DELETE, country));
	return deleteCountryRepo(s->repo, country);
}

int updateCountry(Service* s, Country* c, int population)
{
	if (s == NULL || c == NULL)
	{
		return -1;
	}
	else
	{
		addElement(s->undoStack, createOperation(UPDATE, c));
		updateCountryRepo(s->repo, c, population);
		addElement(s->undoStack, createOperation(UPDATE, c));
	}

	return 1;
}

CountryRepo* filterByStringFunction(Service* s, char string[], typeFct typeFunction)
{
	CountryRepo* resultrepo = createRepo();
	CountryRepo* v = getRepo(s);
	if (v == NULL)
	{
		return;
	}
	int size = getLengthVector(v->countries);
	for (int i = 0; i < size; i++)
	{
		Country* c = getCountryFromPosition(v, i);
		if (string[0] == '\0' || typeFunction(c, string) == 1)
		{
			addCountryRepo(resultrepo, copyCountry(c));
		}
	}
	return resultrepo;
}

CountryRepo* filterByPopulation(CountryRepo* repo, int population)
{
	CountryRepo* resultrepo = createRepo();
	if (repo == NULL)
	{
		return NULL;
	}
	int size = getLengthVector(repo->countries);
	for (int i = 0; i < size; i++)
	{
		Country* c = getCountryFromPosition(repo, i);
		if (c->population >= population)
		{
			addCountryRepo(resultrepo, copyCountry(c));
		}
	}
	return resultrepo;
}

int updateUndoRedo(CountryRepo* repo, DynamicVector* stack1, DynamicVector* stack2, int stackSize, Operation* op, Country* c)
{
	Operation* op2 = get(stack1, stackSize - 2);
	Operation* op3 = get(stack1, stackSize - 3);
	Operation* op4 = get(stack1, stackSize - 4);

	Country* c2 = getOpObject(op2);
	Country* c3 = getOpObject(op3);
	Country* c4 = getOpObject(op4);

	addElement(stack2, copyOperation(op));
	addElement(stack2, copyOperation(op2));
	addElement(stack2, copyOperation(op3));
	addElement(stack2, copyOperation(op4));

	deleteCountryRepo(repo, c);
	addCountryRepo(repo, copyCountry(c2));
	deleteCountryRepo(repo, c3);
	addCountryRepo(repo, copyCountry(c4));

	deleteElement(stack1, stackSize - 1);
	deleteElement(stack1, stackSize - 2);
	deleteElement(stack1, stackSize - 3);
	deleteElement(stack1, stackSize - 4);
}

int undo(Service* s)
{
	if (s == NULL)
		return -1;
	int undoStackSize = getLengthVector(s->undoStack);
	if (undoStackSize == 0)
		return 1;
	Operation* op = get(s->undoStack, undoStackSize - 1);
	if (op == NULL)
	{
		return -1;
	}
	Country* c = getOpObject(op);
	if (c == NULL)
	{
		return -1;
	}

	if (getOpType(op) == ADD)
	{
		addElement(s->redoStack, createOperation(DELETE, c));
		deleteCountryRepo(s->repo, c);
		
		deleteElement(s->undoStack, undoStackSize - 1);
	}
	else if (getOpType(op) == DELETE)
	{
		addElement(s->redoStack, createOperation(ADD, c));
		
		addCountryRepo(s->repo, copyCountry(c));
		
		deleteElement(s->undoStack, undoStackSize - 1);
	}
	else if (getOpType(op) == UPDATE)
	{
		
		updateUndoRedo(s->repo, s->undoStack, s->redoStack, undoStackSize, op, c);
		
	}
	return 0;
}

int redo(Service* s)
{
	if (s == NULL)
	{
		return -1;
	}
	int redoStackSize = getLengthVector(s->redoStack);
	if (redoStackSize == 0)
		return 1;
	Operation* op=get(s->redoStack, redoStackSize-1);
	if (op == NULL)
	{
		return -1;
	}
	Country* c = getOpObject(op);
	if (c == NULL)
	{
		return -1;
	}
	if (getOpType(op) == ADD)
	{
		addElement(s->undoStack, createOperation(DELETE, c));
		deleteCountryRepo(s->repo, c);
		deleteElement(s->redoStack, redoStackSize - 1);
		
	}
	else if (getOpType(op) == DELETE)
	{
		addElement(s->undoStack, createOperation(ADD, c));
		addCountryRepo(s->repo, copyCountry(c));
		deleteElement(s->redoStack, redoStackSize-1);
	}
	else if (getOpType(op) == UPDATE)
	{
		updateUndoRedo(s->repo, s->redoStack, s->undoStack, redoStackSize, op, c);
	}
	return 0;
}

CountryRepo* getRepo(Service* s)
{
	return s->repo;
}

void testService()
{
	CountryRepo* repo = createRepo();
	Service* s = createService(repo);


	assert(addCountry(s, "a", "b", 1000) == 1);
	assert(getLengthVector(s->undoStack) == 1);
	assert(addCountry(s, "a", "b", 1000) == -1);
	
	CountryRepo* repo2 = getRepo(s);
	assert(getLength(repo2) == getLength(repo));
	assert(repo2 == repo);

	initializeData(s); 
	assert(getLength(s->repo) == 16);

	assert(getLengthVector(s->undoStack) == 16);
	undo(s);
	assert(getLengthVector(s->undoStack) == 15);

	assert(getLengthVector(s->redoStack) == 1);
	redo(s);
	assert(getLengthVector(s->redoStack) == 0);
	assert(getLengthVector(s->undoStack) == 16);

	CountryRepo* repo3 = filterByPopulation(s->repo, 40);
	assert(getLength(repo3) == 12);

	typeFct type = &isOnContinent;

	CountryRepo* repo4 = filterByStringFunction(s, "Europe", type);
	assert(getLength(repo4) == 9);


	destroyRepo(repo3);
	destroyRepo(repo4);
	destroyService(s);
}