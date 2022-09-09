#pragma once
#include "SortedBag.h"
#include <stack>
class SortedBag;

class SortedBagIterator
{
	friend class SortedBag;

private:
	const SortedBag& bag;
    int current;
    std::stack<int> stack;
	explicit SortedBagIterator(const SortedBag& b);

	//TODO - Representation

public:
	TComp getCurrent();
	bool valid();
	void next();
	void first();

};

