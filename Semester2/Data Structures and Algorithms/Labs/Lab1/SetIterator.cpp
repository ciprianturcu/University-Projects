#include "SetIterator.h"
#include "Set.h"
#include <exception>

using namespace std;


SetIterator::SetIterator(const Set& m) : set(m)
{
	this->current=0;
}


void SetIterator::first() {
	this->current=0;
}


void SetIterator::next() {
	//TODO - Implementation
    if(!this->valid())
        throw exception();
    else
        this->current++;
}


TElem SetIterator::getCurrent()
{
	//TODO - Implementation
    if(!this->valid())
        throw exception();
    else
        return this->set.elems[this->current];
}

bool SetIterator::valid() const {
	//TODO - Implementation
    if(this->current<this->set.size_nr)
        return true;
	return false;
}

void SetIterator::jumpForward(int k) {
    if( this->current + k > this->set.size_nr)
        throw exception();
    this->current=this->current+k;
}



