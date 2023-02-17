import sys
import socket
import struct
import threading

peers=[]
mutex = threading.Lock()

def client_message(client_socket):
    #we read a any message that comes our way from other clients
    while True:
        message,c_address=client_socket.recvfrom(256)
        print(c_address,":",message.decode())

def user_message(peer_socket):
    #we read a message and after that we go through the peers list and send the message through udp to all other clients
    #we need to lock the mutex because the peers list is a global variable available to all threads
    global peers, mutex
    while True:
        message = input()
        if message == 'quit':
            exit(0)
        mutex.acquire()
        for peer in peers:
            peer_socket.sendto(message.encode(),peer)
        mutex.release()


if __name__ == '__main__':
    #creating the server socket and connecting to it
    server_socket = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
    server_socket.connect(("127.0.0.1",1234))

    #getting the clietn port from the arguments
    client_port = int(sys.argv[1])
    peer_socket=socket.socket(socket.AF_INET,socket.SOCK_DGRAM)
    peer_socket.bind(("127.0.0.1",client_port))

    #making 2 threads 1 for when we read and oane for when we receive data from other clients they run at the same time
    peer_thread=threading.Thread(target=client_message, args=(peer_socket,))
    user_thread=threading.Thread(target=user_message, args=(peer_socket,))
    peer_thread.start()
    user_thread.start()
    
    #sending the client port to the server such that it is included in the connections list and will be later shown to all other clients
    server_socket.send(struct.pack("!i", client_port))

    while True:
        #we get the clients list that needs to be shown every time we add a new client
        client_list = server_socket.recv(512).decode()
        print("updated list of connected clients:")
        #because we modify the peers list we get the mutex such that no other thread can read or write to it
        #this ensures that the server doesn't add a new client in the proceess of showing the current client the last list of updated clients
        mutex.acquire()
        peers.clear()
        clients = client_list.split(';')
        for client in clients:
            client_information=client.split(',')
            client_ip=client_information[0].rstrip()
            client_port = int(client_information[1])
            print("client=",client_ip,client_port)
            peers.append((client_ip,client_port))
        mutex.release() 
