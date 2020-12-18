                                                      linux 作业题

**搭建一个MySQL高可用架构集群环境（4台主机，1主、2从、1 MHA）**



![image-20201218165712462](https://gitee.com/adc123321/blog_img/raw/master/image/202012/18/165713-354069.png)

 如上图 CoderCheng 为主库    UCloud  和 UCloud1年  为两个从库

以dept 为库为演示    

![image-20201218170115160](https://gitee.com/adc123321/blog_img/raw/master/image/202012/18/170115-644437.png)

  从库 和主库 目前只要这几条数据  



现在 我在主库添加一条数据



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