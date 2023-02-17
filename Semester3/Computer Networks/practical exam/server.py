import struct
import socket
import select


def send_client_list(client_list):
    data=''
    #making a string with all the data to be sent to the clients

    for client in client_list.values():
        data += client[0]+','+str(client[1])+';'
    data = data[:-1] #deleting the last ;
    for client in client_list:
        client.send(data.encode())
    

if __name__ == '__main__':
    #we save in the dict the tcp socket to sending the clients list from the server to all the clients
    # we save the udp_port - udp communication happens here, ip address - communication)
    client_list=dict()
    s=socket.socket(socket.AF_INET, socket.SOCK_STREAM)
    s.bind(("127.0.0.1",1234))
    s.listen(7)

    reading_sock=[s]
    while True:
        #maintaining a list of file descriptors that need to be woken up because of an action i/o
        ready_to_read, _, _=select.select(reading_sock,[],[])
        for x in ready_to_read:
            if x is s:
                # fd, addr = accept()
                client_sock, c_address = x.accept()
                print("new connection from :", c_address)
                #we get the client udp port
                c_udp_port = client_sock.recv(4)
                #decode it
                c_udp_port = struct.unpack("!i",c_udp_port)[0]
                #put it in the clients_list 
                client_list[client_sock]=(c_address[0],c_udp_port)
                #sending to all clients the refreshed list
                send_client_list(client_list)
                reading_sock.append(client_sock)
            else:
                data = x.recv(512)
                if not data or 'quit' == data.decode().lower():
                    x.close()
                    client_list.pop(x)
                    reading_sock.remove(x)
                    send_client_list(client_list)