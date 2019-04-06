#基础镜像
FROM mysql:8.0.13

#作者
MAINTAINER lmTest <lmTest@gmail.com>

#设置 root 用户密码
ENV MYSQL_ROOT_PASSWORD mysql

#移除 The MySQL  Client configuration file
RUN		rm	/etc/mysql/conf.d/mysql.cnf

#复制新 The MySQL  Client configuration file
COPY 	./mysql.cnf		/etc/mysql/conf.d/

#移除 The MySQL  Server configuration file
RUN     rm /etc/mysql/my.cnf

#复制新 The MySQL  Server configuration file
COPY    ./my.cnf    /etc/mysql/

#复制初始化 sql 文件
COPY 	./initdb.sql	/docker-entrypoint-initdb.d/

#设置时区
#NV TZ=Asia/Shanghai
#RUN ln -snf /usr/share/zoneinfo/$TZ /etc/localtime && echo $TZ > /etc/timezone





#创建一个数据库
#ENV MYSQL_DATABASE company
