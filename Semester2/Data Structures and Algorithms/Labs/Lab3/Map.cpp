#include "Map.h"
#include "MapIterator.h"

Map::Map() {
    this->capacity = ARRAY_INITIAL_CAPACITY;
    this->nodes = DynamicVector<Node>(this->capacity + 1);
    this->head = -1;
    this->tail = -1;
    this->first_empty = 1;
    for (int i = 1; i < this->capacity; i++)
        this->nodes[i].next = i + 1;
    this->nodes[this->capacity].next = -1;
}

Map::~Map() {
    //TODO - Implementation
}
//theta(n)
TValue Map::add(TKey c, TValue v) {
    int key_index = this->search_return_index(c);
    //if the value is already in the map, we update the value and return the old value.
    if (key_index != -1) {
        auto old_value = this->nodes[key_index].element.second;
        this->nodes[key_index].element.second = v;
        return old_value;
    }
    //allocating a new element
    int new_index = this->allocate();
    if(new_index==-1) // case where array is full, we resize then allocate
    {
        this->resize_array();
        new_index = this->allocate();
    }
    if(new_index != -1)
    {
        //create a TElem and store it in the newly allocated element
        TElem new_element;
        new_element.first = c;
        new_element.second = v;
        this->nodes[new_index].element = new_element;
        //add the element at the end
        //set the links
        if(this->tail == -1) //element tail
        {
            this->tail = new_index;
            this->nodes[new_index].prev = -1;
            this->nodes[new_index].next =-1;
        }
        else //element not tail
        {
            this->nodes[this->tail].next = new_index;
            this->nodes[new_index].prev = this->tail;
            this->nodes[new_index].next = -1;
            this->tail = new_index;
        }
        this->nodes.a_size++;
    }
    if(this->head == -1)
        this->head = new_index;
    return NULL_TVALUE;
}
//theta(n)
TValue Map::search(TKey c) const {
    int current = this->head;
    while(current != -1 && this->nodes[current].element.first != c)
    {
        current = this->nodes[current].next;
    }
    if(current != -1)
        return this->nodes[current].element.second;

    return NULL_TVALUE;
}
//theta(n)
TValue Map::remove(TKey c) {
    int key_index = this->search_return_index(c);
    if(key_index != -1) // element is found
    {
        if(key_index == this->head && key_index == this->tail) // if the element is the only one we make the list empty
        {
            this->head =-1;
            this->tail = -1;
        }
        else if(key_index == this->head)  //if it is the head we move one position and cut the link to the first element
        {
            this->head = this->nodes[head].next;
            this->nodes[head].prev = -1;
        }
        else if(key_index == this->tail) // if the element is the tail we simply go back with the tail and remove the link to the old tail
        {
            this->tail = this->nodes[tail].prev;
            this->nodes[tail].next = -1;
        }
        else //element is inside the list
        {
            int prev_node = this->nodes[key_index].prev;
            int next_node = this->nodes[key_index].next;
            this->nodes[prev_node].next = next_node;
            this->nodes[next_node].prev = prev_node;
        }
        auto old_value = this->nodes[key_index].element.second;
        this->free(key_index);
        this->nodes.a_size--;
        return old_value;
    }
    if(this->size()==0)
    {
        this->head = -1;
        this->tail = -1;
        this->first_empty = 1;
        for(int i = 1; i< this->capacity; i++)
            this->nodes[i].next = i + 1;
        this->nodes[this->capacity].next = -1;
    }
    return NULL_TVALUE;
}

//theta(1)
int Map::size() const {
    return this->nodes.a_size;
    return 0;
}
//theta(1)
bool Map::isEmpty() const {
    return this->nodes.a_size == 0;
}
//theta(1)
MapIterator Map::iterator() const {
    return MapIterator(*this);
}
//theta(n)
TValue Map::search_return_index(TKey c) {
    int current = this->head;
    while (current != -1 && this->nodes[current].element.first != c) {
        current = this->nodes[current].next;
    }
    return current;
}
//theta(n)
void Map::resize_array() {
    this->nodes.resize();
    int old_cap = this->capacity;
    this->capacity *= RESIZE_FACTOR;
    for (int i = old_cap + 1; i < this->capacity; i++) {
        this->nodes[i].next = i + 1;
        this->nodes[i].prev = i - 1;
    }
    this->nodes[old_cap+1].prev = -1;
    this->nodes[this->capacity-1].next=-1;
    this->first_empty = old_cap + 1;
}
//theta(1)
TValue Map::allocate() {
    int new_elem = this->first_empty;
    if(new_elem!=-1)
    {
        this->first_empty = this->nodes[this->first_empty].next;
        if(this->first_empty != -1)
        {
            this->nodes[this->first_empty].prev = -1;
        }
        this->nodes[new_elem].next = -1;
        this->nodes[new_elem].prev = -1;
    }
    return new_elem;
}
//theta(1)
void Map::free(int position) {
    this->nodes[position].next = this->first_empty;
    this->nodes[position].prev =-1;
    if(this->first_empty !=-1)
    {
        this->nodes[this->first_empty].prev = position;
    }
    this->first_empty = position;
}



