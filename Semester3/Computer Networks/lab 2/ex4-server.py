import socket,struct
from threading import Lock,Thread


#creating server socket
rs = socket.create_server(('127.0.0.1',1234),family=socket.AF_INET, backlog=10,reuse_port=True)

print("Server started.")

mutex=Lock()
over=False

sockets=[]
elems=[]
threads=[]

def worker(client_sock,client_addr,element):
    global over
    temp = client_sock.recv(4)
    n=struct.unpack("!i",temp)[0]
    print("Received {0} from {1}".format(n, client_addr))
    if n==0:
        mutex.acquire()
        over=True
        mutex.release()
        return
    array=[]
    mutex.acquire()
    for i in range(n):
        temp = client_sock.recv(4)
        x=struct.unpack("!f",temp)[0]
        print("Received {0} from {1}".format(n, client_addr))
        array.append(x)
    elems+=array
    mutex.release()



while not over:
    client_socket,client_address = rs.accept()
    sockets.append(client_socket)
    t=Thread(target=worker,args=(client_socket,client_address,elems))
    threads.append(t)
    t.start()

for t in threads:
    t.join()

elems = sorted(elems)

for cs in sockets:
    cs.send(struct.pack("!i",len(elems)))
    for x in elems:
        cs.send(struct.pack("!f",x))
    cs.close()