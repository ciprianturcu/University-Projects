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

    SOCKET s;
    struct sockaddr_in server, client;

    //creating server socket
    s = socket(AF_INET, SOCK_STREAM,0);
    if(s<0)
    {
        perror("Error on client socket creation");
        return 1;
    }

    //initializing socket values
    memset(&server, 0, sizeof(server));
    server.sin_port = htons(1234);
    server.sin_family = AF_INET;
    server.sin_addr.s_addr = INADDR_ANY;

    //we bind
    if(bind(s,(struct sockaddr*)&server, sizeof(server))<0)
    {
        perror("Error at binding");
        return 1;
    }

    //listen to max 5 conn
    listen(s,5);
    int l = sizeof(client);
    //clearing client socket
    memset(&client,0,sizeof(client));

    int c,err;

    while(1)
    {
        printf("Listening for incomming connections\n");
        //connection from a client
        c = accept(s, (struct sockaddr*)&client, &l);
        err =errno;

        if(c<0)
        {
            perror("accept error");
            continue;
        }

        uint16_t nr;
        int res;

        res = recv(c, (char*)&nr, sizeof(nr), 0);
        nr = htons(nr);

        for(uint16_t i=1;i<=nr;i++)
        {
            if(nr%i==0)
            {
                res = send(c,(char*)&i, sizeof(i),0);
            }
        }
        uint16_t a=0;
        res = send(c,(char*)&a, sizeof(a),0);

        closesocket(c);

    }
}