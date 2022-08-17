#pragma once
#include "Coat.h"
#include <vector>
#include <algorithm>

template<typename T>
class Comparator {
public:
    Comparator();
    virtual bool compare(T el1, T el2) = 0;
    virtual ~Comparator();
};

class ComparatorAscendingByPrice : public Comparator<Coat> {
public:
    ComparatorAscendingByPrice();
    bool compare(Coat el1, Coat el2) override;
    ~ComparatorAscendingByPrice() override;
};

class ComparatorAscendingByColour : public Comparator<Coat> {
public:
    ComparatorAscendingByColour();
    bool compare(Coat el1, Coat el2) override;
    ~ComparatorAscendingByColour() override;
};

template<typename T> std::vector<T> sortFunction(std::vector<T>, Comparator<T>* comp);

template<typename T>
inline std::vector<T> sortFunction(std::vector<T> v, Comparator<T>& comp)
{
    int ok = 0;
    do {
        ok = 1;
        for (int i = 1; i < v.size(); i++)
        {
            if (comp.compare(v[i], v[i - 1]))
            {
                ok = 0;
                std::swap(v[i], v[i - 1]);
            }
        }
    } while (!ok);
    
    return v;
}

template<typename T>
inline Comparator<T>::Comparator()
{
}

template<typename T>
inline Comparator<T>::~Comparator() = default;