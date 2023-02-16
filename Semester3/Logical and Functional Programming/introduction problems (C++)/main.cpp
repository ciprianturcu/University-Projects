#include "lista.h"
#include <iostream>
using namespace std;


int main()
{
   Lista l;
   l=creare();
   Lista lis;
   lis = substitute(l);
   tipar(l);
   tipar(lis);

   Lista l1, l2;
   cout << "List A\n";
   l1 = creare();
   cout << "List B\n";
   l2 = creare();
	
   Lista res;
   res = difference(l1, l2);
   tipar(res);
}
