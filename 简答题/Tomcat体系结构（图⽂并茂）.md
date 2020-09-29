​                                                  **Tomcat体系结构（图⽂并茂）** 

Tomcat 设计了两个核心组件连接器（**Connector**）和容器（**Container**）来完成 Tomcat 的两大核心功能。

连接器，负责对外交流： 处理Socket连接，负责⽹络字节流与Request和Response对象的转化；

容器，负责内部处理：加载和管理Servlet，以及具体处理Request请求；

![](http://ww1.sinaimg.cn/large/c4c193b1ly1gj6j9nldhdj20oh06aq3a.jpg)



- **EndPoint**

EndPoint 是 Coyote 通信端点，即通信监听的接⼝，是具体Socket接收和发送处理器，是对传输层的抽象，因此EndPoint用来实现TCP/IP协议的。

- **Processor**

Processor 是Coyote 协议处理接口 ，如果说EndPoint是⽤来实现TCP/IP协议的，那么Processor用来实现HTTP协议，Processor接收来自EndPoint的Socket，读取字节流解析成Tomcat Request和Response对象，并通过Adapter将其提交到容器处理，Processor是对应用层协议的抽象。

- **ProtocolHandler**

Coyote 协议接口， 通过Endpoint 和 Processor ， 实现针对具体协议的处理能力。Tomcat 按照协议和I/O 提供6个实现类：AjpNioProtocol ，AjpAprProtocol， AjpNio2Protocol ， Http11NioProtocol，Http11Nio2Protocol ，Http11AprProtocol。

- **Adapter**

由于协议不同，客户端发过来的请求信息也不尽相同，Tomcat定义了自己的Request类来封装这些请求信息。ProtocolHandler接口负责解析请求并生成Tomcat Request类。但是这个Request对象不是标准ServletRequest，不能用Tomcat Request作为参数来调⽤容器。Tomcat设计者的解决方⽅案是引入CoyoteAdapter，这是适配器模式的经典运用，连接器调用CoyoteAdapter的Sevice方法，传入的是Tomcat Request对象，CoyoteAdapter负责将Tomcat Request转成ServletRequest，再调用容器。

#### Tomcat Servlet 容器 Catainlina

Tomcat就是一个Catalina的实例，因为Catalina是Tomcat的核心

Tomcat/Catalina实例

- Catalina

负责解析Tomcat的配置文件（server.xml） , 以此来创建服务器Server组件并进行管理

![](http://ww1.sinaimg.cn/large/c4c193b1ly1gj6iur0yrsj20jk0jx76t.jpg)



```xml
 <?xml version='1.0' encoding='utf­8'?>
<Server port="8005" shutdown="SHUTDOWN">
<Service name="Catalina">
<Connector port="8080" protocol="HTTP/1.1" connectionTimeout="20000"
redirectPort="8443" URIEncoding="UTF­8"/>
<Engine name="Catalina" defaultHost="localhost">
<Host name="localhost">
<Context path="" docBase="/" reloadable="true"/>
</Host>
</Engine>
</Service>
</Server> 
```

 (1) **Server**
Server表示整个的Catalina Servlet容器。Tomcat提供了Server接口的一个默认实现，这通常不需要用户自己去实现。在Server容器中，可以包含一个或多个Service组件。
(2) **Service**
Service是存活在Server内部的中间组件，它将一个或多个连接器（Connector）组件绑定到一个单独的引擎（Engine)上。在Server中，可以包含一个或多个Service组件。Service也很少由用户定制，Tomcat提供了Service接口的默认实现，而这种实现既简单又能满足应用。
(3) **Connector**
连接器（Connector）处理与客户端的通信，它负责接收客户请求，以及向客户返回响应结果。在Tomcat中，有多个连接 器可以使用。
(4) **Engine**
在Tomcat中，每个Service只能包含一个Servlet引擎（Engine）。引擎表示一个特定的Service的请求处理流水线。作为一个Service可以有多个连接器，引擎从连接器接收和处理所有的请求，将响应返回给适合的连接器，通过连接器传输给用户。用户允许通过实现Engine接口提供自定义的引擎，但通常不需要这么做。
(5) **Host**
Host表示一个虚拟主机，一个引擎可以包含多个Host。用户通常不需要创建自定义的
Host，因为Tomcat给出的Host接口的实现（类StandardHost）提供了重要的附加功能。
(6) **Context**
一个Context表示了一个Web应用程序，运行在特定的虚拟主机中。什么是Web应用程序呢？在Sun公司发布的Java Servlet规范中，对Web应用程序做出了如下的定义：“一个Web应用程序是由一组Servlet、HTML页面、类，以及其他的资源组成的运行在Web服务器上的完整的应用程序。它可以在多个供应商提供的实现了Servlet规范的Web容器中运行”。一个Host可以包含多个Context（代表Web应用程序），每一个Context都有一个唯一的路径。用户 通 常 不 需 要 创 建 自 定 义 的 Context ， 因 为 Tomcat 给 出 的 Context 接 口 的 实 （ 类StandardContext）提供了重要的附加功能。

**总结**  :<u>凡是实现了Servlet规范的都可以成为Servlet容器</u> 。

​         一个 Catalina 容器中 是有多个Server组成 而每个Server可以有多个Service,每个Service中包含  一个Engine组件，多个Connector 组件，多个Host 组件，多个Context组件 构成。





