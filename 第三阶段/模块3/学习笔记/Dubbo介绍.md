                                           Dubbo 学习笔记

### Dubbo介绍

[官网参考资料](http://dubbo.apache.org/zh-cn/docs/2.7/user/preface/background/)

## 什么是Dubbo

Apache Dubbo是 一款高性能的Java RPC框架。其前身是阿里巴巴公司开源的一个高性能、轻量级的开源Java RPC框架，可以和Spring框架无缝集成。

###  Dubbo特性

- 面向接口代理的高性能RPC调用

- 智能负载均衡

- 服务自动注册和发现

- 高度可扩展能力 SPI

- 可视化服务治理与运维

  ###  Dubbo处理流程

![下载](https://gitee.com/adc123321/blog_img/raw/master/image/202011/11/173721-660328.png)

图解：

| 节点      | 描述                                 |
| --------- | ------------------------------------ |
| Provider  | 服务提供者                           |
| Consumer  | 服务消费者                           |
| Registry  | 服务注册与发现的注册中心             |
| Monitor   | 监控中心，统计服务调用次数调用时间   |
| Container | 服务运行容器，负责启动加载运行生产者 |

| 线条     | 描述                   |
| -------- | ---------------------- |
| 蓝色虚线 | 服务启动后的初始化过程 |
| 红色虚线 | 异步过程               |
| 红色实现 | 同步调用过程           |



##  Dubbo高级进阶

###  扩展点配置 SPI

SPI 全称为 (Service Provider Interface) ，是JDK内置的一种服务提供发现机制。

目前有不少框架用它来做服务的扩展发现，简单来说，它就是一种动态替换发现的机制。

使用SPI机制的优势是实现解耦，使得第三方服务模块的装配控制逻辑与调用者的业务代码分离。

![JDK-SPI](https://gitee.com/wjy_jy/stage-three-module-three/raw/master/%E7%AC%94%E8%AE%B0/JDK-SPI.png)

Java中如果想要使用SPI功能，先提供标准服务接口，然后再提供相关接口实现和调用者。这样就可以通过SPI机制中约定好的信息进行查询相应的接口实现。

SPI遵循如下约定:

1. 当服务提供者提供了接口的一种具体实现后，在META-INF/services目录下创建一个以“接口全限定名”为命名的文件，内容为实现类的全限定名;

2. 接口实现类所在的jar包放在主程序的classpath中;

3. 主程序通过java.util.ServiceLoader动态装载实现模块。

   它通过扫描META-INF/services目录下的配置文件找到实现类的全限定名，把类加载到JVM;

4. SPI的实现类必须携带一个无参构造方法

####  扩展点-Dubbo中的SPI

##### 来源

Dubbo 的扩展点加载从 JDK 标准的 SPI (Service Provider Interface) 扩展点发现机制加强而来。

Dubbo 改进了 JDK 标准的 SPI 的以下问题：

- JDK 标准的 SPI 会**一次性实例化扩展点所有实现**，如果有扩展实现初始化很耗时，但如果没用上也加载，会很浪费资源。
- 如果扩展点加载失败，连扩展点的名称都拿不到了。比如：JDK 标准的 ScriptEngine，通过`getName()` 获取脚本类型的名称，但如果 RubyScriptEngine 因为所依赖的 jruby.jar 不存在，导致 RubyScriptEngine 类加载失败，这个失败原因被吃掉了，和 ruby 对应不起来，当用户执行 ruby 脚本时，会报不支持 ruby，而不是真正失败的原因。
- 增加了对扩展点 IoC 和 AOP 的支持，一个扩展点可以直接 setter 注入其它扩展点。

##### 约定

1. 当服务提供者提供了接口的一种具体实现后，在`META-INF/dubbo/目录下创建一个以“接口全限定名”为命名的文件，内容为实现类的全限定名
2. 内容为：`配置名=扩展实现类全限定名`，多个实现类用换行符分隔