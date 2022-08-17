#include "Operation.h"
#include <stdlib.h>
#include "assert.h"
#include <stdio.h>

Operation* createOperation(operationType type, Country* c)
{
    Operation* op = malloc(sizeof(Operation));
    if (op == NULL)
    {
        return NULL;
    }
    op->type = type;
    Country* copyOfC = createCountry(c->name,c->continent,c->population);
    op->c = copyOfC;

    return op;

}

void destroyOperation(Operation* o)
{
    if (o == NULL)
    {
        return;
    }
    
    destroyCountry(o->c);
    free(o);

}

operationType getOpType(Operation* o)
{
    if (o == NULL)
        return -1;
    return o->type;
}

Country* getOpObject(Operation* o)
{
    if (o == NULL)
        return NULL;
    return o->c;
}

Operation* copyOperation(Operation* o)
{
    if (o == NULL)
        return;
    return createOperation(o->type, o->c);
}

void testOperation()
{
    Country* c = createCountry("France", "Europe", 1000);

    Operation* o1 = createOperation(ADD, c);
    Operation* o2 = createOperation(DELETE, c);
    Operation* o3 = createOperation(UPDATE, c);

    assert(getOpType(o1) == ADD);
    assert(getOpType(o2) == DELETE);
    assert(getOpType(o3) == UPDATE);

    assert(strcmp(getOpObject(o1)->name,c->name) == 0);
    assert(getOpObject(o1)->population == c->population);
    assert(strcmp(getOpObject(o1)->continent,c->continent) == 0);
    destroyOperation(o1);
    destroyOperation(o2);
    destroyOperation(o3);
    destroyCountry(c);

}

