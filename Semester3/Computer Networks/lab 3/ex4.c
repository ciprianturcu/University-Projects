#include <stdio.h>
#include <unistd.h>
#include <sys/types.h>
#include <sys/socket.h>
#include <netinet/in.h>
#include <netdb.h>
#include <arpa/inet.h>
#include <string.h>
#include <time.h>
#include <string.h>



#define MAX 100
#define RECEIVING_BUFFER_LEN 100

/// @brief 
/// @param argc 
/// @param argv 
/// @return 
int main(int argc ,char **argv)
{
    /*
    we check that the program was called correctly there should be 2 args
    name of the exec and the network broadcast address <NBCAST>  
    */
    if(argc!=2)
    {
        perror("Invalid arguments");
        return 1;
    }

    //we create 2 sockets one for the receiever and one for the sender

    struct sockaddr_in receiver_addr,sender_addr;

    // we set the receiver address on the given broadcast address and on the port 7777

    receiver_addr.sin_family = AF_INET;
    receiver_addr.sin_port = htons(7777);
    receiver_addr.sin_addr.s_addr = inet_addr(argv[1]);

    unsigned int len = sizeof(struct sockaddr_in);

    if(fork()==0)
    {
        // we use SOCK_DGRAM type to create socket, send short messages of fixed maximum length
        int child_sock = socket(AF_INET, SOCK_DGRAM, 0);
        if(child_sock < 0)
        {
            perror("Error on creating child socket: ");
            close(child_sock);
            return 1;   
        }
        //setting up the socket for broadcasting
        int broadcast=1;
        if(setsockopt(child_sock,SOL_SOCKET, SO_BROADCAST, &broadcast, sizeof(broadcast))<0)
        {
            perror("Error setting the broadcast option");
            close(child_sock);
            return 1;
        }
        //setting the timeout period and changing the socket option
        struct timeval timeout_of_read;
        timeout_of_read.tv_sec=1;
        timeout_of_read.tv_usec=0;
        setsockopt(child_sock, SOL_SOCKET, SO_RCVTIMEO, &timeout_of_read, sizeof(timeout_of_read));

        // now in order to be able to send a braodcast every 3 and 10 seconds we need an infinite loop
        
        int failed_recv_count = 0;
        int last=-1;
        while (1)
        {
            
            char recv_message[MAX];
            //we check if the sender gave a response to the request
            int result = recvfrom(child_sock, recv_message, 100, 0, (struct sockaddr*)&sender_addr, &len);
            if(result<0) // recvfrom failed
            {
                failed_recv_count++;
            }
            else // we got some message back
            {
                printf("%s\n\n" ,recv_message);
            }
            if(failed_recv_count == last)
            {
                continue;
            }
            last = failed_recv_count;
            if(failed_recv_count%3==0)
            {
                char* msg = "TIMEQUERY";
                sendto(child_sock, msg, strlen(msg)+1, 0, (struct sockaddr*)&receiver_addr, &len);

            }
            if(failed_recv_count%10==0)
            {
                char *msg="DATEQUERY";
                sendto(child_sock, msg, strlen(msg)+1, 0, (struct sockaddr*)&receiver_addr, &len);

            }
        }
        exit(0);
        return 0;
    }

    int sock = socket(AF_INET, SOCK_DGRAM,0);
    if(sock<0)
    {
        perror("Error in creating socket.");
        return 1;
    }
    if(bind(sock,(struct sockaddr*)&receiver_addr, sizeof(receiver_addr))<0)
    {
        perror("Error at binding");
        close(sock);
        return 1;
    }
    //parent gets the requests and  processes the time and dates
    while(1)
    {
        char receiving_buffer[RECEIVING_BUFFER_LEN];
        if(recvfrom(sock, receiving_buffer, RECEIVING_BUFFER_LEN, 0, (struct sockaddr*)&sender_addr, &len)<0)
            perror("Error in receiving \n");
        //getting the actual time off of the machine
        time_t t = time(NULL);
        struct tm current_time = *localtime(&t);
        if(strcmp(receiving_buffer, "TIMEQUERY")==0)
        {
            printf("received a TIMEQUERRY request\n");
            char time[MAX];
            sprintf(time, "TIME %02d:%02d:%02d", current_time.tm_hour, current_time.tm_min, current_time.tm_sec);
            //printf(time);
            sendto(sock, time, strlen(time)+1, 0, (struct sockaddr*)&sender_addr, (socklen_t)len);

        }
        else if(strcmp(receiving_buffer, "DATEQUERY")==0)
        {
            printf("received a DATEQUERRY request\n");
            char time[MAX];
            sprintf(time, "DATE %02d:%02d:%04d",current_time.tm_mday , current_time.tm_mon+1, current_time.tm_year+1900);
            //printf(time);
            sendto(sock, time, strlen(time)+1, 0, (struct sockaddr*)&sender_addr, (socklen_t)len);
        }
        else printf("Rubbish - %s", receiving_buffer);
    }
    close(sock);
    return 0;


}
