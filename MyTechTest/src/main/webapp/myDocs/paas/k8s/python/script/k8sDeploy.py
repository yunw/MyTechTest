#!/usr/bin/python
# -*- coding: utf-8 -*-

from dbOps import dbOps
import sys
import threading
import os

class k8sDeploy:
        
    def k8sDeploy(self):
        pass

    def saveDeployData(self):        
        db = dbOps()
        
        stage = getattr(self,'stage')
        appName = getattr(self,'appName')
        appVersion = getattr(self,'appVersion')
        appPath = getattr(self,'appPath')
        accessURL = getattr(self,'accessURL')
        dubboAddress = getattr(self,'dubboAddress')
        debugPort = getattr(self,'debugPort')
        tcpPort = getattr(self,'tcpPort')
        udpPort = getattr(self,'udpPort')
        result = getattr(self,'result')
        
        sql = 'INSERT INTO TB_DEPLOY_K8S (STAGE,APP_NAME,APP_VERSION,APP_PATH,ACCESS_URL,DUBBO_ADDRESS,DEBUG_PORT,TCP_PORT,UDP_PORT,RESULT) VALUES(?,?,?,?,?,?,?,?,?,?)'
        data = [(stage,appName,appVersion,appPath,accessURL,dubboAddress,debugPort,tcpPort,udpPort,result),]
        res = db.save(sql, data)
        if (res == 0):
            return 0
        
