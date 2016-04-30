import ConfigParser
import traceback
import socket
from dbOps import dbOps
import sys
import threading
import os

class ports:


    def ports(self):
        pass

    def getFreePort(self,appName,porttype):
        host=['0.0.0.0','127.0.0.1', '10.25.23.165']
        
        cf = ConfigParser.ConfigParser() 
        realpath = os.path.split( os.path.realpath( sys.argv[0] ) )[0] 
        cf.read(realpath + '/script/ports.properties')
        cf.sections()
        minPort = cf.get("portRange", "minPort")
        maxPort = cf.get("portRange", "maxPort")
        lock = threading.Lock()
        db = dbOps()
        dbPath =  db.getDbFilePath()
        p = db.fetchFreePort(appName,porttype)
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
                            res = db.save(sql, data)
                            if (res == 0):
                                return port
            lock.release()
