//#pragma comment(lib, "ws2_32.lib")
//#include <sys/types.h>
//#include <WinSock2.h>
//#include <stdio.h>
//#include <string.h>
//#include <cstdint>
//#include <ws2tcpip.h>
//#include <iostream>
//
//using namespace std;
//
//
//int main() {
//
//    int c;
//
//    struct sockaddr_in server;
//
//    uint16_t n, x, suma;
//
//#ifdef _WIN32
//
//    WSADATA wsaData;
//
//    if (WSAStartup(MAKEWORD(2, 2), &wsaData) < 0) {
//
//        printf("Error initializing the Windows Sockets LIbrary");
//
//        return -1;
//
//    }
//
//#endif
//
//    c = socket(AF_INET, SOCK_STREAM, 0);
//
//    if (c < 0) {
//
//        printf("Eroare la crearea socketului client\n");
//
//        return 1;
//
//    }
//
//
//
//    memset(&server, 0, sizeof(server));
//
//    server.sin_port = htons(1234);
//
//    server.sin_family = AF_INET;
//
//    int retval = inet_pton(AF_INET, "192.168.100.25", &server.sin_addr.s_addr);
//
//    if (connect(c, (struct sockaddr*)&server, sizeof(server)) < 0) {
//
//        printf("Eroare la conectarea la server\n");
//
//        return 1;
//
//    }
//
//
//
//    printf("n = ");
//
//    scanf_s("%hu", &n);
//
//    n = htons(n);
//
//    send(c, (char*)&n, sizeof(n), 0);
//
//    n = htons(n);
//
//    for (uint16_t i = 1; i <= n; i++)
//    {
//        printf("x=");
//        scanf_s("%hu", &x);
//        x = htons(x);
//        send(c, (char*)&x, sizeof(x), 0);
//    }
//
//    recv(c, (char*)&suma, sizeof(suma), 0);
//
//    suma = ntohs(suma);
//
//    printf("Suma este %hu\n", suma);
//
//    closesocket(c);
//
//}