#define _WINSOCK_DEPRECATED_NO_WARNINGS 1

// exists on all platforms

#include <stdio.h>

 

// this section will only be compiled on NON Windows platforms

#ifndef _WIN32

#include <sys/types.h>

#include <sys/socket.h>

#include <netinet/in.h>

#include <netinet/ip.h>

#include <string.h>

#include <arpa/inet.h>

#define max 100

#include <stdlib.h>

 

 

#include <unistd.h>

#include <errno.h>

#define closesocket close

typedef int SOCKET;

#else

// on Windows include and link these things

#include<WinSock2.h>

// for uint16_t an so

#include<cstdint>

 

// this is how we can link a library directly from the source code with the VC++ compiler – otherwise got o project settings and link to it explicitly

//#pragma comment(lib,"Ws2_32.lib")

#endif

int main()
{
    int c;
    struct sockaddr_in server;
    c = socket(AF_INET, SOCK_STREAM, 0);
    if(c<0)
    {
        perror("error creating client socket");
        return 1;
    }

    memset(&server,0,sizeof(server));

    server.sin_port = htons(1234);
    server.sin_family = AF_INET;
    server.sin_addr.s_addr = inet_addr("127.0.0.1");

    if(connect(c, (struct sockaddr*) &server,sizeof(server))<0)
    {
        perror("error connecting to server");
        return 1;

    }
    uint16_t x;
    printf("enter number=");
    scanf("%hu", &x);
    x=htons(x);
    send(c,&x,sizeof(x),0);

    int res;
    
    printf("the divisors are:\n");
    res = recv(c,(char*)&x,sizeof(x),0);
    do
    {
        printf("%hu ",x);
        res = recv(c,&x,sizeof(x),0);
    } while (x!=0);
    printf("\n");
    

    closesocket(c);
}