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


char *strrev(char *str)
{
      char *p1, *p2;

      if (! str || ! *str)
            return str;
      for (p1 = str, p2 = str + strlen(str) - 1; p2 > p1; ++p1, --p2)
      {
            *p1 ^= *p2;
            *p2 ^= *p1;
            *p1 ^= *p2;
      }
      return str;
}


int main()
{
    SOCKET s;
    struct sockaddr_in server,client;

    s = socket(AF_INET, SOCK_STREAM,0);
    if(s<0)
    {
        perror("Error on server socket creation");
        return 1;
    }

    memset(&server, 0 , sizeof(server));
    server.sin_port = htons(1234);
    server.sin_family = AF_INET;    
    server.sin_addr.s_addr = INADDR_ANY;

    if(bind(s,(struct sockaddr*)&server, sizeof(server))<0)
    {
        perror("Bind Error");
        return 1;
    }

    listen(s,5);
    int l=sizeof(client);
    memset(&client, 0, sizeof(client));

    int c,err;

    while(1)
    {
        printf("Listening for incomming connections\n");
        c = accept(s, (struct sockaddr*) &client, &l);
        err = errno;

    if(c<0)
    {
        perror("accept error\n");
        continue;
    }

    char reversed[256];
    int res;
    char temp[256];
    
    res = recv(c, temp, sizeof(temp), 0);

    res = send(c,strrev(temp), strlen(temp)+1,0);

    if(res != strlen(reversed)) printf("Error sending result \n");

    closesocket(c);

    }

}