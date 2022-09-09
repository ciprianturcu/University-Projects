#include "SortedBag.h"
#include "SortedBagIterator.h"

SortedBag::SortedBag(Relation r) {
    //TODO - Implementation
    this->head = nullptr;
    this->nr_of_elems = 0;

}

void SortedBag::add(TComp e) {
    //TODO - Implementation
    Node *position = insertPosition(e);

    if (position == nullptr && this->head==nullptr) {
        Node *newNode = new Node;
        newNode->value = e;
        newNode->next = nullptr;
        newNode->frequency=1;
        this->head = newNode;
    }
    else if(position == nullptr && this->head != nullptr) {
        Node *lastNode = getBefore(nullptr);
        Node *newNode = new Node;
        newNode->value = e;
        newNode->next= nullptr;
        newNode->frequency=1;
        lastNode->next = newNode;
    }
    else if(position!= nullptr && position->value>e)
    {
        Node* afterNode = getBefore(position);
        Node* newNode = new Node;
        newNode->value = e;
        newNode->next = afterNode->next;
        newNode->frequency = 1;
        newNode->next = afterNode;
    }
    else if (position!= nullptr && position->value == e) {
        position->frequency++;
    }
    this->nr_of_elems++;
}


bool SortedBag::remove(TComp e) {
    //TODO - Implementation
    if(!search(e))
        return false;
    Node* position = getPosition(e);
    if(position!= nullptr)
    {
        if(position->frequency>1)
        {
            position->frequency--;
        }
        else if( position->frequency == 1)
        {
            Node* before = getBefore(position);
            Node* after = position->next;
            if(before == position && before == this->head)
            {
                Node* next = this->head->next;
                delete this->head;
                this->head = next;

            }
            else {
                before->next = after->next;
                delete position;
            }
        }
        this->nr_of_elems--;
        return true;
    }
    return false;
}


bool SortedBag::search(TComp elem) const {
    //TODO - Implementation
    Node* aux = this->head;
    while(aux!= nullptr) {
        if (aux->value == elem)
            return true;
        aux = aux->next;
    }
    return false;
}


int SortedBag::nrOccurrences(TComp elem) const {
    //TODO - Implementation
    Node* node = getPosition(elem);
    if(node != nullptr && node->value==elem)
        return node->frequency;
    return 0;
}



int SortedBag::size() const {
    //TODO - Implementation
    return this->nr_of_elems;
}


bool SortedBag::isEmpty() const {
    //TODO - Implementation
    return this->nr_of_elems==0;
}


SortedBagIterator SortedBag::iterator() const {
    return SortedBagIterator(*this);
}


SortedBag::~SortedBag() {
    //TODO - Implementation

}

SortedBag::Node *SortedBag::insertPosition(TElem elem) const {
    Node* aux = this->head;
    while(aux!= nullptr)
    {
        if(aux->value >= elem)
            return aux;
        aux = aux->next;
    }
    return aux;
}

SortedBag::Node *SortedBag::getBefore(Node* node) const {
    Node* aux = this->head;
    if(aux == node)
        return aux;
    if(aux->next==node)
        return aux;
    do {
        aux=aux->next;
    }
    while(aux->next!=node);
    return aux;
}

SortedBag::Node *SortedBag::getPosition(TElem elem) const {
    Node* aux = this->head;
    while(aux!= nullptr)
    {
        if(aux->value == elem)
            return aux;
        aux = aux->next;
    }
    return nullptr;

}

void SortedBag::addAll(const SortedBag& b) {
    Node* node = b.head;
    while(node != nullptr)
    {
        add(node->value);
        node=node->next;
    }
}



/*SortedBag::Node *SortedBag::search_position(TElem elem) const {
    Node *start = head;
    Node *last = nullptr;
    Node *mid;
    while (last == nullptr || last != start)
    {
        mid = middle(start, last);
        if (mid == nullptr)
            return nullptr;
        if (mid->value == elem)
            return mid;
        else if (mid->value < elem)
            start = mid->next;
        else
            last = mid;
    }
    return mid;
}

SortedBag::Node *SortedBag::middle(SortedBag::Node *start, SortedBag::Node *last) const {
    if (start == nullptr) {
        return nullptr;
    }
    Node *slow = start;
    Node *fast = start->next;
    while (fast != last) {
        fast = fast->next;
        if (fast != last) {
            slow = slow->next;
            fast = fast->next;
        }
    }
    return slow;
}
*/