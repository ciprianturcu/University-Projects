#include "comparator.h"

ComparatorAscendingByPrice::ComparatorAscendingByPrice()
{
}

bool ComparatorAscendingByPrice::compare(Coat el1, Coat el2)
{
    return el1.getPrice() < el2.getPrice();
}

ComparatorAscendingByPrice::~ComparatorAscendingByPrice() = default;

ComparatorAscendingByColour::ComparatorAscendingByColour()
{
}

bool ComparatorAscendingByColour::compare(Coat el1, Coat el2)
{
    if (el1.getColour().compare(el2.getColour()) < 0) {
        return true;
    }
    return false;
}

ComparatorAscendingByColour::~ComparatorAscendingByColour() = default;