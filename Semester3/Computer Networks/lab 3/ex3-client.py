import sys
import socket
import struct
import threading

# data:
# - from the server : TCP (list of other clients) recv() - blocking (main thread)
# - from the clients: UDP (messages) - recvfrom() - blocking T1; peer_socket
# - stdin : read msg from the user - input() - blocking T2

SERVER_PORT = 1234

peers = [] # the ip addresses and ports of the chat participants (send by the server); (ip_addr, port_UDP)
lock = threading.Lock()

def read_client_msg(cs):
    #     reads through the udp port the client messages
    while True:
        msg, addr = cs.recvfrom(256)
        print(addr, ':', msg.decode())


def read_user_msg(ps):
    global peers, lock
    # read msg from the user (keyboard) and send them to all the clients
    while True:
        msg = input()
        lock.acquire()
        for peer in peers: # peer - tuple(ip, port)
            #print(peer)
            ps.sendto(msg.encode(), peer)
        lock.release()

if __name__ == '__main__':

    server_socket = socket.socket(socket.AF_INET, socket.SOCK_STREAM)  #SOCK_STREAM - tcp
    server_socket.connect(('127.0.0.1', SERVER_PORT))

    client_port = int(sys.argv[1])
    peer_socket = socket.socket(socket.AF_INET, socket.SOCK_DGRAM)
    peer_socket.bind(('127.0.0.1', client_port))

    peer_thread = threading.Thread(target=read_client_msg, args=(peer_socket,))
    user_thread = threading.Thread(target=read_user_msg, args=(peer_socket, ))
    peer_thread.start()
    user_thread.start()

    # send the udp port to the server
    server_socket.send(struct.pack('!I', client_port))

    while True:
        peers_list = server_socket.recv(512).decode()
        # ip0, port0; ip1, port1; ip2, port2
        # 127.0.0.1,1230;192.156.2.45,9101
        print('received new peer list: ', peers_list)
        lock.acquire()
        peers.clear()
        clients = peers_list.split(';')
        for client in clients:
            client_info = client.split(',')
            ip = client_info[0].rstrip()
            port = int(client_info[1])
            print('peer: ', ip, port)
            peers.append((ip, port))
        lock.release()
