import struct
import socket
import select


def send_clients(clients):
    send_data = ''  
    for info in clients.values():
        send_data += info[0]+','+str(info[1])+';'
    send_data = send_data[:-1]
    for client in clients:
        client.send(send_data.encode())


if __name__ == '__main__':
    clients = dict() # key: tcp socket - used to send the list of clients ,
                    # values: (udp_port - udp communication happens here, ip address - communication)
    rdv = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
    rdv.bind(('127.0.0.1', 1234))
    rdv.listen(7)

    read_sock = [rdv]
    while True:
        ready_read, _, _ = select.select(read_sock, [], [])
        for s in ready_read:
            if s is rdv:
                # we have a new client connection
                cs, addr = s.accept()
                print('new client from addr: ', addr)
                c_udpp = cs.recv(4)
                c_udpp = struct.unpack('!I', c_udpp)[0]
                clients[cs] = (addr[0], c_udpp)
                send_clients(clients)
                read_sock.append(cs)
            else:
                data = s.recv(512)
                if not data or 'quit' in data.decode().lower():
                    s.close()
                    clients.pop(s)
                    read_sock.remove(s)
                    send_clients(clients)
