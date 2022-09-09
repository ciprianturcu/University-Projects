#include "Set.h"
#include "SetITerator.h"

Set::Set() {
	this->capacity= 100;
    this->size_nr=0;
    this->elems = new TElem[this->capacity];
}
//Theta(1)


bool Set::add(TElem elem) {
    if(search(elem))
        return false;
    if(this->size_nr == this->capacity)
        this->resize();
    this->elems[this->size_nr]=elem;
    this->size_nr++;
    return true;
}
//BC = Theta(n) WC = Theta(n) AC = Theta(n)


bool Set::remove(TElem elem) {
	//TODO - Implementation
    int position = get_position(elem);
    if(position==-1)
        return false;
    else {
        for (int i = position; i < this->size_nr; i++) {
            this->elems[i] = this->elems[i + 1];
        }
        this->size_nr--;
        return true;
    }
}
//BC = Theta(n) WC = Theta(n) AC = Theta(n)

bool Set::search(TElem elem) const {
    for(int i=0;i<this->size_nr;i++)
    {
        if (elem == this->elems[i])
            return true;
    }
	return false;
}
//BC = Theta(1) WC = Theta(n) AC = Theta(n)

int Set::size() const {
	return this->size_nr;
}
//Theta(1)


bool Set::isEmpty() const {
	return (this->size() == 0);
}
//Theta(1)


Set::~Set() {
	delete[] this->elems;
}
//Theta(1)


SetIterator Set::iterator() const {
	return SetIterator(*this);
}
//Theta(1)

void Set::resize() {
    auto new_list= new TElem [this->capacity*2];
    for ( int i=0;i<this->size_nr;i++)
    {
        new_list[i]=this->elems[i];
    }
    delete this->elems;
    this->elems = new_list;
    this->capacity*=2;

}
//Theta(n)

int Set::get_position(TElem elem) const {
    for(int i=0;i<this->size_nr;i++)
    {
        if (elem == this->elems[i])
            return i;
    }
    return -1;
}
//BC = Theta(1) WC = Theta(n) AC = Theta(n)


