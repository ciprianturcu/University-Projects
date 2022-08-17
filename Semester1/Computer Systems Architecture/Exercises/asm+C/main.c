#include <stdio.h>
#include <stdlib.h>

void function(int i, int *a, int *b);

int main()
{
    FILE *fptr= fopen("numbers.txt", "r");
    
    int a[100];
    int b[100];
    int num;
    int i;
    i=0;
    while (fscanf(fptr, "%d", &num) != EOF)//reading until the last number
    {
        a[i]=num;
        i=i+1;
    }
    int *p1=a+i-1; //pointer to the last element of the read vector
    int *p2=b; // pointer to start of result vector
    function(i,p1,p2);
    int j=0;
    while(j<i)
    {
        printf("%d",b[j]);
        printf(" ");
        j=j+1;
    }
    return 0;
    
}
