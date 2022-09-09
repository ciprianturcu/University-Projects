//
// Created by cipri on 10/05/2022.
//

#ifndef NEW_FOLDER__3__DYNAMICVECTOR_H
#define NEW_FOLDER__3__DYNAMICVECTOR_H

#endif //NEW_FOLDER__3__DYNAMICVECTOR_H
#pragma once
#define RESIZE_FACTOR 2
#define ARRAY_INITIAL_CAPACITY 10

#include <exception>

template<typename T>
class DynamicVector {
    friend class Map;

    friend class MapIterator;

private:
    int capacity{}, a_size{};
    T *array;
public:
    explicit DynamicVector(int capacity = ARRAY_INITIAL_CAPACITY);

    DynamicVector(const DynamicVector &v);

    ~DynamicVector();

    T &operator[](int position);

    const T &operator[](int position) const;

    DynamicVector &operator=(const DynamicVector &v);

    void resize(int resize_factor = RESIZE_FACTOR);

};

template<typename T>
inline DynamicVector<T>::DynamicVector(const DynamicVector<T> &v) {
    this->capacity = v.capacity;
    this->a_size = v.a_size;
    this->array = v.array;
    for (int i = 0; i < this->a_size; i++) {
        this->array[i] = v.array[i];
    }
}

template<typename T>
inline DynamicVector<T>::DynamicVector(int capacity) {
    this->a_size = 0;
    this->capacity = capacity;
    this->array = new T[capacity];
}

template<typename T>
inline DynamicVector<T>::~DynamicVector<T>() {
    delete this->array;
}

template<typename T>
inline T &DynamicVector<T>::operator[](int position) {
    if (position >= this->capacity || position < 0)
        throw std::exception();
    return this->array[position];
}

template<typename T>
inline DynamicVector<T> &DynamicVector<T>::operator=(const DynamicVector<T> &v) {
    if (this == &v)
        return *this;

    this->a_size = v.a_size;
    this->capacity = v.capacity;
    delete[] this->array;
    this->array = new T[this->capacity];
    for (int i = 0; i < this->capacity; i++)
        this->array[i] = v.array[i];

    return *this;
}

template<typename T>
inline const T &DynamicVector<T>::operator[](int position) const {
    if (position >= this->capacity || position < 0)
        throw std::exception();
    return this->array[position];
}

template<typename T>
inline void DynamicVector<T>::resize(int resize_factor) {
    int old_cap = this->capacity;
    this->capacity *= resize_factor;
    T* new_v = new T[this->capacity];
    for(int i=0;i<old_cap;i++)
    {
        new_v[i]=this->array[i];
    }
    delete[] this->array;
    this->array=new_v;
}