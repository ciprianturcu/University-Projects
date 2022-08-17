#pragma once
#include "repo.h"
class Service
{
public:
	Repo repo;
public:
	Service(Repo repo);
	vector<Equation> getAll() { return this->repo.getAll(); }
	void add(double& a, double & b, double & c);
	float computex1(Equation& e);
	float computex2(Equation& e);

};