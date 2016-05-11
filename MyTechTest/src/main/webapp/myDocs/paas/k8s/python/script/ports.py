import ConfigParser
import traceback
import socket
from dbOps import dbOps
import sys
import threading
import os
import subprocess
import re

class ports:


    def ports(self):
        pass

    def find_all_ip(self):
        ipstr = '([0-9]{1,3}\.){3}[0-9]{1,3}'
        ipconfig_process = subprocess.Popen("ifconfig", stdout=subprocess.PIPE)
        output = ipconfig_process.stdout.read()
        ip_pattern = re.compile('(inet %s)' % ipstr)
        broad_pattern = re.compile('(broadcast %s)' % ipstr)
        pattern = re.compile(ipstr)
        iplist = []
        for ipaddr in re.finditer(ip_pattern, str(output)):
            ip = pattern.search(ipaddr.group())
            iplist.append(ip.group())
        for broadaddr in broad_pattern.finditer(str(output)):
            broad = pattern.search(broadaddr.group())
            iplist.append(broad.group())
        return iplist

    def getFreePort(self,appName,porttype):
        #host=['0.0.0.0','127.0.0.1', '10.25.23.165']
        host = self.find_all_ip()
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
