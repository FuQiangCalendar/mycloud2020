package com.atguigu.springcloud.network.rmi;

/**
 * @Package com.atguigu.springcloud.network
 * @ClassName RMIProgram
 * @Description RMI 编程
 *
 * Java的RMI远程调用是指，一个JVM中的代码可以通过网络实现远程调用另一个JVM的某个方法。RMI是Remote Method Invocation的缩写。
 * 提供服务的一方我们称之为服务器，而实现远程调用的一方我们称之为客户端。
 *
 * 我们先来实现一个最简单的RMI：服务器会提供一个WorldClock服务，允许客户端获取指定时区的时间，即允许客户端调用下面的方法：
 * LocalDateTime getLocalDateTime(String zoneId);
 *
 * 先运行服务器，再运行客户端。从运行结果可知，因为客户端只有接口，并没有实现类，因此，客户端获得的接口方法返回值实际上是通过网络从服务器端获取的。整个过程实际上非常简单，对客户端来说，客户端持有的WorldClock接口实际上对应了一个“实现类”，它是由Registry内部动态生成的，并负责把方法调用通过网络传递到服务器端。而服务器端接收网络调用的服务并不是我们自己编写的WorldClockService，而是Registry自动生成的代码。我们把客户端的“实现类”称为stub，而服务器端的网络服务类称为skeleton，它会真正调用服务器端的WorldClockService，获取结果，然后把结果通过网络传递给客户端。整个过程由RMI底层负责实现序列化和反序列化：
 *
 * ┌ ─ ─ ─ ─ ─ ─ ─ ─ ┐         ┌ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ┐
 *   ┌─────────────┐                                 ┌─────────────┐
 * │ │   Service   │ │         │                     │   Service   │ │
 *   └─────────────┘                                 └─────────────┘
 * │        ▲        │         │                            ▲        │
 *          │                                               │
 * │        │        │         │                            │        │
 *   ┌─────────────┐   Network   ┌───────────────┐   ┌─────────────┐
 * │ │ Client Stub ├─┼─────────┼>│Server Skeleton│──>│Service Impl │ │
 *   └─────────────┘             └───────────────┘   └─────────────┘
 * └ ─ ─ ─ ─ ─ ─ ─ ─ ┘         └ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ┘
 * Java的RMI严重依赖序列化和反序列化，而这种情况下可能会造成严重的安全漏洞，因为Java的序列化和反序列化不但涉及到数据，还涉及到二进制的字节码，即使使用白名单机制也很难保证100%排除恶意构造的字节码。因此，使用RMI时，双方必须是内网互相信任的机器，不要把1099端口暴露在公网上作为对外服务。
 * 此外，Java的RMI调用机制决定了双方必须是Java程序，其他语言很难调用Java的RMI。如果要使用不同语言进行RPC调用，可以选择更通用的协议，例如gRPC
 *
 * 小结：
 * Java提供了RMI实现远程方法调用：
 * RMI通过自动生成stub和skeleton实现网络调用，客户端只需要查找服务并获得接口实例，服务器端只需要编写实现类并注册为服务；
 * RMI的序列化和反序列化可能会造成安全漏洞，因此调用双方必须是内网互相信任的机器，不要把1099端口暴露在公网上作为对外服务。
 *
 * @Copyright: Copyright (c) 2021</p>
 * @Company: </p>
 * @Author FuQiangCalendar
 * @Date 2021/5/25 15:44
 * @Version 1.0
 **/
public class RMIProgram {

}






