概念：
keystore：密钥库
存储着两类项目：
1、密钥项——以一种受保护的格式储存以防止未授权的访问。通常，储存在这类项中的密钥是机密密钥，或是伴有用于认证相应公钥用的证书“链”的私钥。
   keytool 和 jarsigner 工具只处理后一类型的项，即私钥及其关联的证书链。
2、可信任的证书项——每项包含一个属于另一团体的公钥证书。它之所以叫做“可信任的证书”，是因为密钥库的拥有者相信证书中的公钥确实属于证书“主体”（拥有者）识别的身份。
      证书签发人通过对证书签名来保证这点。

alial：密钥库别名
对所有的密钥仓库项（密钥项和可信任的证书项）的访问都要通过唯一的别名来进行。别名不区分大小写，即别名Tomcat和tomcat指的是同一密钥仓库项
当用 -genkey 命令来生成密钥对（公钥和私钥）或用 -import 命令来将证书或证书链加到可信任证书的清单中，以增加一个实体到密钥仓库中，必须指定了一个别名。
后续 keytool 命令必须使用这一相同的别名来引用该实体。

storepass：密钥库口令，保护密钥库不被非法访问。任何对密钥库的访问都必须提供该口令。

#当客户端连接到服务端的时候，首先会获取服务端的数字证书，然后判断这个证书是否可信，如果是，则交换信道加密秘钥，进行通信，否则连接失败
#服务端证书的CN（common name）被设定为test.com那么客户端在连接服务端的时候，也要用这个域名（可在hosts文件中配置）来连接，
#否则根据SSL协议标准，域名与证书的CN不匹配，说明这个证书是不安全的，通信将无法正常运行。 

#生成服务端数字证书
#将服务端数字证书：sslServer保存在证书仓库：d:/ssl/server_ks中
keytool -genkey -v -alias sslServer -keyalg RSA -keystore d:/ssl/server_ks -dname "CN=test.com,OU=cn,O=cn,L=cn,ST=cn,C=cn" -storepass server -keypass 123123 -validity 365
-genkey：产生密钥对（公钥和与之关联的私钥）
-v：选项可出现在除 -help 之外的所有命令中。如果出现该选项，表示处在“长格式”模式下；将输出详细的证书信息
-alias：密钥库别名
-keyalg：指定了用于生成密钥对的算法（如：RSA DSA，默认为DSA）
-keystore：指定密钥库的位置和名称
-dname：指定证书拥有者信息；CN=名字与姓氏,OU=组织单位名称,O=组织名称,L=城市或区域名称,ST=州或省份名称,C=单位的两字母国家代码
-storepass：指定密钥库的密码(获取keystore信息所需的密码)
-keypass：指定别名条目的密码(私钥的密码)
-validity：指定创建的证书有效期多少天

#由于服务端的证书是我们自己生成的，没有任何受信任机构的签名，所以客户端是无法验证服务端证书的有效性的，通信必然会失败。
#所以我们需要为客户端创建一个保存所有信任证书的仓库，然后把服务端证书导进这个仓库。这样，当客户端连接服务端时，
#会发现服务端的证书在自己的信任列表中，就可以正常通信了。 

#为客户端创建一个保存所有信任证书的仓库：d:/ssl/client_ks
#因keytool不能只生成一个空白证书仓库，所以也生成了一个客户端数字证书：sslClient
keytool -genkey -v -alias sslClient -keyalg RSA -keystore d:/ssl/client_ks -dname "CN=test.com,OU=cn,O=cn,L=cn,ST=cn,C=cn" -storepass client -keypass 456456

#导出服务端数字证书：
keytool -export -alias sslServer -keystore d:/ssl/server_ks -file d:/ssl/server_key.cer

#将服务端数字证书导入客户端仓库：
keytool -import -trustcacerts -alias sslServer -file d:/ssl/server_key.cer -keystore d:/ssl/client_ks

#服务端也要对客户端进行认证，以实现双向握手。但是，同样的道理，现在服务端并没有信任客户端的证书，因为客户端的证书也是自己生成的。
#所以，对于服务端，需要做同样的工作：把客户端的证书导出来，并导入到服务端的证书仓库

#导出客户端数字证书：
keytool -export -alias sslClient -keystore d:/ssl/client_ks -file d:/ssl/client_key.cer

#将客户端数字证书导入服务端仓库：
keytool -import -trustcacerts -alias sslClient -file d:/ssl/client_key.cer -keystore d:/ssl/server_ks

参考：http://www.iteye.com/topic/1125183
Secure Sockets Layer:
http://www.pierobon.org/ssl/outline.htm

openssl：
openssl req -new -x509 -keyout D:/ssl/kafka/ca-key -out D:/ssl/kafka/ca-cert -days 365 -passout pass:Pass1234 -subj /C=CN/ST=sh/O=GeoAuth/CN=Self
req          产生证书签发申请命令
-new         表示新请求
-x509        产生自签名的证书
-keyout      指明创建的新的私钥文件
-out         指明生成的CA证书的文件
-days        指定我们自己的CA给其他人签证书的有效期.缺省是30天
-passout     输出文件口令保护存放方式。
                         格式如下：pass:Password evn:var file:pathname fd:number stdin 
                         具体参考：https://www.openssl.org/docs/manmaster/apps/openssl.html
-subj        证书的识别名信息字段（Distinguished Name fields），简称为DN字段

openssl x509 -req -CA D:/ssl/kafka/ca-cert -CAkey D:/ssl/kafka/ca-key -in D:/ssl/kafka/cert-file -out cert-signed -days 365 -CAcreateserial -passin pass:Pass1234
x509            作用很丰富，上述命令中，该选项用来处理CSR和给证书签名，就象一个CA 
-req            缺省的认为输入文件是证书文件，设置了这个选项说明输入文件是CSR
-CA             指定签名用的CA证书文件名。
-CAkey          指定CA私钥文件。如果这个option没有参数输入，那么缺省认为私有密钥在CA证书文件里有。
-in             指定输入文件（本例为证书请求文件CSR：Cerificate Signing Request）
-out            指定输出文件（本例为签名证书）
-days           指定证书的有效时间
-CAcreateserial 如果没有CA系列号文件，那么本选项将生成一个。
-passin

#生成证书请求文件
keytool -keystore D:/ssl/kafka/37/kafka.server.keystore.jks -alias SSSVL0028.smc.saicmotor.com -certreq -file D:/ssl/kafka/cert-file

#显示证书仓库中的所有条目
keytool -list -keystore kafka.client.keystore.jks -storepass Pass1234
