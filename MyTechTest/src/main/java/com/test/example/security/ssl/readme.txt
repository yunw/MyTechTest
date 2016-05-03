#当客户端连接到服务端的时候，首先会获取服务端的数字证书，然后判断这个证书是否可信，如果是，则交换信道加密秘钥，进行通信，否则连接失败
#服务端证书的CN（common name）被设定为test.com那么客户端在连接服务端的时候，也要用这个域名（可在hosts文件中配置）来连接，
#否则根据SSL协议标准，域名与证书的CN不匹配，说明这个证书是不安全的，通信将无法正常运行。 

#生成服务端数字证书
#将服务端数字证书：sslServer保存在证书仓库：d:/ssl/server_ks中
keytool -genkey -v -alias sslServer -keyalg RSA -keystore d:/ssl/server_ks -dname "CN=test.com,OU=cn,O=cn,L=cn,ST=cn,C=cn" -storepass server -keypass 123123

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