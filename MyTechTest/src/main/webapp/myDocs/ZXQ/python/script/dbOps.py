#!/usr/bin/python
# -*- coding: utf-8 -*-
import sqlite3
import os
import ConfigParser
import traceback

#global var
DB_FILE_PATH = ''

def init():
    cf = ConfigParser.ConfigParser() 
    cf.read("./script/db.properties")
    global DB_FILE_PATH
    DB_FILE_PATH=cf.get("dbParas", "dbFilePath")

def get_conn(path):
    if(os.path.exists(path) and os.path.isfile(path)):
        return sqlite3.connect(path)
    else:
        return sqlite3.connect(':memory:')

def get_cursor(conn):
    if conn is not None:
        return conn.cursor()
    else:
        return get_conn('').cursor()

def close_all(conn, cu):
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

def save(sql, data):
    if sql is not None and sql != '':
        if data is not None:
            for d in data:
                    conn=get_conn(DB_FILE_PATH)
                    cu=get_cursor(conn)
                    try:
                        cu.execute(sql,d)
                        conn.commit()
                    except:
                        conn.rollback()
                        traceback.print_exc()
                        return -1
                    finally:
                        close_all(conn,cu)
    return 0

def fetchFreePort(appName,type):
    sql = 'select port from ports where appName = ? and type = ?'
    data=(appName,type)
    conn = get_conn(DB_FILE_PATH)
    cu=get_cursor(conn)
    cu.execute(sql,data)
    rows=cu.fetchall()

    if (len(rows)>0):
       for row in rows:
           return row[0]

def main():
    init()

if __name__ == '__main__':
    main() 

