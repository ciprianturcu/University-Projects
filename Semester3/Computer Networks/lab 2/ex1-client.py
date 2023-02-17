import socket,struct

#create socket
s=socket.socket(socket.AF_INET,socket.SOCK_STREAM)

#reading command
cmd = input("command:")

#connecting to server tuple is needed
s.connect(("127.0.0.1",4321))
#sending command character one by one
for x in cmd:
    s.send(struct.pack("c",x.encode("ascii")))
#sending null char to end the message
s.send(struct.pack("c",'\0'.encode("ascii")))

while True:
    c = s.recv(1)
    c = c.decode("ascii")
    if(c=='\0'):
        break
    print(c,end="")

c=s.recv(4)
c=struct.unpack("!i",c)
print("Exitcode is {0}".format(c[0].__format__('d')))

s.close()


