#include "service.h"


Service::Service(Repo repo)
{
	this->repo = repo;
}

void Service::add(double& a, double& b, double& c)
{
	Equation e = Equation(a, b, c);
	this->repo.add(e);
}

float Service::computex1(Equation& e)
{
	float x1 = (sqrt(abs(e.getDel())) - e.getB())/ (2 * e.getA());
	return x1;
}

float Service::computex2(Equation& e)
{
	float x2 = (-1)*float(sqrt(e.getDel()) - e.getB()) / (2 * e.getA());
	return x2;
}
