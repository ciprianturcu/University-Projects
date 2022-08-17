#pragma once
#include <string>
using namespace std;

class Equation
{
private:
	double a;
	double b;
	double c;
public:
	Equation() {
		this->a = 0;
		this->b = 0;
		this->c = 0;
	}
	Equation(double& a, double& b, double& c) {
		this->a = a;
		this->b = b;
		this->c = c;
	}
	void setA(double a) { this->a = a;}
	void setB(double b) { this->b = b; }
	void setC(double c) { this->c = c; }
	double getA() { return this->a; }
	double getB() { return this->b; }
	double getC() { return this->c; }
	string toString() { return string(to_string(getA())+" * x ^ 2 + "+to_string(getB()) + " * x + "+ to_string(getC())); }
	float getDel() {
		double x = getB() * getB() - 4 * getA() * getC();
		return x;
	}
	friend std::istream& operator>>(std::istream& is, Equation& e);
};