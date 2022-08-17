#pragma once
#include <vector>
#include "domain.h"
class Repo
{
private:
	vector<Equation> arr;
public:
	Repo();
	void readFile();
	vector<Equation> getAll() { return this->arr; }
	void add(Equation& e) { this->arr.push_back(e); };
};