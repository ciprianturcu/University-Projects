cmake_minimum_required(VERSION 3.21)
project(New_folder__3_)

set(CMAKE_CXX_STANDARD 14)

include_directories(.)

add_executable(New_folder__3_
        App.cpp
        ExtendedTest.cpp
        ExtendedTest.h
        Map.cpp
        Map.h
        MapIterator.cpp
        MapIterator.h
        ShortTest.cpp
        ShortTest.h DynamicVector.h)
