#pragma once
#include "Coat.h"

//typedef Recipe TElement;

template <typename TElement>
class DynamicVector
{
private:
    TElement* elems;
    int size;
    int capacity;

public:
    // default constructor for a DynamicVector
    DynamicVector(int capacity = 10);

    // copy constructor for a DynamicVector
    DynamicVector(const DynamicVector& v);
    ~DynamicVector();

    // assignment operator for a DynamicVector
    DynamicVector& operator=(const DynamicVector& v);

    // Adds an element to the current DynamicVector.
    void add_element(const TElement& e);

    //Deletes an element from the current DynamicVector.
    void delete_element(const TElement& e);

    //Finds an element and returns its position in the DynamicVector.
    int position_element(const TElement& e);

    //Updates an element
    void update_element(const TElement& e, const TElement& new_e);

    //Gets an element from the position
    TElement get_element_from_position(int position) const;

    int get_size() const;
    TElement* get_all_elems() const;

    /*
    DynamicVector& operator+(const TElement& e);

    friend DynamicVector operator+(const TElement& e,const DynamicVector& v)
    {
        DynamicVector temp = v;
        temp.add_element(e);
        return temp;
    }

    friend DynamicVector operator+(const DynamicVector& v, const TElement& e);
     */

private:
    // Resizes the current DynamicVector, multiplying its capacity by a given factor (real number).
    void resize(double factor = 2);
};

/*
DynamicVector operator+(const TElement& e,const DynamicVector& v)
{
    DynamicVector temp = v;
    temp.add_element(e);
    return temp;
}

/*
DynamicVector operator+(const DynamicVector& v, const TElement& e)
{
    DynamicVector temp = v;
    temp.add_element(e);
    return temp;
}
 */

#include "DynamicVector.h"

template <typename TElement>
DynamicVector<TElement>::DynamicVector(int capacity)
{
    this->size = 0;
    this->capacity = capacity;
    this->elems = new TElement[capacity];
}

template <typename TElement>
DynamicVector<TElement>::DynamicVector(const DynamicVector<TElement>& v)
{
    this->size = v.size;
    this->capacity = v.capacity;
    this->elems = new TElement[this->capacity];
    for (int i = 0; i < this->size; i++)
        this->elems[i] = v.elems[i];
}

template <typename TElement>
DynamicVector<TElement>::~DynamicVector()
{
    delete[] this->elems;
}

template <typename TElement>
DynamicVector<TElement>& DynamicVector<TElement>::operator=(const DynamicVector<TElement>& v)
{
    if (this == &v)
        return *this;

    this->size = v.size;
    this->capacity = v.capacity;

    delete[] this->elems;
    this->elems = new TElement[this->capacity];
    for (int i = 0; i < this->size; i++)
        this->elems[i] = v.elems[i];

    return *this;
}

template <typename TElement>
void DynamicVector<TElement>::add_element(const TElement& e)
{
    int position = position_element(e);
    if (position == -1)
    {
        if (this->size == this->capacity)
            this->resize();
        this->elems[this->size] = e;
        this->size++;
    }
}

template <typename TElement>
void DynamicVector<TElement>::resize(double factor)
{
    this->capacity *= static_cast<int>(factor);

    TElement* els = new TElement[this->capacity];
    for (int i = 0; i < this->size; i++)
        els[i] = this->elems[i];

    delete[] this->elems;
    this->elems = els;
}

template <typename TElement>
TElement* DynamicVector<TElement>::get_all_elems() const
{
    return this->elems;
}

template <typename TElement>
int DynamicVector<TElement>::get_size() const
{
    return this->size;
}

template <typename TElement>
void DynamicVector<TElement>::delete_element(const TElement& e) {
    int position = position_element(e);
    if (position != -1)
    {
        for (int i = position; i < this->size - 1; i++)
            this->elems[i] = this->elems[i + 1];
        this->size--;
    }
}

template <typename TElement>
int DynamicVector<TElement>::position_element(const TElement& e) {
    for (int i = 0; i < this->size; i++)
        if ((TElement)e == this->elems[i])
            return i;
    return -1;
}

template <typename TElement>
void DynamicVector<TElement>::update_element(const TElement& e, const TElement& new_e) {
    int position = position_element(e);
    if (position != -1)
    {
        this->elems[position] = new_e;
    }
}

template <typename TElement>
TElement DynamicVector<TElement>::get_element_from_position(int position) const {
    return elems[position];
}

/*
DynamicVector &DynamicVector::operator+(const TElement& e) {
    this->add_element(e);
    return *this;
}
*/