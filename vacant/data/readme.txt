导出数据库
mysqldump -h192.168.0.7 -uroot -pmysqlesun -R minzheng2>D:/minzheng2.bak.sql


roroot/*UR&PS2baicheng*

白城:
	WEB服务器(122.138.197.2):
		tomcat目录=/usr/local/apache-tomcat-6.0.36, 程序安装位置=webapps/ROOT, 数据库配置信息:192.168.8.2,minzheng,minzheng
	数据库服务器(192.168.8.2):
		mysql目录=/usr/local/mysql, 常用脚本目录(minzheng.clear.sql):/root/minzheng, 
		
常用命令:
数据库还原命令:/usr/local/mysql/bin/mysql -h192.168.8.2 -uminzheng -pminzheng minzheng</root/minzheng/minzheng.bak.sql
复制文件至数据库服务器:scp /root/minzheng/minzheng.bak.sql root@192.168.8.2:/root/minzheng
删除指定文件夹下所有文件:rm -rf /usr/local/apache-tomcat-6.0.36/webapps/ROOT/*
移动文件至指定目录:mv /root/minzheng/webapp.zip /usr/local/apache-tomcat-6.0.36/webapps/ROOT/
解压zip文件: unzip webapp.zip
修改jdbc配置文件