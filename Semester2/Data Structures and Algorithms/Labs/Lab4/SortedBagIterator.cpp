#include "SortedBagIterator.h"
#include "SortedBag.h"
#include <exception>

using namespace std;

SortedBagIterator::SortedBagIterator(const SortedBag& b) : bag(b) {
    this->index = 0;
    this->length = b.length;
    this->array = new TElem [this->length];
    this->key = 0;
    for(int i = 0 ; i < bag.capacity; i++)
        if (b.hashTable[i] != b.deleted && b.hashTable[i] != b.empty)
            this->array[this->key++] = b.hashTable[i];

    for(int i = 0; i < key - 1; i++)
        for(int j = i + 1; j < key; j++)
            if(!b.relation(this->array[i], this->array[j]))
            {
                TElem aux;
                aux = this->array[i];
                array[i] = array[j];
                array[j] = aux;

            }
}
//theta(key^2)

TComp SortedBagIterator::getCurrent() {
    if(!valid())
        throw std::exception();
    return this->array[this->index];
}
//theta(1)

bool SortedBagIterator::valid() {
    return this->index < this->length;
}
//theta(1)

void SortedBagIterator::next() {
    if(!valid())
        throw std::exception();
    this->index++;
}
//theta(1)

void SortedBagIterator::first() {
    this->index = 0;
}
//theta(1)

