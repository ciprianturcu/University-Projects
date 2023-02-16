#include "lista.h"
#include <iostream>

using namespace std;


PNod creare_rec(){
  TElem x;
  cout<<"x=";
  cin>>x;
  if (x==0)
    return NULL;
  else{
    PNod p=new Nod();
    p->e=x;
    p->urm=creare_rec();
    return p;
  }
}

PNod sub(Lista l, TElem v, int poz, int i)
{
    PNod p = new Nod();
    if (l._prim == NULL)
    {
        p = NULL;
        return p;
    }   
    else if (i == poz)
    {
        p->e = v;
        l._prim = l._prim->urm;
        p->urm = sub(l, v, poz, i + 1);
        return p;
    }
    else
    {
        p->e = l._prim->e;
        l._prim = l._prim->urm;
        p->urm = sub(l, v, poz, i + 1);
        return p;
    }
}

Lista substitute(Lista l)
{
    Lista lis;
    int poz;
    TElem v;
    cout << "poz=";
    cin >> poz;
    cout << "v=";
    cin >> v;
    lis._prim = sub(l,v,poz,0);
    return lis;
}

bool inclusion(PNod x, Lista l)
{
    while (l._prim != NULL)
    {
        if (l._prim->e == x->e)
            return true;
        l._prim = l._prim->urm;
    }
    return false;
}

PNod difference_rec(Lista a, Lista b)
{
    
    if (a._prim == NULL && b._prim == NULL)
    {
        PNod p = new Nod();
        p = NULL;
        return p;
    }
    else if (a._prim == NULL)
    {
        PNod p = new Nod();
        p = NULL;
        return p;
    }
    else if (b._prim == NULL)
    {
        PNod p = new Nod();
        p = a._prim;
        return p;
    }
    else if (inclusion(a._prim, b) == false)
    {
        PNod p = new Nod();
        p->e = a._prim->e;
        a._prim = a._prim->urm;
        p->urm = difference_rec(a, b);
        return p;
    }
    else if (inclusion(a._prim, b) == true)
    {
        a._prim = a._prim->urm;
        return difference_rec(a, b);
    }
}

Lista difference(Lista a, Lista b)
{
    Lista rez;
    rez._prim = difference_rec(a,b);
    return rez;
}

Lista creare(){
   Lista l;
   l._prim=creare_rec();
   return l;
}

void tipar_rec(PNod p){
   if (p!=NULL){
     cout<<p->e<<" ";
     tipar_rec(p->urm);
   }
}

void tipar(Lista l){
   tipar_rec(l._prim);
   cout << "\n";
}

void distrug_rec(PNod p){
   if (p!=NULL){
     distrug_rec(p->urm);
     delete p;
   }
}

void distrug(Lista l) {
	//se elibereaza memoria alocata nodurilor listei
    distrug_rec(l._prim);
}

