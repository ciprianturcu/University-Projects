cmake_minimum_required(VERSION 3.21)
project(New_folder)

set(CMAKE_CXX_STANDARD 14)

include_directories(.)

add_executable(New_folder
        App.cpp
        ExtendedTest.cpp
        ExtendedTest.h
        Set.cpp
        Set.h
        SetIterator.cpp
        SetIterator.h
        ShortTest.cpp
        ShortTest.h)
