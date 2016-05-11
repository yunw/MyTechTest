
-- Deployment records
CREATE TABLE TB_DEPLOY_K8S(
    ID INTEGER PRIMARY KEY AUTOINCREMENT,
    STAGE INTEGER, -- 0:DEV , 1:FT , 2:PF
    APP_NAME VARCHAR(50),
    APP_VERSION VARCHAR(50),
    APP_PATH VARCHAR(100),
    ACCESS_URL VARCHAR(200),
    DUBBO_ADDRESS VARCHAR(200),
    DEBUG_PORT INTEGER,
    TCP_PORT INTEGER,
    UDP_PORT ITNEGER,
    RESULT TEXT
);

CREATE TABLE ports(
    port    INT     PRIMARY KEY     NOT NULL,
    appName TEXT    NOT NULL,
    type    INT     NOT NULL -- 1:debug 2:tcp 3:udp
);
CREATE UNIQUE INDEX idx_ports_appname_type on ports (appName, type);
    
