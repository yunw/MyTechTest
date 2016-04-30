import ConfigParser
import traceback
import socket
import dbOps
import sys
import threading

class ports:


    def ports(self):
        pass

    def getFreePort(self,appName,porttype):
        host=['0.0.0.0','127.0.0.1', '10.25.23.165']
        
        cf = ConfigParser.ConfigParser() 
        cf.read("./script/ports.properties")
        cf.sections()
        minPort = cf.get("portRange", "minPort")
        maxPort = cf.get("portRange", "maxPort")
        lock = threading.Lock()
        dbOps.init()
        p = dbOps.fetchFreePort(appName,porttype)
        if p is not None:
            return p
        if lock.acquire():
            for port in range(int(minPort), int(maxPort)):
                cnt = 0
                for h in host:
                    try:
                        s=socket.socket(socket.AF_INET,socket.SOCK_STREAM)
                        s.bind((h,port))
                        s.close()
                        cnt= cnt+1
                    except:
                        break
                if (cnt == 3):
                    whitePorts=cf.get('whitePorts', 'ports')
                    wps = whitePorts.split(',')
                    for p in wps:
                        if (int(p) == port):
                            continue
                        else:
                            sql='INSERT INTO ports values(?,?,?)'
                            data=[(port,appName,porttype),]
                            res = dbOps.save(sql, data)
                            if (res == 0):
                                return port
            lock.release()
