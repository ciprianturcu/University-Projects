cmake_minimum_required(VERSION 3.21)
project(New_folder__2_)

set(CMAKE_CXX_STANDARD 14)

include_directories(.)

add_executable(New_folder__2_
        App.cpp
        ExtendedTest.cpp
        ExtendedTest.h
        ShortTest.cpp
        ShortTest.h
        SortedBag.cpp
        SortedBag.h
        SortedBagIterator.cpp
        SortedBagIterator.h)
