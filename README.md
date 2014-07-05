### **移动互联网时代如果还未体验过即时服务,建议尝试下Sissi.**

### 什么是Sissi?
Sissi是由Java编写的XMPP服务器.[Whats XMPP?](http://xmpp.org/)
***
1. 已支持  
RFC-6120,  
RFC-6121,  
RFC-6122,  
XEP-0004 数据表单,  
XEP-0012 Last activity,  
XEP-0030 服务发现,  
XEP-0045 多用户聊天,  
XEP-0054 电子名片,  
XEP-0055 用户搜索,  
XEP-0065 SOCKS字节流,  
XEP-0077 站内注册,  
XEP-0092 Software Version,  
XEP-0096 文件传输,   
XEP-0153 用户头像,   
XEP-0184 消息回执,    
XEP-0191 黑名单,  
XEP-0199 Ping,    

2. 已扩展  
XEP-0045 真正的持久化  
XEP-0065 离线文件传输  

### 如何安装Sissi?
1. 安装[JDK 1.7](http://www.oracle.com/technetwork/cn/java/javase/downloads/jdk7-downloads-1880260.html)及以上版本
2. 安装[MongoDB 2.4.10](http://www.mongodb.org/downloads)及以上版本   
3. 安装[Maven 3.2.1](http://maven.apache.org/)及以上版本
4. git clone https://github.com/KimShen/sissi/  
5. mvn clean package  
6. java -jar sissi-server.jar  



