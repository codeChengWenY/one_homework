​                                                SpringCloud 作业编程题

**整体架构如下图：**

![](https://gitee.com/adc123321/blog_img/raw/master/image/202011/27/155606-993025.png)

 

实现效果如下：

![image-20201127155925594](C:\Users\Lenovo\AppData\Roaming\Typora\typora-user-images\image-20201127155925594.png)

  接下来（**Show Time**）

效果如下

首先先准备配置项 

- 1  将 本地Host  www.test.com  指向本地  (说明 防止静态页面访问跨域)
- 2   启动需要的项目 效果如下：

![image-20201127160519706](https://gitee.com/adc123321/blog_img/raw/master/image/202011/27/160521-160445.png)

- 3   接下来  访问静态页面 测试 

![image-20201127160756723](https://gitee.com/adc123321/blog_img/raw/master/image/202011/27/160758-257175.png)

  **当输入不存在邮箱时 弹窗提示**

![image-20201127160910249](https://gitee.com/adc123321/blog_img/raw/master/image/202011/27/160911-615933.png)

​        **进入注册页面**



![image-20201127161128142](https://gitee.com/adc123321/blog_img/raw/master/image/202011/27/161129-117372.png)

 **收到邮箱验证码**

![image-20201127161455123](C:\Users\Lenovo\AppData\Roaming\Typora\typora-user-images\image-20201127161455123.png)

 **输入错误的验证码**

![image-20201127161522896](https://gitee.com/adc123321/blog_img/raw/master/image/202011/27/161524-696498.png)



输入正确的验证码 提示并跳转登录界面

![image-20201127161557408](https://gitee.com/adc123321/blog_img/raw/master/image/202011/27/161557-364667.png)



登录界面输入错误的密码时

![image-20201127161728051](https://gitee.com/adc123321/blog_img/raw/master/image/202011/27/161728-146800.png)

登录界面输入正确的密码时

![image-20201127161815852](https://gitee.com/adc123321/blog_img/raw/master/image/202011/27/161815-714463.png)

 接下来 看看 ip暴力测试



​      码云的配置1分钟 限制3次

![image-20201127162115878](C:\Users\Lenovo\AppData\Roaming\Typora\typora-user-images\image-20201127162115878.png)

![image-20201127162126670](https://gitee.com/adc123321/blog_img/raw/master/image/202011/27/162126-617649.png)

 页面显示 请求拒绝    成功！

 **整体思路： 其实是在原来基础上进行增加 对应的操作 实现逻辑。**

