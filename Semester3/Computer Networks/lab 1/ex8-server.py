import socket, struct

if __name__ =='__main__':
    try:
        s= socket.create_connection('localhost',1234)
    except socket.error as msg:
        print('Error: ', msg.strerror)
        exit(-1)
    
    