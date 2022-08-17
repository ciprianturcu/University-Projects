#include "RepositoryExceptions.h"

FileException::FileException(const std::string& msg) : message{msg}
{
}

const char* FileException::what()
{
    return message.c_str();
}

RepositoryException::RepositoryException()
{
}

RepositoryException::RepositoryException(const std::string& msg) : message{msg}
{
}

const char* RepositoryException::what()
{
    return this->message.c_str();
}

const char* DuplicateCoat::what()
{
    return "There is anoter coat with the same atributes.";
}

const char* InexistentCoat::what()
{
    return "There is no coat matching.";
}
