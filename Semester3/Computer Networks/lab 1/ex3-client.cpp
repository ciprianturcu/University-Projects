#pragma comment(lib, "ws2_32.lib")
#include <sys/types.h>
#include <WinSock2.h>
#include <stdio.h>
#include <string.h>
#include <cstdint>
#include <ws2tcpip.h>
#include <iostream>

using namespace std;


int main() {

	struct sockaddr_in server;
    int c;
    char sir[400];
    char reversed[400];

#ifdef _WIN32

    WSADATA wsaData;

    if (WSAStartup(MAKEWORD(2, 2), &wsaData) < 0) {

        printf("Error initializing the Windows Sockets LIbrary");

        return -1;

    }

#endif
    c = socket(AF_INET, SOCK_STREAM, 0);
    if (c < 0)
    {
        perror("Error on client socket creation");
        return 1;
    }

    memset(&server, 0, sizeof(server));

    server.sin_port = htons(1234);
    server.sin_family = AF_INET;
    int retval = inet_pton(AF_INET, "192.168.100.25", &server.sin_addr.s_addr);


    if (connect(c, (struct sockaddr*)&server, sizeof(server)) < 0)
    {
        perror("Error connecting to server");
        return 1;
    }

    printf("enter string to be reversed=");
    
    fgets(sir, 400, stdin);

    send(c, sir, strlen(sir) + 1, 0);

    recv(c, reversed, strlen(sir) + 1, 0);

    printf("Reversed string is  = %s", reversed);

    closesocket(c);

    

}