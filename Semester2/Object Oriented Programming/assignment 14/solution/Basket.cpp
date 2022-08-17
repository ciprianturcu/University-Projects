#include "Basket.h"
#include "RepositoryExceptions.h"


Basket::Basket()
{
    this->current = 0;
    this->sum = 0;
}

void Basket::add(const Coat& c)
{
    auto result = std::find(begin(this->coats), end(this->coats), c);
    if (result != std::end(this->coats))
    {
        this->coats.at(std::distance(this->coats.begin(), result)).setQuantity(this->coats.at(std::distance(this->coats.begin(), result)).getQuantity() + 1);
        this->sum = this->sum + this->coats.at(std::distance(this->coats.begin(), result)).getPrice();
        return;
    }
    Coat new_coat = Coat(c);
    new_coat.setQuantity(1);
    this->coats.push_back(new_coat);
    this->sum = this->sum + new_coat.getPrice();
}

 void Basket::remove(Coat& c)
{
     auto result = std::find(begin(this->coats), end(this->coats), c);
     if (result != std::end(this->coats))
     {
         if (this->coats.at(std::distance(this->coats.begin(), result)).getQuantity()>1)
         {
             this->coats.at(std::distance(this->coats.begin(), result)).setQuantity(this->coats.at(std::distance(this->coats.begin(), result)).getQuantity() - 1);
             this->sum = this->sum - this->coats.at(std::distance(this->coats.begin(), result)).getPrice();
             return;
         }
         else if (this->coats.at(std::distance(this->coats.begin(), result)).getQuantity() == 1)
         {
             int pos = getPos(c);
             //std::remove(this->coats.begin(), this->coats.end(), c);
             this->coats.erase(this->coats.begin() + pos);
             this->sum = this->sum - c.getPrice();
             return;
         }
     }
     
}

 int Basket::getPos(Coat& c)
 {
     int cnt = 0;
     for (auto i : this->coats) {
         if (i == c) {
             return cnt;
         }
         cnt++;
     }
     return -1;
 }

Coat Basket::getCurrent()
{
    if (this->current == this->coats.size())
        this->current = 0;
    return this->coats[this->current];
    return Coat{};
}

void Basket::next()
{
    if (this->coats.size() == 0)
        return;
    this->current++;
    Coat currentCoat = getCurrent();
    currentCoat.display_link();
}

bool Basket::isEmpty()
{
    return this->coats.size() == 0;
}

int Basket::getSum()
{
    return this->sum;
}

std::vector<Coat> Basket::getAllCoats()
{
    return coats;
}
