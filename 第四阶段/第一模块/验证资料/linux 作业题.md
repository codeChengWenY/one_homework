                                                      linux 作业题

**搭建一个MySQL高可用架构集群环境（4台主机，1主、2从、1 MHA）**



![image-20201218165712462](https://gitee.com/adc123321/blog_img/raw/master/image/202012/18/165713-354069.png)

 如上图 CoderCheng 为主库    UCloud  和 UCloud1年  为两个从库

以dept 为库为演示    

![image-20201218170115160](https://gitee.com/adc123321/blog_img/raw/master/image/202012/18/170115-644437.png)

  从库 和主库 目前只要这几条数据  现在 我在主库添加一条数据

id为4	name的5数据



![image-20201218170611409](https://gitee.com/adc123321/blog_img/raw/master/image/202012/18/171449-724149.png)



刷新从库 可以看到 数据已经同步过来了 



![image-20201218170740855](https://gitee.com/adc123321/blog_img/raw/master/image/202012/18/171436-319152.png)



![image-20201218170800466](https://gitee.com/adc123321/blog_img/raw/master/image/202012/18/170801-195533.png)

说明 主从同步成功

 以下命名在主库用到命令



![image-20201218170921953](https://gitee.com/adc123321/blog_img/raw/master/image/202012/18/170922-801950.png)

![image-20201218170947061](https://gitee.com/adc123321/blog_img/raw/master/image/202012/18/170947-620462.png)

以下命令从库 用到

![image-20201218171031275](https://gitee.com/adc123321/blog_img/raw/master/image/202012/18/171031-566571.png)

说明满足主从 半同步复制。

  

 接下来 测试mha功能  

备注  

39.106.214.114  master

106.75.20.63     slave

117.50.7.7     slave

106.75.31.205   manager 

-  首先4台机器进行ssh免密登录  

  配置完成后使用命令测试

  

  ```shell
   masterha_check_ssh -conf=/etc/mha_master/mha.cnf
  ```

  出现下图所示代表ssh 免密成功

![image-20201219084600607](https://gitee.com/adc123321/blog_img/raw/master/image/202012/19/090608-553293.png)

- 进行主从复制测试（出现下图表示成功）

  ```powershell
  masterha_check_repl -conf=/etc/mha_master/mha.cnf > /etc/mha_master/manager.log &
  ```

  ![image-20201219084939148](https://gitee.com/adc123321/blog_img/raw/master/image/202012/19/084940-376922.png)
-     接下来 启动 manager管理节点查看主从切换日志

```shell
 masterha_manager --conf=/etc/mha_master/mha.cnf < /etc/mha_master/manager.log &
```

​       接下来手动关闭  39.106.214.114  模拟故障  master mysql  查看 manager日志

![image-20201219091135589](https://gitee.com/adc123321/blog_img/raw/master/image/202012/19/091136-580829.png)

  出现  这个 成功 说明   已经  mha 切换可以成功

   下面在新的master添加一条数据 测试 是否成成功



![image-20201219091935067](https://gitee.com/adc123321/blog_img/raw/master/image/202012/19/091935-930142.png)



​    开始在新的master（Ucloud）添加一条 id 888  name 666的数据  刷新 slave （UCloud1年）库查看数据  

![image-20201219092058063](https://gitee.com/adc123321/blog_img/raw/master/image/202012/19/092100-605003.png)



**测试成功！！！**