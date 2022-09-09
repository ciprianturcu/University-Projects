#include "SortedBagIterator.h"
#include "SortedBag.h"
#include <exception>

using namespace std;

SortedBagIterator::SortedBagIterator(const SortedBag& b) : bag(b) {
	//TODO - Implementation
    this->first();
}

TComp SortedBagIterator::getCurrent() {
	//TODO - Implementation
    if (!this->valid())
        throw exception();
	return this->currentElement->value;
}

bool SortedBagIterator::valid() {
	//TODO - Implementation
	return this->currentElement!= nullptr;
}

void SortedBagIterator::next() {
	//TODO - Implementation
    if(!this->valid())
        throw exception();
    if(this->currentFrequency == 1) {
        this->currentElement = this->currentElement->next;
        if(this->currentElement!= nullptr)
            this->currentFrequency = this->currentElement->frequency;
    }
    else
    {
        this->currentFrequency--;
    }

}

void SortedBagIterator::first() {
	//TODO - Implementation
    this->currentElement = this->bag.head;
    if(this->currentElement != nullptr)
        this->currentFrequency = this->bag.head->frequency;
}

