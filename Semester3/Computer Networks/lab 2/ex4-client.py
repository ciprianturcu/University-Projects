import struct,socket

#create client socket
s=socket.socket(socket.AF_INET, socket.SOCK_STREAM)
#create server socket

#connect to the server
s.connect(("127.0.0.1",1234))
#read n and n floats
n = input("n=")
s.send(struct.pack("!i",int(n)))
for i in range(int(n)):
    x=input("x=")
    s.send(struct.pack("!f",int(x)))
#receive n and n floats

c = struct.unpack("!i", s.recv(4))[0]
for i in range(int(c)):
    x=struct.unpack("!f",s.recv(4))[0]
    print("{0},".format(x))