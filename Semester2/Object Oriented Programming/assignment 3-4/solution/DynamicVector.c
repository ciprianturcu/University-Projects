#include "DynamicVector.h"
#include "stdlib.h"
#include "assert.h"

DynamicVector* createDynamicVector(int capacity, destroyFct destroy)
{
	DynamicVector* v = (DynamicVector*)malloc(sizeof(DynamicVector));
	if (v == NULL)
	{
		return NULL;
	}
	v->capacity = capacity;
	v->size = 0;
	v->elements = malloc(sizeof(TElem) * v->capacity);
	if (v->elements == NULL)
	{
		free(v);
		return NULL;
	}
	v->destroy = destroy;
	return v; 
}

void destroyDynamicVector(DynamicVector* v)
{
	if (v == NULL)
	{
		return;
	}
	if (v->elements == NULL)
		return;
	for (int i = 0; i < v->size; i++)
	{
		v->destroy(v->elements[i]);
	}
	free(v->elements);
	v->elements = NULL;
	free(v);
	

}

TElem get(DynamicVector* v, int pos)
{
	if (v == NULL || pos < 0)
		return NULL;
	return v->elements[pos];
}

void swap(DynamicVector* v , int pos1 , int pos2)
{
	TElem *temp;
	temp = v->elements[pos1];
	v->elements[pos1] = v->elements[pos2];
	v->elements[pos2] = temp;
}


void set(DynamicVector* v, int pos, TElem new_elem)
{
	v->destroy(v->elements[pos]);

	v->elements[pos] = new_elem;
	for (int i = pos; i < v->size; i++)
	{
		v->elements[pos] = v->elements[pos + 1];
	}
}

void resize(DynamicVector* v)
{
	if (v == NULL)
	{
		return;
	}
	v->capacity *= 2;
	TElem* new_elements = (TElem*)malloc(sizeof(TElem) * v->capacity);
	if (new_elements == NULL)
	{
		return;
	}
	for (int i = 0; i < v->size; i++)
		new_elements[i] = v->elements[i];
	free(v->elements);
	v->elements = new_elements;

}

void addElement(DynamicVector* v, TElem element)
{

	if (v == NULL)
	{
		return;
	}
	int old_capacity = v->capacity;

	if (v->size == v->capacity)
	{
		resize(v);
		if (old_capacity == v->capacity) // resize failed
			return;
	}
	v->elements[v->size++] = element;
}

void deleteElement(DynamicVector* v, int position)
{
	if (v == NULL || position <0 || position > v->size)
		return;
	v->destroy(v->elements[position]);
	if (position != v->size - 1)
	{
		v->elements[position] = v->elements[v->size - 1];
	}
	v->size--;
}


int getLengthVector(DynamicVector* v)
{
	if (v == NULL)
		return -1;
	return v->size;
}

void testVector()
{
	DynamicVector* v = createDynamicVector(2, destroyCountry);

	Country* c1 = createCountry("France", "Europe", 2000);
	Country* c2 = createCountry("Germany", "Europe", 2000);
	Country* c3 = createCountry("Spain", "Europe", 2000);

	addElement(v, c1);
	addElement(v, c2);

	assert(v->capacity == 2);

	addElement(v, c3);

	assert(v->size == 3);
	assert(v->capacity == 4);

	deleteElement(v, 2);
	assert(v->size == 2);
	assert(v->capacity == 4);

	assert(getLengthVector(v) == 2);

	resize(v);

	assert(v->capacity == 8);

	destroyDynamicVector(v);

}