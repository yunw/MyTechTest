# prepare for this build. 
# ****************************************************************************************
import os
import subprocess
import sys
import commands  
import pexpect 
import time 
import sqlite3
from script.ports import *
from script.k8sDeploy import *
import time

rootDir = "/paas/k8s/python/"
appDir = "apps/"
warsDir = "/paas/k8s/python/wars/"
registry = "10.25.23.165:5000" 


appName = sys.argv[1]
appVersion = sys.argv[2]+"-"+str(long(time.time()))
warFileName = sys.argv[3]
workingDirName = appName + "-" + appVersion + "/"

def init():  
    os.system("mkdir " + rootDir + appDir + workingDirName)

# build a war file from Git.
def buildImage():
    geneWarFile = warsDir + warFileName;
    # make a docker image. 
    os.system("cp " + geneWarFile + " " + rootDir + appDir + workingDirName + warFileName);
    fin = open(rootDir + "template/Dockerfile.in", "r")
    fout = open(rootDir + appDir + workingDirName+"Dockerfile", "w")
    for s in fin:
        fout.write(s.replace("${WAR_FILE_NAME}", warFileName))
    print "Dockerfile generated."    
    fout.close()
    fin.close()
    os.system("docker build -t " + appName+":" + appVersion + " " + rootDir + appDir + workingDirName)

# push the docker image into private registry. 
def pushImage():
    p=subprocess.Popen("docker images|grep " + appName + " |grep " +appVersion ,shell=True,stdout=subprocess.PIPE, stdin=subprocess.PIPE, stderr=subprocess.PIPE) 
    p.wait()
    imageID = p.stdout.readline().split()[2]
    os.system("docker tag -f " + imageID + " " + registry + "/" + appName + ":" + appVersion);
    os.system("docker push -f " + registry + "/" + appName + ":" + appVersion)


# deploy the image to the kubernetes cluster. 
def deploy():
    port = ports()
    debugPort = str(port.getFreePort(appName,1))
    tcpPort = str(port.getFreePort(appName,2))
    udpPort = str(port.getFreePort(appName,3))
    fin = open(rootDir + "template/war-rc.yaml.in", "r")
    fout = open(rootDir + appDir + workingDirName + "/war-rc.yaml", "w")
    for s in fin:
        s1 = s.replace("${WAR_FILE_NAME}", warFileName).replace("${APP_NAME}",appName).replace("${APP_VERSION}",appVersion)
        s2 = s1.replace("${DEBUG_PORT}", debugPort).replace("${TCP_PORT}",tcpPort).replace("${UDP_PORT}",udpPort)         
        fout.write(s2)
    print "war-rc.yaml generated."      
    fout.close()
    fin.close()
    os.system("kubectl delete rc " + appName + "-rc")
    os.system("kubectl create -f " + rootDir + appDir + workingDirName + "/war-rc.yaml")
    fin = open(rootDir + "template/war-svc.yaml.in", "r")
    fout = open(rootDir + appDir + workingDirName + "/war-svc.yaml", "w")
    for s in fin:
        fout.write(s.replace("${WAR_FILE_NAME}", warFileName).replace("${APP_NAME}",appName).replace("${APP_VERSION}",appVersion))
    print "war-svc.yaml generated."      
    fout.close()
    fin.close()
    os.system("kubectl delete service " + appName + "-svc")
    os.system("kubectl create -f " + rootDir + appDir + workingDirName + "/war-svc.yaml")
    return (debugPort,tcpPort,udpPort)

# create nginx upstream and location. 
def createLB():
    fin = open(rootDir + "nginx/location.conf.in", "r")
    fout = open(rootDir + appDir + workingDirName + "/location-" + appName + ".conf", "w")
    for s in fin:
        fout.write(s.replace("${WAR_FILE_NAME}", warFileName).replace("${APP_NAME}",appName).replace("${APP_VERSION}",appVersion))
    print "location.conf generated."      
    fout.close()
    fin.close()
    fin = open(rootDir + "nginx/upstream.conf.in", "r")
    fout = open(rootDir + appDir + workingDirName + "/upstream-" + appName + ".conf", "w")
    for s in fin:
        fout.write(s.replace("${WAR_FILE_NAME}", warFileName).replace("${APP_NAME}",appName).replace("${APP_VERSION}",appVersion))
    print "upstream.conf generated."      
    fout.close()
    fin.close()

# copy location.conf and upstream.conf to nginx server(10.25.23.166).
def applyLB():
    ip = "10.25.23.166"
    need_pwd= 'root@'+ip+"'s password: " 
    cmd1 = "scp " + rootDir + appDir + workingDirName + "location-" + appName + ".conf" + " root@" + ip + ":/etc/nginx/conf.d/vhosts/locations/location-" + appName + ".conf"
    cmd2 = "scp " + rootDir + appDir + workingDirName + "upstream-" + appName + ".conf" + " root@" + ip + ":/etc/nginx/conf.d/vhosts/upstreams/upstream-" + appName + ".conf"
    print cmd1
    print cmd2
    cmds = (cmd1,cmd2)
    pwd = "Pass1234"
    for cmd in cmds:
        child = pexpect.spawn(cmd)  
        i = child.expect([pexpect.TIMEOUT, need_pwd])          
        if i == 1:                 
            child.sendline(pwd)  
            print child.before, child.after  
        else:  
            print child.before, child.after  
            child.sendline ('yes')                        
            i = child.expect([pexpect.TIMEOUT, need_pwd])  
            if i == 1:  
                child.expect(need_pwd)  
                child.sendline(pwd)  
            else:  
                print 'error:'  
                print child.before,child.after  
                break  
        time.sleep(3)            
    p=subprocess.Popen("kubectl get pods |grep nginxplus" ,shell=True,stdout=subprocess.PIPE, stdin=subprocess.PIPE, stderr=subprocess.PIPE) 
    p.wait()
    nginxPodName = p.stdout.readline().split()[0]        
    os.system("kubectl delete pod " + nginxPodName)

def save(exposedPorts,result):
    d = k8sDeploy()
    setattr(d, "stage", "0")
    setattr(d, "appName", appName)
    setattr(d, "appVersion", appVersion)
    setattr(d, "appPath", warsDir + warFileName)
    setattr(d, "accessURL", "http://10.25.23.166/" + appName)
    setattr(d, "dubboAddress", "")
    setattr(d, "debugPort", exposedPorts[0])
    setattr(d, "tcpPort", exposedPorts[1])
    setattr(d, "udpPort", exposedPorts[2])
    setattr(d, "result", result)
    d.saveDeployData()
    
def main():
    result = "success"
    try:
        init()
        buildImage()
        pushImage()
        exposedPorts = deploy()
        createLB()
        applyLB()        
    except Exception,ex:	
        print ex
        result = ex;
    save(exposedPorts,result)
        
if __name__ == '__main__':
    main()    
        
