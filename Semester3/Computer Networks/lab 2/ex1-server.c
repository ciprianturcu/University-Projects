// #define _WINSOCK_DEPRECATED_NO_WARNINGS 1

// // exists on all platforms

// #include <stdio.h>

 

// // this section will only be compiled on NON Windows platforms

// #ifndef _WIN32

// #include <sys/types.h>

// #include <sys/socket.h>

// #include <netinet/in.h>

// #include <netinet/ip.h>

// #include <string.h>

// #include <arpa/inet.h>

// #define max 100

// #include <stdlib.h>

 

 

// #include <unistd.h>

// #include <errno.h>
// #include <signal.h>

 

// #define closesocket close

// typedef int SOCKET;

// #else

// // on Windows include and link these things

// #include<WinSock2.h>

// // for uint16_t an so

// #include<cstdint>

 

// // this is how we can link a library directly from the source code with the VC++ compiler – otherwise got o project settings and link to it explicitly

// //#pragma comment(lib,"Ws2_32.lib")

// #endif

// int c;


// void time_out(__attribute__((unused)) int semnal) {
// int32_t r = -1;
// r = htonl(r);
// printf("Time out.\n");
// send(c, &r, sizeof(int32_t), 0);
// close(c); // desi nu am primit nimic de la client in 10 secunde, inchidem civilizat conexiunea cu acesta
// exit(1);
// }


// void computation()
// {
//     //first we check the client filedescriptor
//     if(c<0)
//     {
//         perror("Error connecting to client");
//         exit(1);
//     }

//     char command[256];
//     int pos=0;
//     uint8_t b;
//     int r;
//     int cod;

//     signal(SIGALRM,time_out);
//     alarm(10);

//     r=0;//number of chars received from the client;
//     do
//     {
//         cod = recv(c,&b,1,0);
//         if(cod==1)
//             alarm(10);
//         if(cod!=1)
//         {   
//             r=-1;
//             break;
//         }
//         command[pos++]=b;
//     } while (b!=0);
//     alarm(0);
//     //we use a file descriptor to execute the command and get the content
//     //popen command run
//     FILE *fp = popen(command,"r");
//     while(1)
//     {
//         char q;
//         fscanf(fp, "%c", &q);
//         if(feof(fp)) //if end of file is reached we transmit a zero
//         {
//             q=0;
//             send(c,&q,1,0);
//             break;
//         }
//         else send(c,&q,1,0);
//     }
//     int exit_code = htonl(WEXITSTATUS(fclose(fp)));
//     send(c,&exit_code,4,0);
//     close(c);
//     if(exit_code>=0)
//     {
//         printf("Am inchis cu succes conexiunea cu un client.\n");
//     }
// 	else {
// 		printf("Am inchis cu eroare conexiunea cu un client. Cod de eroare %d.\n", r);
// 	exit(1);
//     }
//     exit(0);
    
// }

 

// int main() {

//     int s, cod;
// 	unsigned l;
// 	struct sockaddr_in client, server;  

// 	s = socket(PF_INET, SOCK_STREAM, 0);
// 	if (s < 0) {
// 		fprintf(stderr, "Eroare la creare socket server.\n");
// 		return 1;
// 	}

// 	memset(&server, 0, sizeof(struct sockaddr_in));
// 	server.sin_family = AF_INET;
// 	server.sin_port = htons(4321);
// 	server.sin_addr.s_addr = INADDR_ANY;

// 	cod = bind(s, (struct sockaddr *) &server, sizeof(struct sockaddr_in));
// 	if (cod < 0) {
// 		fprintf(stderr, "Eroare la bind. Portul este deja folosit.\n");
// 		return 1;
// 	}

// 	listen(s, 5);

// 	while (1) { // deserveste oricati clienti

// 		memset(&client, 0, sizeof(client));
// 		l = sizeof(client);

// 		printf("Astept sa se conecteze un client.\n");
// 		c = accept(s, (struct sockaddr *) &client, &l);
// 		printf("S-a conectat clientul cu adresa %s si portul %d.\n", inet_ntoa(client.sin_addr), ntohs(client.sin_port));

// 		if (fork() == 0) { // server concurent, conexiunea va fi tratata de catre un proces fiu separat
// 			computation();
// 		}
//     }
// }