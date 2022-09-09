#include "Map.h"
#include "MapIterator.h"
#include <exception>
using namespace std;


MapIterator::MapIterator(const Map& d) : map(d)
{
	this->first();
}


void MapIterator::first() {
	this->current = this->map.head;
}


void MapIterator::next() {
    if(!this->valid())
        throw exception();
	this->current = this->map.nodes[this->current].next;

}


TElem MapIterator::getCurrent(){
	if(!this->valid())
    {
        throw exception();
    }
	return this->map.nodes[this->current].element;
}


bool MapIterator::valid() const {
	return this->current!=-1;
}

void MapIterator::jumpBackward(int k) {

    for(int i=0;i<k && this->current!= -1;i++)
    {
        this->current=this->map.nodes[this->current].prev;
    }
    if(k<=0)
        throw exception();
    if(!this->valid())
        throw exception();


}



