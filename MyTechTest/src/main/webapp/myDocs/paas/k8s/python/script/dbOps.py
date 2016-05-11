#!/usr/bin/python
# -*- coding: utf-8 -*-
import sqlite3
import os
import ConfigParser
import traceback
import sys

class dbOps:

    def dbOps(self):
        pass

    def getDbFilePath(self):
        cf = ConfigParser.ConfigParser() 
        realpath = os.path.split(os.path.realpath(sys.argv[0]))[0]
        cf.read(realpath + '/script/db.properties')
        cf.sections()
        return cf.get('dbParas', 'dbFilePath')

    def get_conn(self,path):
        if(os.path.exists(path) and os.path.isfile(path)):
            return sqlite3.connect(path)
        else:
            return sqlite3.connect(':memory:')

    def get_cursor(self,conn):
        if conn is not None:
            return conn.cursor()
        else:
            return self.get_conn('').cursor()

    def close_all(self,conn, cu):
        try:
            if cu is not None:
                cu.close()
        finally:
            if cu is not None:
                cu.close()
        try:
            if conn is not None:
                conn.close()
        finally:
            if conn is not None:
                conn.close()

    def save(self,sql, data):
        if sql is not None and sql != '':
            if data is not None:
                for d in data:
                    conn=self.get_conn(self.getDbFilePath())
                    cu=self.get_cursor(conn)
                    try:
                        cu.execute(sql,d)
                        conn.commit()
                    except:
                        conn.rollback()
                        traceback.print_exc()
                        return -1
                    finally:
                        self.close_all(conn,cu)
        return 0

    def fetchFreePort(self,appName,type):
        sql = 'select port from ports where appName = ? and type = ?'
        data=(appName,type)
        conn = self.get_conn(self.getDbFilePath())
        cu= self.get_cursor(conn)
        cu.execute(sql,data)
        rows=cu.fetchall()

        if (len(rows)>0):
           for row in rows:
               return row[0]
        self.close_all(conn,cu)
