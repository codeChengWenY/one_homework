​                                                                                  Docker学习笔记

当人们说“Docker”时，他们通常是指 Docker Engine，它是一个客户端 - 服务器应用程序，由 Docker守护进程、一个REST API指定与守护进程交互的接口、和一个命令行接口（CLI）与守护进程通信（通过封装REST API）。Docker Engine 从 CLI 中接受docker 命令，例如 docker run 、docker ps 来列出正在运行的容器、docker images 来列出镜像，等等。

**docker** **基本组成**

docker主机(Host)：安装了Docker程序的机器（Docker直接安装在操作系统之上）；

docker仓库(Registry)：用来保存各种打包好的软件镜像；仓库分为公有仓库和私有仓库。(很类似maven)

docker镜像(Images)：软件打包好的镜像；放在docker仓库中；

docker容器(Container)：镜像启动后的实例称为一个容器；容器是独立运行的一个或一组应用

**docker 与操作系统比较**

docker是一种轻量级的虚拟化方式

![image-20210318150849177](https://gitee.com/adc123321/blog_img/raw/master/image/202103/18/150851-563512.png)

 Docker常用命令

![image-20210318151000222](https://gitee.com/adc123321/blog_img/raw/master/image/202103/18/161605-357589.png)

**images命令**

```powershell
docker images 
docker image ls
```

**save命令**

```shell
mkdir -p /data 
cd /data 
docker save tomcat:9.0.20-jre8-alpine -o tomcat9.tar 
docker save tomcat:9.0.20-jre8-slim > tomcat9.slim.tar
```

**inspect命令**

- 通过 docker inspect 命令，我们可以获取镜像的详细信息，其中，包括创建者，各层的数字摘要等。

```powershell
docker inspect tomcat:9.0.20-jre8-alpine
docker inspect -f {{".Size"}} tomcat:9.0.20-jre8-alpine
```

**history命令**

- 从前面的命令中，我们了解到，一个镜像是由多个层组成的，那么，我们要如何知道各个层的具体内容呢？

通过 docker history命令，可以列出各个层的创建信息，例如：查看 tomcat:9.0.20-jre8-alpine的各层信息

```powershell
docker history tomcat:9.0.20-jre8-alpine
```

**tag命令**

- 标记本地镜像，将其归入某一仓库

```powershell
docker tag tomcat:9.0.20-jre8-alpine lagou/tomcat:9
```

**rmi命令**

  **常用参数**

- -f, -force : 强制删除镜像，即便有容器引用该镜像；
- -no-prune : 不要删除未带标签的父镜像；

```powershell
docker rmi tomcat:9.0.20-jre8-alpine 
docker image rm tomcat:9.0.20-jre8-alpine
```

**清理镜像**

我们在使用 Docker 一段时间后，系统一般都会残存一些临时的、没有被使用的镜像文件，可以通过以下命令进行清理。执行完命令后，还是告诉我们释放了多少存储空间！

```powershell
docker image prune
```

​                                                                             **Docker容器（container）**

**容器是镜像的运行时实例**

![image-20210318151857629](https://gitee.com/adc123321/blog_img/raw/master/image/202103/18/151858-535527.png)

**新建并启动容器**

```powershell
docker run [OPTIONS] IMAGE [COMMAND] [ARG...]
```

```powershell
docker run -it --rm -p 8080:8080 tomcat:9.0.20-jre8-alpine
```

**docker run**命令常用参数比较多，这里仅仅列出开发岗常用参数，请小伙伴们自行查找资料获得更多参数信息

**-d, --detach=false:** 后台运行容器，并返回容器ID

**-i, --interactive=false:** 以交互模式运行容器，通常与 -t 同时使用

**-P, --publish-all=false:** 随机端口映射，容器内部端口随机映射到主机的端口。**不推荐各位小伙伴使用该参数**

**-p, --publish=[]:** 指定端口映射，格式为：主机(宿主)端口容器端口，推荐各位小伙伴们使用**

**-t, --tty=false:** 为容器重新分配一个伪输入终端，通常与 -i 同时使用

**--name="nginx-lb":** 为容器指定一个名称

**-h , --hostname="laosiji":** 指定容器的hostname

**-e , --env=[]:** 设置环境变量，容器中可以使用该环境变量

**--net="bridge":** 指定容器的网络连接类型，支持 bridge/host/none/container: 四种类型

**--link=[]:** 添加链接到另一个容器；**不推荐各位小伙伴使用该参数**

**-v, --volume :** 绑定一个卷

**--privileged=false:** 指定容器是否为特权容器，特权容器拥有所有的capabilities 

**--restart=no**：指定容器停止后的重启策略

no：容器退出时不重启 

on-failure：容器故障退出（返回值非零）时重启 

always：容器退出时总是重启,**推荐各位小伙伴们使用** 

**--rm=false:** 指定容器停止后自动删除容器,**不能以**docker run -d**启动的容器**

**容器日志**

```
docker logs [OPTIONS] CONTAINER 
```

**-f :** 跟踪日志输出

**--tail :**仅列出最新N条容器日志

**删除容器**

**docker rm** **：**删除一个或多个容器。docker rm命令只能删除处于终止或退出状态的容器，并不能删除还处于运行状态容器

**-f :**通过 SIGKILL 信号强制删除一个运行中的容器。

**-l :**移除容器间的网络连接，而非容器本身。

**-v :**删除与容器关联的卷。

**列出容器**

docker ps [OPTIONS] 

```powershell
docker run -itd --name tomcat9 -p 8080:8080 tomcat:9.0.20-jre8-alpine 
查看运行中的容器 
docker ps tomcat9 
查看所有容器 
docker ps -a tomcat9
```

**启动、重启、终止容器**

**docker start** :启动一个或多个已经被停止的容器

**docker stop** :停止一个运行中的容器

**docker restart** :重启容器

**进入容器**

```
docker exec [OPTIONS] CONTAINER COMMAND [ARG...] 
```

```powershell
有bash命令的linux系统：例如centos
docker run -it --name tomcat9.1 -p 8080:8080 tomcat:9.0.20-jre8-slim
docker exec -it tomcat9.1 /bin/bash 
没有bash命令的linux系统：例如alpine系统 
docker run -it --name tomcat9.2 -p 8081:8080 tomcat:9.0.20-jre8-alpine 
docker exec -it tomcat9.2 sh
```

**查看容器**

**docker inspect :** 获取容器/镜像的元数据。

 语法 

```
docker inspect [OPTIONS] NAME|ID [NAME|ID...] 
```

```
docker run -it --name tomcat9 -p 8081:8080 tomcat:9.0.20-jre8-alpine 
docker inspect tomcat9
```

常用参数

**-f :**指定返回值的模板文件。

**-s :**显示总的文件大小。

**--type :**为指定类型返回JSON。

![image-20210318153201331](https://gitee.com/adc123321/blog_img/raw/master/image/202103/18/153201-770904.png)