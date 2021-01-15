                                                           redis 集群（redis_cluster）搭建
RedisCluster最少需要三台主服务器，三台从服务器。 

- 下载redis 对应的版本

``` powershell
wget http://download.redis.io/releases/redis-5.0.5.tar.gz
```

-  解压文件

  ```powershell
  tar -xzf redis-5.0.5.tar.gz
  ```

   - 新建redis_cluster 文件夹并 新建7001-7006 目录

     ``` powershell
      mkdir redis_cluster
      cd  redis_cluster/ 
      mkdir 7001 7002 7003 7004 7005 7006
     ```

- 编译 redis 

  ```powershell
  make install PREFIX=/root/redis_cluster/redis_cluster/7001
  ```

- 拷贝配置文件
  
  ```
     cp /root/redis-5.0.5/redis.conf /root/redis_cluster/7001/bin
  ```

-  修改配置文件

     1 端口号

  ![image-20210114142001237](https://gitee.com/adc123321/blog_img/raw/master/image/202101/14/142002-14345.png)

​       2  打开cluster-enable yes

![image-20210114142104172](https://gitee.com/adc123321/blog_img/raw/master/image/202101/14/142104-78557.png)

​    3  后台进程 开启  daemonize yes

![image-20210114142214518](https://gitee.com/adc123321/blog_img/raw/master/image/202101/14/142214-366898.png)

   4   保护模式 关闭

![image-20210114142249346](https://gitee.com/adc123321/blog_img/raw/master/image/202101/15/133752-642747.png)

- 复制7001，创建7002~7006实例，**注意端口修改**。

  ```
   cp -r /root/redis_cluster/7001/* /root/redis_cluster/7002
   cp -r /root/redis_cluster/7001/* /root/redis_cluster/7003 
   cp -r /root/redis_cluster/7001/* /root/redis_cluster/7004 
   cp -r /root/redis_cluster/7001/* /root/redis_cluster/7005 
   cp -r /root/redis_cluster/7001/* /root/redis_cluster/7006
  ```

- 创建 start.sh 启动所有实例

   ```powershell
  cd 7001/bin
  ./redis-server redis.conf
  ```

```powershell
 cd ..
cd ..
cd 7002/bin
./redis-server redis.conf
cd ..
cd ..

cd 7003/bin
./redis-server redis.conf
cd ..
cd ..

cd 7004/bin
./redis-server redis.conf
cd ..
cd ..
cd 7005/bin
./redis-server redis.conf
cd ..
cd ..

cd 7006/bin
./redis-server redis.conf
cd ..
cd ..
```

  chmod u+x start.sh (赋写和执行的权限) 

 ./start.sh(启动RedisCluster)

- 创建Redis集群（创建时Redis里不要有数据）

​         进入到任何一个实例下的bin 目录

```powershell
cd redis_cluster/7001/bin/
```

 执行 集群创建集群命令

```powershell
./redis-cli --cluster create 10.9.49.202:7001 10.9.49.202:7002 10.9.49.202:7003 10.9.49.202:7004 10.9.49.202:7005 10.9.49.202:7006 --cluster-replicas 1
```

 看到如下信息 回复yes



![image-20210114143446447](https://gitee.com/adc123321/blog_img/raw/master/image/202101/14/143447-961623.png)

  最后看到如下信息说明成功

​            ![image-20210114143522864](https://gitee.com/adc123321/blog_img/raw/master/image/202101/14/143522-36593.png)

- 命令客户端连接集群  注意：**-c** 表示是以redis集群方式进行连接

```powershell
./redis-cli -h 127.0.0.1 -p 7001 -c
```

   执行命令测试

   ```
 set name 111 
   ```

![image-20210114143848490](https://gitee.com/adc123321/blog_img/raw/master/image/202101/14/143849-230533.png)

查看集群状态

```
cluster info 
```
![image-20210114144003634](https://gitee.com/adc123321/blog_img/raw/master/image/202101/14/144006-851450.png)

查看集群中的节点

```
  cluster nodes
```

![image-20210114144132655](https://gitee.com/adc123321/blog_img/raw/master/image/202101/14/144133-335819.png)

 到此为止   redis 集群搭建成功！！！

​                 ---------------------------------------------------动态扩容------------------------------------------------------------------

先创建7007 主节点 （无数据） 7008从节点 （无数据）

```powershell
mkdir redis_cluster/7007
mkdir redis_cluster/7008
make install PREFIX=/root/redis_cluster/7007
make install PREFIX=/root/redis_cluster/7008
#拷贝配置文件 并进行端口修改
cp /root/redis-5.0.5/redis.conf /root/redis_cluster/7007/bin
cp /root/redis-5.0.5/redis.conf /root/redis_cluster/7008/bin
```

添加7007结点作为新节点,并启动

   后面地址是集群的一个ip地址表示要加入到该集群中

```
 ./redis-cli --cluster add-node 10.9.49.202:7007 10.9.49.202:7001
```

![image-20210114153904347](https://gitee.com/adc123321/blog_img/raw/master/image/202101/14/153905-774723.png) 

​           **添加完主节点需要对主节点进行hash槽分配，这样该主节才可以存储数据。**

​           **给刚添加的7007结点分配槽**

- ​    **第一步：连接上集群（连接集群中任意一个可用结点都行）**

    ```
    ./redis-cli --cluster reshard 10.9.49.202:7007
    ```

- **第二步：输入要分配的槽数量**

   How many slots do you want to move (from 1 to 16384)? 3000

​     输入：**3000**，表示要给目标节点分配3000个槽

- **第三步：输入接收槽的结点id**

​          What is the receiving node ID?

输入：bcbac44a7830a8e23f3bd07cb69a38df04aa1a2c

```
PS：这里准备给7007分配槽，通过cluster nodes查看7007结点id为：
bcbac44a7830a8e23f3bd07cb69a38df04aa1a2c
```

- **第四步：输入源结点id**

![image-20210114154653083](https://gitee.com/adc123321/blog_img/raw/master/image/202101/14/154653-638862.png)

  表示从所有节点上 一共分配3000 个节点

- **第五步：输入yes开始移动槽到目标结点id**

![image-20210114154847720](https://gitee.com/adc123321/blog_img/raw/master/image/202101/14/154848-182152.png)



![image-20210114154908085](https://gitee.com/adc123321/blog_img/raw/master/image/202101/14/154908-348436.png)





  **添加从节点**

```powershell
./redis-cli --cluster add-node 10.9.49.202:7008 10.9.49.202:7007 --cluster-slave --cluster-master-id  bcbac44a7830a8e23f3bd07cb69a38df04aa1a2c
```



![image-20210114155117757](https://gitee.com/adc123321/blog_img/raw/master/image/202101/14/155118-982584.png)

 查看 扩容后结果

![image-20210114155233904](https://gitee.com/adc123321/blog_img/raw/master/image/202101/14/155234-192808.png)

   成功

  ##  遇到问题 在客户端执行命名 报 

1. **error MOVED 原因和解决方案**

 原因①②③④

这种情况一般是因为启动redis-cli时没有设置集群模式所导致

启动时使用-c参数来启动集群模式，命令如下：

```
redis-cli -c -p 7000
```

2.  [(error) CLUSTERDOWN The cluster is down](https://www.cnblogs.com/enjoyjava/p/11361127.html)

​       外部不到redis 集群 所以修改了 nodes.conf  内网地址为 外网地址 导致  报这个错

解决过程   

​    ① 删除所有节点的 dump.rdb 和nodes.conf文件删除  

​     ②   **开放17001-17008**(重要)

​    ③ 节点创建使用外网地址     否则集群无法创建成功

​       ok 成功访问。使用客户端查看信息 

![image-20210115112823240](https://gitee.com/adc123321/blog_img/raw/master/image/202101/15/112824-690646.png)

   用Jedis 代码查看   


![image-20210115134442139](https://gitee.com/adc123321/blog_img/raw/master/image/202101/15/134443-257713.png)

