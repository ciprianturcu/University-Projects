#pragma once
#include <exception>
#include <string>

class FileException : public std::exception
{
protected:
	std::string message;
public:
	FileException(const std::string& msg);
	virtual const char* what();

};

class RepositoryException : public std::exception
{
protected:
	std::string message;
public:
	RepositoryException();
	RepositoryException(const std::string& msg);
	virtual ~RepositoryException() {}
	virtual const char* what();

};

class DuplicateCoat : public RepositoryException
{
public:

	const char* what();
};

class InexistentCoat : public RepositoryException
{
public:

	const char* what();

};

class RepoError : public std::exception {
private:
	const char* message;
public:
	explicit RepoError(const char* message);
	[[nodiscard]] const char* what() const noexcept;
};