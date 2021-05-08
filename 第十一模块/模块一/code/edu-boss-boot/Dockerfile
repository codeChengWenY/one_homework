FROM openjdk:8-alpine3.9
# 作者信息
MAINTAINER laosiji Docker springboot "laosiji@lagou.com"

RUN echo "http://mirrors.aliyun.com/alpine/latest-stable/main/" > /etc/apk/repositories && echo "http://mirrors.aliyun.com/alpine/latest-stable/community/" >> /etc/apk/repositories

RUN apk --update add curl bash tzdata && rm -rf /var/cache/apk/*

ENV TZ Asia/Shanghai
COPY edu-boss-boot.jar app.jar
EXPOSE 8761

ENTRYPOINT ["java","-jar","/app.jar"]