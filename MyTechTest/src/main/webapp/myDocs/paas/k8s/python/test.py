import sys
from ports import ports

port = ports()
port.getFreePort(sys.argv[1], int(sys.argv[2]) + 0)
print('debug')
port.getFreePort(sys.argv[1], int(sys.argv[2]) + 1)
print('tcp')
port.getFreePort(sys.argv[1], int(sys.argv[2]) + 2)
print('udp')
