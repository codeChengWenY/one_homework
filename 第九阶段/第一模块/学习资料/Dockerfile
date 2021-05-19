FROM  mysql:5.7.31
# 作者信息
MAINTAINER   mysql from date UTC by Asia/Shanghai "laosiji@lagou.com"
ENV  TZ Asia/Shanghai

COPY lagou.sql /docker-entrypoint-initdb.d

COPY my.cnf /etc/mysql/
