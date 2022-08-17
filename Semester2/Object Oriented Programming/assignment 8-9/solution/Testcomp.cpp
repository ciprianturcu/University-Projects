#include "TestComp.h"
#include <vector>
#include <cassert>

void testComparator()
{
    Coat c1 = Coat("XL", "Black", 40, 80, "https://bit.ly/38Ekxav");
    Coat c2 = Coat("S", "Red", 100,40, "https://bit.ly/3DVDsJp");
    Coat c3 = Coat("M", "White", 300, 150, "https://bit.ly/38rDAEI");

    std::vector<Coat> coatList;
    coatList.push_back(c1);
    coatList.push_back(c2);
    coatList.push_back(c3);

    auto* l1 = new ComparatorAscendingByPrice();
    std::vector<Coat> v1 = sortFunction(coatList, *l1);
    assert(v1[0].getPrice() == 40);
    assert(v1[1].getPrice() == 100);
    assert(v1[2].getPrice() == 300);

    auto* l2 = new ComparatorAscendingByColour();
    std::vector<Coat> v2 = sortFunction(coatList, *l2);
    assert(v2[0].getColour() == "Black");
    assert(v2[1].getColour() == "Red");
    assert(v2[2].getColour() == "White");

    delete l1;
    delete l2;
}