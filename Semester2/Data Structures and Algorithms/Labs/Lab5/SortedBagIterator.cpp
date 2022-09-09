#include "SortedBagIterator.h"
#include "SortedBag.h"
#include <exception>

using namespace std;

SortedBagIterator::SortedBagIterator(const SortedBag &b) : bag(b), stack{std::stack<int>{}} {
    this->first();
}

TComp SortedBagIterator::getCurrent() {
    if (this->valid()) {
        return this->bag.info[this->current];
    }
    throw std::exception();
}

bool SortedBagIterator::valid() {
    return this->current != -1;
}

void SortedBagIterator::next() {
    if(!valid())
        throw exception();
    int node = stack.top();
    stack.pop();

    if(bag.rightChild[node]!=-1)
    {
        node = bag.rightChild[node];
        while(node != -1)
        {
            stack.push(node);
            node = bag.leftChild[node];
        }
    }
    if(!stack.empty())
        current = stack.top();
    else
        current =-1;
}

void SortedBagIterator::first() {
    this->current = bag.root;
    stack = std::stack<int>{};
    while(current != -1)
    {
        stack.push(current);
        current = bag.leftChild[current];
    }
    if(!stack.empty())
        current = stack.top();
    else
        current =-1;
}


